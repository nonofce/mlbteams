package com.nonofce.android.mlbteams.ui.settings


import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
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
        val lastCacheDatePreference = findPreference<Preference>("lastCacheDate")
        lastCacheDatePreference?.summaryProvider = Preference.SummaryProvider<Preference> {
            it.sharedPreferences.getString("lastCacheDate", "")
        }
    }

}
