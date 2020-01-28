package in.dsc.instagram;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class CommonUtils {

    public static ArrayList<String> allImages=new ArrayList<>();


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void getPermission(Activity activity){

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };

        if (!hasPermissions(activity,PERMISSIONS)) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
        }

    }


    public static ArrayList<String> gettAllImages(Activity activity) {

        //Remove older images to avoid copying same image twice

        allImages.clear();
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null, imageName;

        //get all images from external storage
        uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA};
        cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            allImages.add(absolutePathOfImage);
        }

        // Get all Internal storage images
        uri = android.provider.MediaStore.Images.Thumbnails.INTERNAL_CONTENT_URI;
        cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            allImages.add(absolutePathOfImage);
        }

        return allImages;
    }

}
