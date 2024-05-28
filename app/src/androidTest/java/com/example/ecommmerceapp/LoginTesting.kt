package com.mvi.ecommmerceapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTesting {
    //https://stackoverflow.com/questions/39193268/run-espresso-test-multiple-times
    @get:Rule
    val loginRule =   createAndroidComposeRule<LoginActivity>()

    @Test
    fun openLoginScreen(){
        loginRule.onNodeWithTag("userTag").performTextInput("demo@gmail.com")
        loginRule.onNodeWithTag("passTag").performTextInput("123")
        loginRule.onNodeWithTag("loginTag").performClick()
    }

}




