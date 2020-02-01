package com.nonofce.android.mlbteams.ui.player


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.common.app
import com.nonofce.android.mlbteams.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {

    private lateinit var dataBinding: FragmentPlayerBinding
    private val args: PlayerFragmentArgs by navArgs<PlayerFragmentArgs>()

    private lateinit var component: PlayerFragmentComponent
    private val viewModel: PlayerViewModel by lazy {
        @Suppress("UNCHECKED_CAST")
        val vmFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                component.playerViewModel as T
        }
        ViewModelProvider(this, vmFactory).get(PlayerViewModel::class.java)
    }

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

        component = app.mlbComponent.plus(PlayerFragmentModule(args.playerId))

        dataBinding.apply {
            lifecycleOwner = this@PlayerFragment
            playerViewModel = viewModel

        }
    }


}
