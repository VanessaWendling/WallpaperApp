package com.vamg.testing.coroutinerule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainCoroutineRule {
    @ExperimentalCoroutinesApi
    class MainCoroutineRule(
        private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
    ) : TestWatcher() {

        override fun starting(description: Description) {
            super.starting(description)
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description) {
            super.finished(description)
            Dispatchers.resetMain()
        }
    }
}