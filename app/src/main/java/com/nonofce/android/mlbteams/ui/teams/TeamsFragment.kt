package com.nonofce.android.mlbteams.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.google.android.material.snackbar.Snackbar
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.data.MBLRepository
import com.nonofce.android.mlbteams.databinding.FragmentTeamsBinding
import kotlinx.android.synthetic.main.fragment_teams.*

class TeamsFragment : Fragment() {

    private lateinit var viewModel: TeamsViewModel
    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var dataBinding: FragmentTeamsBinding

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

        viewModel = ViewModelProviders.of(
            this,
            TeamsViewModelFactory(MBLRepository())
        )[TeamsViewModel::class.java]

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
                viewModel.setSelectedSeason(parent!!.getItemAtPosition(position) as String)
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

        viewModel.selectedTeam.observe(this, Observer {
            val navController = view.findNavController()
            val action = TeamsFragmentDirections.actionTeamsFragmentToRosterFragment(it)
            navController.navigate(action)
        })
    }
}
