package com.example.reachme.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.reachme.Adapter.PostAdapter;
import com.example.reachme.Adapter.StoryAdapter;
import com.example.reachme.ChatsActivity;
import com.example.reachme.Model.Post;
import com.example.reachme.Model.Story;
import com.example.reachme.Model.UserStories;
import com.example.reachme.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

//    RecyclerView ;
    ShimmerRecyclerView dashboardRV, storyRV;
    ArrayList<Story> storyList;
    ArrayList<Post> postList;
    ImageView addStory;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    RoundedImageView addStoryImage;
    ActivityResultLauncher<String> galleryLauncher;
    ProgressDialog dialog;
    CircleImageView chats;

    public HomeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home2, container, false);

        dashboardRV = view.findViewById(R.id.dashboardRV);
        dashboardRV.showShimmerAdapter();

        storyRV = view.findViewById(R.id.storyRV);
        storyRV.showShimmerAdapter();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Story Uploading");
        dialog.setMessage("Please Wait");
        dialog.setCancelable(false);


//        ============STORY=============



        storyList = new ArrayList<>();

        StoryAdapter adapter = new StoryAdapter(storyList,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        storyRV.setLayoutManager(linearLayoutManager);
        storyRV.setNestedScrollingEnabled(false);

        database.getReference()
                .child("stories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    storyList.clear();
                    for(DataSnapshot storySnapshot : snapshot.getChildren()){
                        Story story = new Story();
                        story.setStoryBy(storySnapshot.getKey());
                        story.setStoryAt(storySnapshot.child("postedBy").getValue(Long.class));

                        ArrayList<UserStories> stories = new ArrayList<>();
                        for (DataSnapshot snapshot1 : storySnapshot.child("userStories").getChildren()){
                            UserStories userStories = snapshot1.getValue(UserStories.class);
                            stories.add(userStories);
                        }
                        story.setStories(stories);
                        storyList.add(story);
                    }

                    storyRV.setAdapter(adapter);
                    storyRV.hideShimmerAdapter();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        ===========DASHBOARD===============

        postList = new ArrayList<>();

//        dashboardList.add(new Post(R.drawable.profile, R.drawable.post1, R.drawable.ic_bookmark_svgrepo_com,
//                "Rohit Varshney", "Traveler, Reader", "465", "224", "25"));
//        dashboardList.add(new Post(R.drawable.profile2, R.drawable.post3, R.drawable.ic_bookmark_svgrepo_com,
//                "Gopal Soni", "Reader", "235", "100", "15"));
//        dashboardList.add(new Post(R.drawable.profile3, R.drawable.post2, R.drawable.ic_bookmark_svgrepo_com,
//                "Yash Sharma", "Helper, Traveler", "235", "100", "15"));

        PostAdapter postAdapter = new PostAdapter(postList, getContext());
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        dashboardRV.setLayoutManager(layoutManager1);
        dashboardRV.setNestedScrollingEnabled(false);


        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    post.setPostId(dataSnapshot.getKey());
                    postList.add(post);
                }
                dashboardRV.setAdapter(postAdapter);
                dashboardRV.hideShimmerAdapter();
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        chats = view.findViewById(R.id.profile_image);
        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iNext = new Intent(getContext(), ChatsActivity.class);
                startActivity(iNext);
            }
        });
        addStoryImage = view.findViewById(R.id.addStoryImage);
        addStoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              galleryLauncher.launch("image/*");
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent()
                , new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        addStoryImage.setImageURI(result);
                        dialog.show();
                        final StorageReference reference = storage.getReference()
                                .child("stories")
                                .child(FirebaseAuth.getInstance().getUid())
                                .child(new Date().getTime()+ "");
                        reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Story story= new Story();
                                        story.setStoryAt(new Date().getTime());
                                        database.getReference()
                                        .child("stories")
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .child("postedBy")
                                        .setValue(story.getStoryAt()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                UserStories stories = new UserStories(uri.toString(), story.getStoryAt());
                                                database.getReference()
                                                        .child("stories")
                                                        .child(FirebaseAuth.getInstance().getUid())
                                                        .child("userStories")
                                                        .push()
                                                        .setValue(stories).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });

                    }
                });


        return view;

    }
}
