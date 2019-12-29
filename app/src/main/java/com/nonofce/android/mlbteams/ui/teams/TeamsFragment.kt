package com.nonofce.android.mlbteams.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.nonofce.android.mlbteams.MLBApp
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.common.EventObserver
import com.nonofce.android.mlbteams.data.MLBRepository
import com.nonofce.android.mlbteams.databinding.FragmentTeamsBinding
import com.nonofce.android.mlbteams.ui.settings.MLBSettings
import kotlinx.android.synthetic.main.fragment_teams.*

class TeamsFragment : Fragment() {

    private lateinit var viewModel: TeamsViewModel
    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var dataBinding: FragmentTeamsBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate<FragmentTeamsBinding>(
            inflater,
            R.layout.fragment_teams,
            container,
            false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
        val mlbSettings = MLBSettings(context)

        viewModel = ViewModelProviders.of(
            this,
            TeamsViewModelFactory(
                MLBRepository(
                    context!!.applicationContext as MLBApp,
                    mlbSettings
                )
            )
        )[TeamsViewModel::class.java]

        val seasonsCount = mlbSettings.getSeasonCountPreferenceValue()
        viewModel.getAvailableSeasons(seasonsCount)
        viewModel.navigateToRoster.observe(this, EventObserver {
            val (team, selectedSeason) = it
            val action =
                TeamsFragmentDirections.actionTeamsFragmentToRosterFragment(team, selectedSeason)
            navController.navigate(action)
        })

        viewModel.seasons.observe(this, Observer {
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
    }
}
