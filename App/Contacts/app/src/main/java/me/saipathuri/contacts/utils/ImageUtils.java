package me.saipathuri.contacts.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.saipathuri.contacts.R;

/**
 * Created by saipathuri on 11/3/17.
 */

public class ImageUtils {

    public static Bitmap getBitmapFromPath(Context context, ImageButton imageButton, String photoPath){
        if(photoPath != null && !photoPath.isEmpty()) {
            return scalePic(imageButton, photoPath);
        }else{
            return BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        }


    }
    //from: https://developer.android.com/training/camera/photobasics.html
    private static Bitmap scalePic(ImageButton imageButton, String photoPath) {
        // Get the dimensions of the View
        int targetW = imageButton.getWidth();
        int targetH = imageButton.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        // On startup, the imageviews are not rendered so scale factor is 0.
        int scaleFactor = 0;
        if(targetH != 0 && targetW != 0) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
        return bitmap;
    }

    //from: https://developer.android.com/training/camera/photobasics.html
    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
}
