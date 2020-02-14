package in.dsc.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class NewPostActivity extends AppCompatActivity {

    ImageView iv_thumbnail;
    Bitmap bmp;
    TextView tv_share;
    EditText ed_caption;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();


        iv_thumbnail=findViewById(R.id.iv_thumbnail);
        tv_share=findViewById(R.id.tv_share);
        ed_caption=findViewById(R.id.ed_caption);

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        iv_thumbnail.setImageBitmap(bmp);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            double latitude=location.getLatitude();
                            double longitude=location.getLongitude();

                        }
                    }
                });

        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(NewPostActivity.this, "image upload started", Toast.LENGTH_SHORT).show();
                uploadImage(bmp);
                //startActivity(new Intent(NewPostActivity.this,FeedsActivity.class));
            }
        });

    }


    public void uploadImage(Bitmap bitmap){


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference storageRef= FirebaseStorage.getInstance().getReference();
        final StorageReference photoRef = storageRef.child("photos"+ Calendar.getInstance().getTimeInMillis() +".jpg");
        final UploadTask uploadTask = photoRef.putBytes(data);

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("dsc_insta","photo upload successful");
                    }
                });

            }
        });

    }
}
