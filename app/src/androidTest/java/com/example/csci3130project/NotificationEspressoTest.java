package com.example.csci3130project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class NotificationEspressoTest {

    @Rule
    public ActivityScenarioRule<NotificationActivity> myRule = new ActivityScenarioRule<>(NotificationActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @Before
    public void setUp() throws Exception {
    }

    //Checks if the title of page is present
    @Test
    public void checkIfTitleIsVisible() {
        onView(withId(R.id.ItemsNearMeHeader)).check(matches(withText("Items Near You")));
    }

}
