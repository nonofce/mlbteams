package com.nonofce.android.mlbteams.utils

import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockWebServerRule(val okHttpClient: OkHttpClient) : TestRule {

    override fun apply(base: Statement, description: Description) = object : Statement() {

        override fun evaluate() {
            val resource =
                OkHttp3IdlingResource.create("OkHttp", okHttpClient)
            IdlingRegistry.getInstance().register(resource)
            base.evaluate()
            IdlingRegistry.getInstance().unregister(resource)
        }
    }
}
