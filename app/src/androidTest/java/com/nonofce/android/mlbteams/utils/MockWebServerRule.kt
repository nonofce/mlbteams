package com.nonofce.android.mlbteams.utils

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import javax.inject.Inject
import kotlin.concurrent.thread

class MockWebServerRule(val server: MockWebServer) : TestRule
{

    override fun apply(base: Statement, description: Description) = object : Statement() {

        override fun evaluate() {
            server.start()
            base.evaluate()
            server.shutdown()
        }
    }
}
