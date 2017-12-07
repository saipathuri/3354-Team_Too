package me.saipathuri.contacts;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import junit.framework.Assert.*;
import org.junit.runner.RunWith;

import io.objectbox.Box;

import static org.junit.Assert.assertEquals;

/**
 * Created by saipathuri on 12/6/17.
 */

@RunWith(AndroidJUnit4.class)
public class ObjectBoxInstrumentedTest extends AbstractObjectBoxTest{

    @Test
    public void test(){
        assertEquals(true, true);
        Box<Contact> box = store.boxFor(Contact.class);
        Contact contact = new Contact();
        contact.setFirstName("Sai");
        contact.setLastName("Pathuri");
        long id = box.put(contact);

        Contact contactFromBox = box.get(id);
        assertEquals("First name: ", contact.getFirstName(), contactFromBox.getFirstName());
    }
}
