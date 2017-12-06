package me.saipathuri.contacts;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import junit.framework.Assert.*;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import io.objectbox.Box;

import static org.junit.Assert.assertEquals;

/**
 * Created by haney on 12/6/2017.
 */

@RunWith(AndroidJUnit4.class)
public class GroupTest extends AbstractObjectBoxTest{

    @Test
    public void test(){
        assertEquals(true, true);

        // create contacts to add to group

        Box<Contact> contactBox = store.boxFor(Contact.class);

        ArrayList<Contact> contactList = new ArrayList<Contact>();

        Contact contact1 = new Contact();
        contact1.setFirstName("Sai");
        contact1.setLastName("Pathuri");
        long id = contactBox.put(contact1);
        contactList.add(contact1);

        Contact contact2 = new Contact();
        contact2.setFirstName("Haneya");
        contact2.setLastName("Khan");
        long id2 = contactBox.put(contact2);
        contactList.add(contact2);

        Contact contact3 = new Contact();
        contact3.setFirstName("Haleigh");
        contact3.setLastName("Rogers");
        long id3 = contactBox.put(contact3);
        contactList.add(contact3);

        Contact contact4 = new Contact();
        contact4.setFirstName("Trey");
        contact4.setLastName("Smith");
        long id4 = contactBox.put(contact4);
        contactList.add(contact4);


        // create group
        Box<Group> groupBox = store.boxFor(Group.class);
        Group group = new Group();
        group.setGroupName("TestGroup");
        long groupId = groupBox.put(group);

        // add relationship between groups and customer
        group.contactsRelation.add(contact1);
        group.contactsRelation.add(contact2);
        group.contactsRelation.add(contact3);
        group.contactsRelation.add(contact4);
        groupBox.put(group);

        Group groupFromBox = groupBox.get(groupId);

        // test if group was saved
        assertEquals("Group ID: ", group.getGroupName(), groupFromBox.getGroupName());


        // check if contacts are saved

        ArrayList<Contact> contactListFromBox = new ArrayList<Contact>();
        for (Contact eachContact: groupFromBox.contactsRelation){
            contactListFromBox.add(eachContact);
        }

        for (int x = 0; x < 4; x++) {
            Contact contactFromGroupFromBox = contactListFromBox.get(x);
            Contact contactInGroup = contactList.get(x);
            assertEquals("Contacts In Group: ", contactFromGroupFromBox.getFirstName(), contactInGroup.getFirstName());
        }
    }
}
