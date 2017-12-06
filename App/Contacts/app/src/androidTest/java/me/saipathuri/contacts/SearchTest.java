package me.saipathuri.contacts;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;
import static org.junit.Assert.assertEquals;

/**
 * Created by Haleigh Rogers on 12/6/2017.
 */

@RunWith(AndroidJUnit4.class)
public class SearchTest extends AbstractObjectBoxTest {

    @Test
    public void searchTest() {

        assertEquals(true, true);
        String queryTerm = "Haleigh";
        Box<Contact> box = store.boxFor(Contact.class);

        //setting a contact
        Contact contact = new Contact();
        contact.setFirstName("Sai");
        contact.setLastName("Pathuri");
        long id = box.put(contact);

        //setting another contact
        Contact contact2 = new Contact();
        contact2.setFirstName("Haleigh");
        contact2.setLastName("Rogers");
        long id2 = box.put(contact2);

        Contact contactFromBox = box.get(id);
        Contact contact2FromBox = box.get(id2);

        if(queryTerm == (contact.getFirstName()))
        {
            assertEquals("Search name: ", contact.getFirstName(), contactFromBox.getFirstName());
        }
        else if(queryTerm ==(contact.getLastName()))
        {
            assertEquals("Search name: ", contact.getLastName(), contactFromBox.getLastName());
        }
        else if(queryTerm == (contact2.getFirstName()))
        {
            assertEquals("Search name: ", contact2.getFirstName(), contact2FromBox.getFirstName());
        }
        else if(queryTerm ==(contact2.getLastName()))
        {
            assertEquals("Search name: ", contact2.getLastName(), contact2FromBox.getLastName());
        }

    }
}
