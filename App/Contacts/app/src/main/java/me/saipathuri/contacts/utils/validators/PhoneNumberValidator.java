package me.saipathuri.contacts.utils.validators;

/**
 * Created by saipathuri on 12/4/17.
 */

public class PhoneNumberValidator implements BaseValidator {
    /**
     * Validates that phone number is in form (XXXXXXXXX)
     * @param s phone number to be validated
     * @return True if valid phone number. False if invalid phone number.
     */
    @Override
    public boolean validate(String s) {
        if (s.length() != 10){
            return false;
        }

        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++){
            if (Character.isLetter(chars[i]) || !Character.isDigit(chars[i]))
                return false;
        }
        return true;
    }
}
