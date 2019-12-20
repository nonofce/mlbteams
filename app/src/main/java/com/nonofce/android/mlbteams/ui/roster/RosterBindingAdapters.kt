package com.nonofce.android.mlbteams.ui.roster

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.nonofce.android.mlbteams.R

@BindingAdapter("playerPosition")
fun ImageView.setPlayerPosition(position: String) {
    if (id == R.id.cf && position == "CF") {
        visibility = View.VISIBLE
    } else if (id == R.id.lf && position == "LF") {
        visibility = View.VISIBLE
    } else if (id == R.id.rf && position == "RF") {
        visibility = View.VISIBLE
    } else if (id == R.id.p && position == "P") {
        visibility = View.VISIBLE
    } else if (id == R.id.c && position == "C") {
        visibility = View.VISIBLE
    } else if (id == R.id.b1 && position == "1B") {
        visibility = View.VISIBLE
    } else if (id == R.id.b2 && position == "2B") {
        visibility = View.VISIBLE
    } else if (id == R.id.b3 && position == "3B") {
        visibility = View.VISIBLE
    } else if (id == R.id.ss && position == "SS") {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}

@BindingAdapter("batPosition")
fun ImageView.setBatPosition(position: String) {
    if (id == R.id.bats_l && (position == "L" || position == "S")) {
        visibility = View.VISIBLE
    } else if (id == R.id.bats_r && (position == "R" || position == "S")) {
        visibility = View.VISIBLE
    } else {
        visibility = View.INVISIBLE
    }
}

@BindingAdapter("throwHand")
fun ImageView.setThrowHand(hand: String) {
    if (id == R.id.throws_l && hand == "L") {
        visibility = View.VISIBLE
    } else if (id == R.id.throws_r && hand == "R") {
        visibility = View.VISIBLE
    } else {
        visibility = View.INVISIBLE
    }
}

@BindingAdapter("roster_years")
fun TextView.setRosterYears(years: String) {
    text = HtmlCompat.fromHtml(
        context.getString(R.string.rosterYears, years),
        HtmlCompat.FROM_HTML_MODE_LEGACY
    )
}