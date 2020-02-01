package com.nonofce.android.mlbteams.ui.roster

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.api.load
import coil.decode.SvgDecoder
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.common.EventObserver
import com.nonofce.android.mlbteams.common.app
import com.nonofce.android.mlbteams.databinding.FragmentRosterBinding
import kotlinx.android.synthetic.main.fragment_roster.*

class RosterFragment : Fragment() {

    private val args: RosterFragmentArgs by navArgs()
    private lateinit var dataBinding: FragmentRosterBinding
    private lateinit var rosterAdapter: RosterAdapter
    private lateinit var navController: NavController

    private lateinit var component: RosterFragmentComponent
    private val viewModel: RosterViewModel by lazy {
        @Suppress("UNCHECKED_CAST")
        val vmFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                component.rosterViewModel as T
        }
        ViewModelProvider(this, vmFactory).get(RosterViewModel::class.java)
    }

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

        component =
            app.mlbComponent.plus(RosterFragmentModule(args.selectedSeason, args.team.team_id))

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
