package in.dsc.instagram.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.ArrayList;

import in.dsc.instagram.MainActivity;
import in.dsc.instagram.R;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{


    Context context;
    ArrayList<String> imageList;

    public GalleryAdapter(Context context, ArrayList<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.galleryrow,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        try{

            Glide.with(context)
                    .load(new File(imageList.get(position)))
                    .override(Target.SIZE_ORIGINAL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_image);

            holder.iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //galleryImageClick.onImageClick(imageList.get(position));
                    ((MainActivity)view.getContext()).OnItemClick(imageList.get(position));
                }
            });

        }catch (Exception e){
            Log.d("adapter",e.toString());
        }


    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image=itemView.findViewById(R.id.iv_image);
        }
    }


}
