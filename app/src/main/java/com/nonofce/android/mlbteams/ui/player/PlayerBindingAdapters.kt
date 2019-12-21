package com.nonofce.android.mlbteams.ui.player

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.model.player.Row

@BindingAdapter("playerLocation")
fun TextView.setPlayerLocation(player: Row?) {
    player?.apply {
        text =
            context.getString(R.string.playerLocationLbl, player.birth_city, player.birth_country)
    }
}

@BindingAdapter("playerHeight")
fun TextView.setPlayerHeight(player: Row?) {
    player?.apply {
        text =
            context.getString(R.string.playerHeightLbl, player.height_feet, player.height_inches)
    }
}

@BindingAdapter("playerWeight")
fun TextView.setPlayerWeight(weight: String?) {
    weight?.apply {
        text =
            context.getString(R.string.playerWeightLbl, weight)
    }
}