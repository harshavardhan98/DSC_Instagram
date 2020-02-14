package in.dsc.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import in.dsc.instagram.model.Post;

public class FeedsActivity extends AppCompatActivity {

    FirestoreRecyclerAdapter adapter;
    RecyclerView recyclerView;
    ImageView iv_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        getSupportActionBar().hide();

        iv_add=findViewById(R.id.iv_add);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FeedsActivity.this,MainActivity.class));
            }
        });


        recyclerView=findViewById(R.id.rv_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseFirestore.getInstance().collection("post").orderBy("timeStamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Post> options=new FirestoreRecyclerOptions.Builder<Post>().setQuery(query, new SnapshotParser<Post>() {
            @NonNull
            @Override
            public Post parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                Post post=new Post(snapshot.getString("userId"),snapshot.getString("caption"),snapshot.getString("photoUrl"));
                return post;
            }
        }).build();


        adapter=new FirestoreRecyclerAdapter<Post,FeedViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FeedViewHolder viewHolder, final int position, @NonNull final Post post) {
                Glide.with(FeedsActivity.this).load(post.getPhotoUrl()).into(viewHolder.iv_postImage);
                viewHolder.tv_caption.setText(post.getCaption());
            }

            @NonNull
            @Override
            public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feedrow,parent,false);
                return new FeedViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    public class FeedViewHolder extends RecyclerView.ViewHolder{

        TextView tv_caption;
        ImageView iv_postImage;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_caption=itemView.findViewById(R.id.tv_caption);
            iv_postImage=itemView.findViewById(R.id.iv_postImage);

        }
    }

}
