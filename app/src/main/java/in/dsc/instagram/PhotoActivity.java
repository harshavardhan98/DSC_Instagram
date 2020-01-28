package in.dsc.instagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;

public class PhotoActivity extends AppCompatActivity {



    public static Bitmap capturedBmp=null;
    CameraView camera;
    ImageView btn_capture;
    TextView tv_gallery;
    ImageView iv_close;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btn_capture=findViewById(R.id.iv_capture);
        iv_close=findViewById(R.id.iv_close);
        tv_gallery=findViewById(R.id.tv_gallery);
        tv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        camera = findViewById(R.id.camera);
        camera.setLifecycleOwner(this);





        camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(PictureResult result) {
                result.toBitmap(1024, 1024, new BitmapCallback() {
                    @Override
                    public void onBitmapReady(@Nullable Bitmap bitmap) {

                        try{
                            capturedBmp=bitmap;
                            //startActivity(new Intent(PhotoActivity.this,PreviewActivity.class));
                        }catch (Exception e){
                            Log.d("bitmap",e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.destroy();
    }
}
