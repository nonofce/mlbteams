package com.nonofce.android.mlbteams.ui.roster

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nonofce.android.mlbteams.R

class RosterFragment : Fragment() {

    companion object {
        fun newInstance() = RosterFragment()
    }

    private lateinit var viewModel: RosterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_roster, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RosterViewModel::class.java)

    }

}
