package com.example.csci3130project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.csci3130project.R;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class LoginEspressoTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> myRule = new ActivityScenarioRule<>(LoginActivity.class);

    @BeforeClass
    public static void setup() throws Exception {
        Intents.init();
    }

    @Test
    public void checkIfLoginIsValid() {
        onView(withId(R.id.loginEmail)).perform(typeText("ben@email.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginPassword)).perform(typeText("qwerty123"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    //Checks if the old password is invalid
    @Test
    public void checkIfLoginIsInvalid() {
        onView(withId(R.id.loginEmail)).perform(typeText("mj@email.com"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginPassword)).perform(typeText("lol12345678"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
    }

    @After
    public void tearDown() throws Exception{
        Intents.release();
    }
}
