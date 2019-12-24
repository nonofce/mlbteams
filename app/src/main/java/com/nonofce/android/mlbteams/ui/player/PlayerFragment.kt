package com.nonofce.android.mlbteams.ui.player


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.nonofce.android.mlbteams.MLBApp
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.data.MLBRepository
import com.nonofce.android.mlbteams.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {

    private lateinit var dataBinding: FragmentPlayerBinding
    private lateinit var viewModel: PlayerViewModel
    private val args: PlayerFragmentArgs by navArgs<PlayerFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate<FragmentPlayerBinding>(
            inflater,
            R.layout.fragment_player,
            container,
            false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(
            this,
            PlayerViewModelFactory(
                MLBRepository(activity!!.applicationContext as MLBApp),
                args.playerId
            )
        )[PlayerViewModel::class.java]

        dataBinding.apply {
            lifecycleOwner = this@PlayerFragment
            playerViewModel = viewModel

        }
    }


}
