package com.nonofce.android.mlbteams.ui.teams

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.google.android.material.snackbar.Snackbar
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.common.EventObserver
import com.nonofce.android.mlbteams.common.PermissionRequester
import com.nonofce.android.mlbteams.common.app
import com.nonofce.android.mlbteams.databinding.FragmentTeamsBinding
import kotlinx.android.synthetic.main.fragment_teams.*

class TeamsFragment : Fragment() {

    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var dataBinding: FragmentTeamsBinding
    private lateinit var navController: NavController

    private lateinit var component: TeamFragmentComponent
    private val viewModel: TeamsViewModel by lazy {
        @Suppress("UNCHECKED_CAST")
        val vmFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                component.teamsViewModel as T
        }
        ViewModelProvider(this, vmFactory).get(TeamsViewModel::class.java)
    }

    private lateinit var coarsePermissionRequester: PermissionRequester

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coarsePermissionRequester =
            PermissionRequester(this.activity!!, ACCESS_FINE_LOCATION)

        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_teams,
            container,
            false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        component = app.mlbComponent.plus(TeamFragmentModule())
        val mlbSettings = component.settings

        navController = view.findNavController()

        val seasonsCount = mlbSettings.getSeasonCountPreferenceValue()
        viewModel.getAvailableSeasons(seasonsCount)
        viewModel.navigateToRoster.observe(viewLifecycleOwner, EventObserver {
            val (team, selectedSeason) = it
            val action =
                TeamsFragmentDirections.actionTeamsFragmentToRosterFragment(team, selectedSeason)
            navController.navigate(action)
        })

        viewModel.seasons.observe(viewLifecycleOwner, Observer {
            seasonsSelector.adapter = ArrayAdapter(
                context!!,
                R.layout.support_simple_spinner_dropdown_item,
                it
            )
            if (viewModel.selectedSeasonPosition != TeamsViewModel.INITIAL_SEASON) {
                seasonsSelector.setSelection(viewModel.selectedSeasonPosition)
            }
        })

        dataBinding.apply {
            teamsViewModel = viewModel
            lifecycleOwner = this@TeamsFragment
        }

        seasonsSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setSelectedSeason(
                    parent!!.getItemAtPosition(position) as String,
                    position
                )
            }
        }

        teamsAdapter = TeamsAdapter(
            viewModel::teamSelected,
            ImageLoader(context!!) {
                componentRegistry {
                    add(SvgDecoder(context!!))
                }
            }
        )
        teamsRecyclerView.adapter = teamsAdapter

        fab.setOnClickListener {
            coarsePermissionRequester.request {
                viewModel.showLocalTeam()
            }

        }

        viewModel.noLocatTeam.observe(viewLifecycleOwner, Observer {
            if (it) {
                Snackbar.make(
                    teamsRecyclerView,
                    getString(R.string.noLocalTeamFound),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }
}
