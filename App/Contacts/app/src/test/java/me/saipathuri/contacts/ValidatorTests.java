package me.saipathuri.contacts;

import org.junit.Before;
import org.junit.Test;

import me.saipathuri.contacts.utils.validators.EmailValidator;
import me.saipathuri.contacts.utils.validators.PhoneNumberValidator;

import static org.junit.Assert.*;

/**
 * Created by saipathuri on 12/4/17.
 */

public class ValidatorTests {
    private PhoneNumberValidator phoneNumberValidator;
    private EmailValidator emailValidator;
    @Before
    public void setup(){
        phoneNumberValidator = new PhoneNumberValidator();
        emailValidator = new EmailValidator();
    }

    @Test
    public void testValidPhoneNumbers(){
        String[] validPhoneNumbers = new String[]{"5129946923", "1234567890", "0987654321"};
        for (int i = 0; i < validPhoneNumbers.length; i++){
            String phoneNumber = validPhoneNumbers[i];
            assertTrue(phoneNumber, phoneNumberValidator.validate(phoneNumber));
        }
    }

    @Test
    public void testInvalidPhoneNumbers(){
        String[] invalidPhoneNumbers = new String[]{"5129946923012", "112323234567890", "098761321354321", "0", ""};
        for (int i = 0; i < invalidPhoneNumbers.length; i++){
            String phoneNumber = invalidPhoneNumbers[i];
            assertFalse(phoneNumber, phoneNumberValidator.validate(phoneNumber));
        }
    }

    @Test
    public void testValidEmails(){
        String[] validEmails = new String[]{"saipathuri@gmail.com", "test@utdallas.edu", "senator@whitehouse.gov"};
        for (int i = 0; i < validEmails.length; i++){
            String phoneNumber = validEmails[i];
            assertTrue(phoneNumber, emailValidator.validate(phoneNumber));
        }
    }

    @Test
    public void testInvalidEmails(){
        String[] invalidEmails = new String[]{"spam@spam@spam.com", "spam.", "spam.spammer", "", "abc"};
        for (int i = 0; i < invalidEmails.length; i++){
            String phoneNumber = invalidEmails[i];
            assertFalse(phoneNumber, emailValidator.validate(phoneNumber));
        }
    }
}
