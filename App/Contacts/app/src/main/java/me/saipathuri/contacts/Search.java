package me.saipathuri.contacts;

/**
 * Created by Haleigh Rogers on 11/12/2017.
 */

public class Search
{
    //first name used for search
    String searchName;

    //TODO: getting the search name from the activity_main
    //public String getSearchName() { return searchName; }

    //TODO: passing the first name for the contacts into the nameFound function
    //search for the contact by the first name an call contact card
    public static boolean nameFound(String searchName, String[] contactNames)
    {
        for(int count = 0; count < contactNames.length; count++)
        {
            if(searchName.contains(contactNames[count]))
                return true;
        }
        return false;
    }

    //TODO: make the call for this function after search view in the activity_main
    //TODO: has entered in a string and return message if not found and calling up the contact card
    //TODO: if is found in the search.

}
