package me.saipathuri.contacts;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import junit.framework.Assert.*;
import org.junit.runner.RunWith;

import io.objectbox.Box;

import static org.junit.Assert.assertEquals;

/**
 * Created by Millie Black on 12/6/2017.
 */

@RunWith(AndroidJUnit4.class)
public class BlacklistTest extends AbstractObjectBoxTest{

    @Test
    public void test(){
        assertEquals(true,true);
        Box<Contact> box = store.boxFor(Contact.class);
        Contact contact = new Contact();
        contact.setFirstName("Trey");
        contact.setLastName("Smith");
        contact.setPhoneNumber1("5555555555");
        contact.setBlacklisted(true);

        long id = box.put(contact);

        Contact contactFromBox = box.get(id);
        assertEquals("Is  ", contact.isBlacklisted(), contactFromBox.isBlacklisted());
    }

}
