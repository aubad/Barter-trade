package com.example.csci3130project;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

//public class RegistrationValidationTests {
//
//    static RegFragment regFragment;
//
//    @BeforeClass
//    public static void setup() {
//        regFragment = new RegFragment();
//    }
//
//    @Test
//    public void isEmptyTest() {
//        assertTrue(regFragment.isEmpty(""));
//        assertFalse(regFragment.isEmpty("Hello"));
//    }
//
//    @Test
//    public void isNotCompleteTest() {
//        assertTrue(regFragment.isNotComplete("ABC", "", "123", "123", "A", "B"));
//        assertFalse(regFragment.isNotComplete("ABC", "email", "123", "123", "A", "B"));
//    }
//
//    @Test
//    public void isValidEmailAddressTest() {
//        assertTrue(regFragment.isValidEmailAddress("email123@email.com"));
//        assertFalse(regFragment.isValidEmailAddress("Hello"));
//    }
//
//    @Test
//    public void validatePasswordLengthTest() {
//        assertTrue(regFragment.validatePasswordLength("12345678"));
//        assertTrue(regFragment.validatePasswordLength("123456789_abc"));
//        assertFalse(regFragment.validatePasswordLength("xyz?"));
//    }
//
//    @Test
//    public void passwordMatchTest() {
//        assertTrue(regFragment.passwordsMatch("pass_word", "pass_word"));
//        assertFalse(regFragment.passwordsMatch("pass_word", "qwerty7"));
//    }
//
//}
