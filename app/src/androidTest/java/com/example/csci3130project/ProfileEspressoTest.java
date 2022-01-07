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


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class ProfileEspressoTest {

    @Rule
    public ActivityScenarioRule<ProfileActivity> myRule = new ActivityScenarioRule<>(ProfileActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @Before
    public void setUp() throws Exception {
    }

    //Checks if we moved to change password
    @Test
    public void checkIfMovedToChangePass() {
        onView(withId(R.id.changePassBtn)).perform(click());
        intended(hasComponent(ChangePasswordActivity.class.getName()));
    }

    //Checks if we moved to transaction history
    @Test
    public void checkIfMovedToTransactionHistoryPage() {
        onView(withId(R.id.transactionHistoryButton)).perform(click());
        intended(hasComponent(TransactionHistory.class.getName()));
    }

    //Checks if the old password is invalid
    @Test
    public void checkIfOldPasswordIsInvalid() {
        onView(withId(R.id.changePassBtn)).perform(click());
        intended(hasComponent(ChangePasswordActivity.class.getName()));
        onView(withId(R.id.oldPasswordText)).perform(typeText("1234567"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.newPasswordText)).perform(typeText("lol12345678"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.resetPassButton)).perform(click());
        onView(withId(R.id.displayTextView)).check(matches(withText("Old Password is Invalid!")));
    }

    //Checks if the new password is invalid
    @Test
    public void checkIfNewPasswordIsInvalid() {
        onView(withId(R.id.changePassBtn)).perform(click());
        intended(hasComponent(ChangePasswordActivity.class.getName()));
        onView(withId(R.id.oldPasswordText)).perform(typeText("12345678"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.newPasswordText)).perform(typeText("lol"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.resetPassButton)).perform(click());
        onView(withId(R.id.displayTextView)).check(matches(withText("New Password is Invalid!")));
    }

    //test that checks if passwords are the same
    @Test
    public void checkIfPasswordsSame() {
        onView(withId(R.id.changePassBtn)).perform(click());
        intended(hasComponent(ChangePasswordActivity.class.getName()));
        onView(withId(R.id.oldPasswordText)).perform(typeText("12345678"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.newPasswordText)).perform(typeText("12345678"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.resetPassButton)).perform(click());
        onView(withId(R.id.displayTextView)).check(matches(withText("Passwords Cannot be the Same!")));
    }

    //test that checks if password updates
    @Test
    public void checkIfPasswordUpdates() {
        onView(withId(R.id.changePassBtn)).perform(click());
        intended(hasComponent(ChangePasswordActivity.class.getName()));
        onView(withId(R.id.oldPasswordText)).perform(typeText("12345678"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.newPasswordText)).perform(typeText("123456789"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.resetPassButton)).perform(click());
        onView(withId(R.id.displayTextView)).check(matches(withText("")));
    }

    //Checks if moved back to login if we click sign out
    @Test
    public void checkIfSignsOut() {
        onView(withId(R.id.logOutButtonProfile)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }




}
