package com.nonofce.android.mlbteams.ui.settings


import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.nonofce.android.mlbteams.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val seasonsCountPreference = findPreference<EditTextPreference>("seasonsCount")
        seasonsCountPreference?.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER
        }
        val dataDurationPreference = findPreference<EditTextPreference>("dataDuration")
        dataDurationPreference?.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER
        }
    }

}
