package com.nonofce.android.mlbteams.ui

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.nonofce.android.mlbteams.MLBApp
import com.nonofce.android.mlbteams.R
import com.nonofce.android.mlbteams.dagger.DaggerUiMlbComponentTest
import com.nonofce.android.mlbteams.ui.main.MainActivity
import com.nonofce.android.mlbteams.utils.MockWebServerRule
import com.nonofce.android.mlbteams.utils.fromJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MlbUiTest {

    @get:Rule
    val rule: MockWebServerRule = MockWebServerRule(
        DaggerUiMlbComponentTest.factory()
            .create(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MLBApp).getOkHttpClient()
    )

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        val app =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MLBApp
        val component = DaggerUiMlbComponentTest.factory()
            .create(app)
        app.mlbComponent = component

        mockWebServer = component.getMockWebServer()
    }

    @Test
    fun loadTeams() {
        mockWebServer.enqueue(
            MockResponse().fromJson(
                InstrumentationRegistry.getInstrumentation().context,
                "team.json"
            )
        )

        activityTestRule.launchActivity(null)

        SystemClock.sleep(5000)

        onView(withId(R.id.teamName)).check(ViewAssertions.matches(withText("Arizona Diamondbacks")))
    }

    @Test
    fun loadRoster(){
        mockWebServer.enqueue(
            MockResponse().fromJson(
                InstrumentationRegistry.getInstrumentation().context,
                "team.json"
            )
        )

        activityTestRule.launchActivity(null)

        SystemClock.sleep(5000)

        mockWebServer.enqueue(
            MockResponse().fromJson(
                InstrumentationRegistry.getInstrumentation().context,
                "roster.json"
            )
        )

        onView(withId(R.id.teamsRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        SystemClock.sleep(1000)

        onView(withId(R.id.playerName)).check(ViewAssertions.matches(withText("Nick Ahmed")))

    }
    @Test
    fun loadPlayer(){
        mockWebServer.enqueue(
            MockResponse().fromJson(
                InstrumentationRegistry.getInstrumentation().context,
                "team.json"
            )
        )

        activityTestRule.launchActivity(null)

        SystemClock.sleep(5000)

        mockWebServer.enqueue(
            MockResponse().fromJson(
                InstrumentationRegistry.getInstrumentation().context,
                "roster.json"
            )
        )

        onView(withId(R.id.teamsRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        SystemClock.sleep(1000)

        onView(withId(R.id.playerName)).check(ViewAssertions.matches(withText("Nick Ahmed")))

        mockWebServer.enqueue(
            MockResponse().fromJson(
                InstrumentationRegistry.getInstrumentation().context,
                "player.json"
            )
        )

        onView(withId(R.id.rosterRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        SystemClock.sleep(1000)

        onView(withId(R.id.textView5)).check(ViewAssertions.matches(withText("Nick Ahmed")))
    }
}