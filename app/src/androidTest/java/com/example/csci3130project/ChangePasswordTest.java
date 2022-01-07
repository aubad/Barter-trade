package com.example.csci3130project;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import com.example.csci3130project.ui.profile.ProfileFragment;

import org.junit.Rule;

@RunWith(AndroidJUnit4.class)
public class ChangePasswordTest {

    @Rule
    public ActivityScenarioRule<ChangePassword> myRule = new ActivityScenarioRule<>(ChangePassword.class);
    public IntentsTestRule<ChangePassword> myIntentRule = new IntentsTestRule<>(ChangePassword.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @Test
    public void checkIfChangePasswordIsVisible() {
        onView(withId(R.id.oldPasswordText)).check(matches(withText("")));
        onView(withId(R.id.newPasswordText)).check(matches(withText("")));
    }

    //Checks if the old password is invalid
    @Test
    public void checkIfOldPasswordIsInvalid() {
        onView(withId(R.id.oldPasswordText)).perform(typeText("1234567"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.newPasswordText)).perform(typeText("lol12345678"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.resetPassButton)).perform(click());
        onView(withId(R.id.displayTextView)).check(matches(withText("Old Password is Invalid!")));
    }

    //Checks if the new password is invalid
    @Test
    public void checkIfNewPasswordIsInvalid() {
        onView(withId(R.id.oldPasswordText)).perform(typeText("12345678"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.newPasswordText)).perform(typeText("lol"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.resetPassButton)).perform(click());
        onView(withId(R.id.displayTextView)).check(matches(withText("New Password is Invalid!")));
    }

    //test that checks if passwords are the same
    @Test
    public void checkIfPasswordsSame() {
        onView(withId(R.id.oldPasswordText)).perform(typeText("12345678"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.newPasswordText)).perform(typeText("12345678"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.resetPassButton)).perform(click());
        onView(withId(R.id.displayTextView)).check(matches(withText("Passwords Cannot be the Same!")));
    }

    //test that checks if password updates
    @Test
    public void checkIfPasswordUpdates() {
        onView(withId(R.id.oldPasswordText)).perform(typeText("12345678"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.newPasswordText)).perform(typeText("123456789"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.resetPassButton)).perform(click());
        onView(withId(R.id.displayTextView)).check(matches(withText("")));
    }
}
