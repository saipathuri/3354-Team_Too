package me.saipathuri.contacts;

import android.app.Application;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Created by saipathuri on 11/1/17.
 */

public class ContactsApp extends Application {

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();

        // do this once, for example in your Application class
        boxStore = MyObjectBox.builder().androidContext(ContactsApp.this).build();
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
