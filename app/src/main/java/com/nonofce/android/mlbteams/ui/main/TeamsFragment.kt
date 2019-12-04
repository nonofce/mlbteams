package com.nonofce.android.mlbteams.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.model.MBLRepository
import kotlinx.android.synthetic.main.fragment_teams.*

class TeamsFragment : Fragment() {

    private lateinit var viewModel: TeamsViewModel
    private lateinit var teamsAdapter: TeamsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(
            this,
            TeamsViewModelFactory(MBLRepository())
        )[TeamsViewModel::class.java]

        viewModel.model.observe(this, Observer(::updateUi))
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        teamsAdapter = TeamsAdapter(viewModel::teamSelected, context!!)
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
                Snackbar.make(teamFragment, model.e.message.toString(), Snackbar.LENGTH_LONG).show()
            }
            is TeamsViewModel.UiModel.TeamSelected -> {
                Snackbar.make(teamFragment, model.team.name_display_full, Snackbar.LENGTH_LONG)
                    .show()
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
