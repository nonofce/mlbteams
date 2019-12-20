package com.nonofce.android.mlbteams.ui.roster

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.api.load
import coil.decode.SvgDecoder
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.data.MLBRepository
import com.nonofce.android.mlbteams.databinding.FragmentRosterBinding
import kotlinx.android.synthetic.main.fragment_roster.*

class RosterFragment : Fragment() {

    private val args: RosterFragmentArgs by navArgs()
    private lateinit var viewModel: RosterViewModel
    private lateinit var dataBinding: FragmentRosterBinding
    private lateinit var rosterAdapter: RosterAdapter

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
        //"${args.team.name_display_full} - ${args.selectedSeason}"

        viewModel = ViewModelProviders.of(
            this,
            RosterViewModelFactory(MLBRepository(), args.selectedSeason, args.team.team_id)
        )[RosterViewModel::class.java]

        dataBinding.apply {
            lifecycleOwner = this@RosterFragment
            rosterViewModel = viewModel
        }

        rosterAdapter = RosterAdapter { viewModel::playerSelected }
        rosterRecyclerView.adapter = rosterAdapter

    }

}
