package com.nonofce.android.mlbteams.ui.player


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.nonofce.android.data.repository.MlbRepository
import com.nonofce.android.mlbteams.MLBApp
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.data.database.RoomDataSource
import com.nonofce.android.mlbteams.data.server.MLBServer
import com.nonofce.android.mlbteams.data.server.RetrofitDataSource
import com.nonofce.android.mlbteams.databinding.FragmentPlayerBinding
import com.nonofce.android.mlbteams.ui.settings.MLBSettings
import com.nonofce.android.mlbteams.ui.settings.MlbSettingsDataSource
import com.nonofce.android.usecases.LoadPlayer

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

        val repository = MlbRepository(
            RoomDataSource((context!!.applicationContext as MLBApp).database),
            RetrofitDataSource(MLBServer.service),
            MlbSettingsDataSource(MLBSettings(context))
        )

        viewModel = ViewModelProviders.of(
            this,
            PlayerViewModelFactory(LoadPlayer(repository, args.playerId))
        )[PlayerViewModel::class.java]

        dataBinding.apply {
            lifecycleOwner = this@PlayerFragment
            playerViewModel = viewModel

        }
    }


}
