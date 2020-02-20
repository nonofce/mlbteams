package com.nonofce.android.mlbteams.ui

import androidx.test.rule.ActivityTestRule
import com.nonofce.android.mlbteams.ui.main.MainActivity
import com.nonofce.android.mlbteams.utils.MockWebServerRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class MlbUiTest {

    @get:Rule
    lateinit var mockWebServerRule: MockWebServerRule

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setup() {
//        mockWebServerRule.server.enqueue(
//            MockResponse().fromJson(ApplicationProvider.getApplicationContext(), "data.json")
//        )

    }

    @Test
    fun loadTeams() {

    }
}