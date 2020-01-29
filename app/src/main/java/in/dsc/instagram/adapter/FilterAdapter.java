package in.dsc.instagram.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.dsc.instagram.PreviewActivity;
import in.dsc.instagram.R;
import in.dsc.instagram.model.FilterImage;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyHolder>{

    ArrayList<FilterImage> filterArrayList;


    public FilterAdapter(ArrayList<FilterImage> filterArrayList){
        this.filterArrayList = filterArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.previewrow,parent,false);
        MyHolder myHolder=new MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final FilterImage thumbnailItem=filterArrayList.get(position);

        if(thumbnailItem.getImage()!=null){

            try{
                //final Bitmap mutableBitmap=Bitmap.createScaledBitmap(thumbnailItem.image,100,100,false);
                holder.tv_filer_type.setText(thumbnailItem.getFilterName());
                holder.iv_filter_preview.setImageBitmap(thumbnailItem.getImage());

                holder.iv_filter_preview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //filterImageClick.onImageClick(thumbnailItem.getImage());
                        ((PreviewActivity)view.getContext()).OnImageClick(thumbnailItem.getImage());

                    }
                });

            }catch (Exception e){
                Log.d("bitmap",e.getMessage());
                e.printStackTrace();
            }

        }


    }

    @Override
    public int getItemCount() {
        return filterArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView iv_filter_preview;
        TextView tv_filer_type;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            iv_filter_preview=itemView.findViewById(R.id.iv_filter_preview);
            tv_filer_type=itemView.findViewById(R.id.tv_filterType);
        }
    }
}
