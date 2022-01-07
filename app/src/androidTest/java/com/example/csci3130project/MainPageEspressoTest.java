package com.example.csci3130project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.csci3130project.R;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class MainPageEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @Before
    public void setUp() throws Exception {
    }

    // This test checks to see if we have moved to the map by clicking the map btn
    @Test
    public void checkIfMovedToMap() {
        onView(withId(R.id.mapBtn)).perform(click());
        intended(hasComponent(MapsActivity.class.getName()));
    }

    // This test checks to see if we have moved to the profile page by clicking the profile btn
    @Test
    public void checkIfMovedToProfile() {
        onView(withId(R.id.profBtn)).perform(click());
        intended(hasComponent(ProfileActivity.class.getName()));
    }

    // This test checks to see if we have moved to the upload page by clicking the upload items btn
    @Test
    public void checkIfMovedToUpload() {
        onView(withId(R.id.uploadBtn)).perform(click());
        intended(hasComponent(UploadActivity.class.getName()));
    }

    // This test checks to see if we have moved to the find items page by clicking the find items btn
    @Test
    public void checkIfMovedToFind() {
        onView(withId(R.id.searchBtn)).perform(click());
        intended(hasComponent(SearchActivity.class.getName()));
    }

    // This test checks to see if we have moved to the alerts page by clicking the alerts btn
    @Test
    public void checkIfMovedToAlerts() {
        onView(withId(R.id.notifBtn)).perform(click());
        intended(hasComponent(NotificationActivity.class.getName()));
    }

    // This test checks to see if we have moved to the chat page by clicking the chat btn
    @Test
    public void checkIfMovedToChat() {
        onView(withId(R.id.chatBtn)).perform(click());
        intended(hasComponent(ChatActivity.class.getName()));
    }

    // This test checks if scan for items button works
    @Test
    public void checkIfScanForItemButton() {
        onView(withId(R.id.refreshBtn)).perform(click());
        onView(withId(R.id.successView)).check(matches(withText("New Item!")));
    }

    @After
    public void tearDown() throws Exception {
    }
}