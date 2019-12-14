package com.nonofce.android.mlbteams.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import kotlinx.android.synthetic.main.fragment_teams.*

class TeamsFragment : Fragment() {

    private lateinit var viewModel: TeamsViewModel
    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        viewModel = ViewModelProviders.of(
            this,
            TeamsViewModelFactory(MBLRepository())
        )[TeamsViewModel::class.java]

        viewModel.model.observe(this, Observer(::updateUi))

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
    }

    private fun updateUi(model: TeamsViewModel.UiModel) {
        when (model) {
            is TeamsViewModel.UiModel.SeasonLoaded -> {
                seasonsSelector.adapter =
                    ArrayAdapter(
                        context!!,
                        R.layout.support_simple_spinner_dropdown_item,
                        model.seasons
                    )
            }
            is TeamsViewModel.UiModel.TeamsLoaded -> {
                teamsAdapter.teams = model.teams
            }
            is TeamsViewModel.UiModel.Error -> {
                val snackbar =
                    Snackbar.make(teamFragment, model.e.message.toString(), Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.retryLoading) {
                    viewModel.setSelectedSeason(seasonsSelector.selectedItem as String)
                }
                snackbar.show()
            }
            is TeamsViewModel.UiModel.TeamSelected -> {
                val action = TeamsFragmentDirections.actionTeamsFragmentToRosterFragment(
                    model.team
                )
                navController.navigate(action)
            }
            is TeamsViewModel.UiModel.StartLoading -> {
                progressBar.visibility = View.VISIBLE
            }
            is TeamsViewModel.UiModel.EndLoading -> {
                progressBar.visibility = View.GONE
            }
        }
    }

}
