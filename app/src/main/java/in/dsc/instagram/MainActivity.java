package in.dsc.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import in.dsc.instagram.adapter.GalleryAdapter;

import static in.dsc.instagram.CommonUtils.allImages;
import static in.dsc.instagram.CommonUtils.getPermission;

public class MainActivity extends AppCompatActivity {


    public static String TAG = "MainActivity";
    RecyclerView rv_gallery;
    ImageView iv_image_preview,iv_close;
    TextView tv_camera,tv_next;
    GridLayoutManager gridLayoutManager;
    GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getPermission(this);
        allImages = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            getPermission(this);
        } else {
            new MyGalleryAsy().execute();
        }



        tv_camera = findViewById(R.id.tv_camera);
        tv_next=findViewById(R.id.tv_next);
        iv_image_preview = findViewById(R.id.iv_preview);
        iv_close=findViewById(R.id.iv_close);
        rv_gallery = findViewById(R.id.galleryRV);
        gridLayoutManager = new GridLayoutManager(this, 3);
        rv_gallery.setLayoutManager(gridLayoutManager);
        galleryAdapter = new GalleryAdapter(this, allImages);
        rv_gallery.setAdapter(galleryAdapter);

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
                startActivity(intent);
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable drawable = (BitmapDrawable) iv_image_preview.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Intent intent=new Intent(MainActivity.this,NewPostActivity.class);
                intent.putExtra("image",byteArray);
                startActivity(intent);
            }
        });



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            new MyGalleryAsy().execute();
        }
    }


    public void OnItemClick(String path){
        Glide.with(this).load(new File(path)).into(iv_image_preview);
    }


    public class MyGalleryAsy extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;
        Bitmap mBitmap;

        @Override
        protected void onPreExecute() {
            //dialog = ProgressDialog.show(MainActivity.this, "", "Loading ...", true);
            //dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            CommonUtils.gettAllImages(MainActivity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(TAG, allImages.size() + "");
//            for(String s:allImages)
//                Log.d(TAG,s);


            for(String s:allImages)
                Log.d(TAG,s);

            galleryAdapter.notifyDataSetChanged();
            Glide.with(MainActivity.this).load(new File(allImages.get(0))).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_image_preview);
        }

    }



}
