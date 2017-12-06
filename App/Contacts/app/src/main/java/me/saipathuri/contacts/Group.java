package me.saipathuri.contacts;

import java.util.ArrayList;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by haney on 12/2/2017.
 */

@Entity
public class Group {

    @Id
    long id;

    String groupName;

    ToMany<Contact> contactsRelation;

    public Group(long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public Group(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
