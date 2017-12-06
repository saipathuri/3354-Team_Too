package me.saipathuri.contacts;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import io.objectbox.BoxStore;
import me.saipathuri.contacts.MyObjectBox;

public abstract class AbstractObjectBoxTest {
    protected File boxStoreDir;
    protected BoxStore store;

    @Before
    public void setUp() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        File directory = appContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File tempFile = File.createTempFile("object-store-test", "", directory);
        tempFile.delete();
        boxStoreDir = tempFile;
        store = MyObjectBox.builder().directory(boxStoreDir).build();
    }

    @After
    public void tearDown() throws Exception {
        if (store != null) {
            store.close();
            store.deleteAllFiles();
        }
    }

}