package com.example.finalproject;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class testSignUpActivity {


    @Test
    public void isValidName(){

        assertTrue(SignUpActivity.isValidName("Zoraiz"));
        // since the the name Zoraiz99 contains numbers it is not valid username there it should return false
        assertFalse(SignUpActivity.isValidName("Zoraiz99"));
    }
    @Test

    public void isPasswordNotContainEmptySpace(){
        // since following password "Ottawa" does not contain a space therefore our method returns True
        assertTrue(SignUpActivity.isPasswordContainsEmptySpace("Ottawa"));
        // since the following pass Ottawa#12345 contains a space character therefore it is not a valid password, so the method returns a false
        assertFalse(SignUpActivity.isPasswordContainsEmptySpace("Ottawa#123@45 "));
    }
}


