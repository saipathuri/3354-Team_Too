package me.saipathuri.contacts.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

import me.saipathuri.contacts.R;

/**
 * Created by saipathuri on 11/3/17.
 */

public class ImageUtils {

    public static Bitmap getBitmapFromPath(Context context, String photoPath){
        if(photoPath != null && !photoPath.isEmpty()) {
            File sd = Environment.getExternalStorageDirectory();
            File image = new File(sd + photoPath);
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bitmapOptions);
            //bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
            return bitmap;
        }else{
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            return bitmap;
        }
    }
}
