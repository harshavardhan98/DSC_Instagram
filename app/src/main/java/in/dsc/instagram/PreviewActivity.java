package in.dsc.instagram;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.util.ArrayList;
import java.util.List;

import in.dsc.instagram.adapter.FilterAdapter;
import in.dsc.instagram.model.FilterImage;

public class PreviewActivity extends AppCompatActivity {

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    ImageView iv_preview;
    ImageView iv_back;
    RecyclerView rv_filterList;
    Bitmap bitmap;
    ArrayList<FilterImage> thumbnailItemArrayList;
    FilterAdapter filterAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        bitmap=PhotoActivity.capturedBmp;

        iv_preview=findViewById(R.id.iv_preview);
        iv_preview.setImageBitmap(bitmap);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        thumbnailItemArrayList=new ArrayList<>();
        rv_filterList=findViewById(R.id.rv_filterList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rv_filterList.setLayoutManager(linearLayoutManager);
        filterAdapter=new FilterAdapter(thumbnailItemArrayList);
        rv_filterList.setAdapter(filterAdapter);

        new loadFilterTask().execute();

    }

    public class loadFilterTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            List<Pair<String, Filter>> filters = new ArrayList<>();
            filters.add(new Pair<>("Vibe", SampleFilters.getAweStruckVibeFilter()));
            filters.add(new Pair<>("BlueMess", SampleFilters.getBlueMessFilter()));
            filters.add(new Pair<>("LimeStutter", SampleFilters.getLimeStutterFilter()));
            filters.add(new Pair<>("NightWhisper", SampleFilters.getNightWhisperFilter()));
            filters.add(new Pair<>("StarLit", SampleFilters.getStarLitFilter()));

            for (Pair<String,Filter> f : filters) {
                String filterName=f.first;
                Filter filter=f.second;
                try{
                    Bitmap temp=bitmap.copy(Bitmap.Config.ARGB_8888,true);
                    final Bitmap bmp=filter.processFilter(temp);
                    FilterImage item = new FilterImage(bmp,filter,filterName);
                    thumbnailItemArrayList.add(item);
                }catch (Exception e){
                    Log.d("bitmap",e.getMessage());
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            filterAdapter.notifyDataSetChanged();
        }
    }



    public void OnImageClick(Bitmap bitmap){
        iv_preview.setImageBitmap(bitmap);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
