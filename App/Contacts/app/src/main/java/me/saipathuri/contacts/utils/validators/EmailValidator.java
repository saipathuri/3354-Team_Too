package me.saipathuri.contacts.utils.validators;

import java.util.ArrayList;

/**
 * Created by saipathuri on 12/4/17.
 */

public class EmailValidator implements BaseValidator {
    private static ArrayList<String> acceptableTlds = new ArrayList<String>() {{
        add("gov");
        add("com");
        add("net");
        add("edu");
    }};
    /**
     * Validates that email is in form (x@x.tld)
     * @param s the email to be validated
     * @return true if email is valid, false if it is not
     */
    @Override
    public boolean validate(String s) {
        //split by . to verify end
        String[] tld_tokens = s.split("\\.");
        String tld = tld_tokens[tld_tokens.length-1];

        if(!acceptableTlds.contains(tld)){
            return false;
        }

        //split by @ to verify there is a domain
        String[] domain_tokens = s.split("\\@");
        if(domain_tokens.length != 2){
            return false;
        }

        return true;
    }
}
