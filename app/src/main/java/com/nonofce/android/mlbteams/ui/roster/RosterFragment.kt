package com.nonofce.android.mlbteams.ui.roster

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.api.load
import coil.decode.SvgDecoder
import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.mlbteams.MLBApp
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.common.EventObserver
import com.nonofce.android.mlbteams.data.database.RoomDataSource
import com.nonofce.android.mlbteams.data.server.MLBServer
import com.nonofce.android.mlbteams.data.server.RetrofitDataSource
import com.nonofce.android.mlbteams.databinding.FragmentRosterBinding
import com.nonofce.android.mlbteams.ui.settings.MLBSettings
import com.nonofce.android.mlbteams.ui.settings.MlbSettingsDataSource
import com.nonofce.android.usecases.LoadRoster
import kotlinx.android.synthetic.main.fragment_roster.*

class RosterFragment : Fragment() {

    private val args: RosterFragmentArgs by navArgs()
    private lateinit var viewModel: RosterViewModel
    private lateinit var dataBinding: FragmentRosterBinding
    private lateinit var rosterAdapter: RosterAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate<FragmentRosterBinding>(
            inflater,
            R.layout.fragment_roster,
            container,
            false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
        val imageLoader = ImageLoader(context!!) {
            componentRegistry {
                add(SvgDecoder(context!!))
            }
        }
        teamLogoSmall.load(
            Uri.parse("file:///android_asset/${args.team.team_id}.svg"),
            imageLoader
        ) {
            crossfade(true)
        }
        teamInfo.text =
            getString(R.string.rosterHeaderLbl, args.team.name_display_full, args.selectedSeason)

        val repository = MlbRepository(
            RoomDataSource((context!!.applicationContext as MLBApp).database),
            RetrofitDataSource(MLBServer.service),
            MlbSettingsDataSource(MLBSettings(context))
        )

        viewModel = ViewModelProviders.of(
            this,
            RosterViewModelFactory(LoadRoster(repository, args.selectedSeason, args.team.team_id))
        )[RosterViewModel::class.java]

        viewModel.navigateToPlayerInfo.observe(this, EventObserver {
            val action = RosterFragmentDirections.actionRosterFragmentToPlayerFragment(it)
            navController.navigate(action)
        })

        dataBinding.apply {
            lifecycleOwner = this@RosterFragment
            rosterViewModel = viewModel
        }

        rosterAdapter = RosterAdapter(viewModel::playerSelected)
        rosterRecyclerView.adapter = rosterAdapter

    }

}
