package com.example.reachme.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.reachme.Adapter.NotificationAdapter;
import com.example.reachme.Model.Notification;
import com.example.reachme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class Notification2Fragment extends Fragment {
    ShimmerRecyclerView recyclerView;
    ArrayList<Notification> list;
    FirebaseDatabase database;

    public Notification2Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
       }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notification2, container, false);

        recyclerView = view.findViewById(R.id.notification2RV);
        recyclerView.showShimmerAdapter();
        list = new ArrayList<>();
//        list.add(new Notification(R.drawable.profile2,"<b>Yash Sharma</b> Mentioned you in a comment : try again", "Just Now" ));
//        list.add(new Notification(R.drawable.cover2,"<b>Gopal Soni</b> Liked Your Picture", "3 hours" ));
//        list.add(new Notification(R.drawable.cover1,"<b>Rohit Varshney</b> Commented On Your Post : Better", "40 minutes ago" ));
//        list.add(new Notification(R.drawable.profile,"<b>Pawan Verma</b> Mentioned you in a comment : Nice Try", "2 hours" ));
//        list.add(new Notification(R.drawable.profile3,"<b>Yash Sharma</b> Mentioned you in a comment : try again", "15 minutes ago" ));
//        list.add(new Notification(R.drawable.post1,"<b>Gopal Sharma</b> Liked Your Post", "15 minutes ago" ));
//        list.add(new Notification(R.drawable.post2,"<b>Bhuvan Bam</b> Commented o you post : Look again", "5 hours" ));
//        list.add(new Notification(R.drawable.post3,"<b>Rohit Sharma</b> Mentioned you in a comment : See it an then talk...", "2 hours ago" ));
//        list.add(new Notification(R.drawable.profile3,"<b>Falana Kumar</b> Commented on your post : OP BOLTE!", "55 minutes ago" ));
//        list.add(new Notification(R.drawable.profile2,"<b>Topper Bhagel</b> Mentioned you in a comment : try again", "5 days ago" ));
//        list.add(new Notification(R.drawable.post1,"<b>Yash Sharma</b> Liked your post", "8 days ago" ));
//        list.add(new Notification(R.drawable.cover1,"<b>Rohit Varshney</b> Commented On Your Post : Better", "15 days ago" ));

        NotificationAdapter adapter = new NotificationAdapter(list,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);



        database.getReference()
                .child("notification")
                .child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Notification notification = dataSnapshot.getValue(Notification.class);
                            notification.setNotificationId(dataSnapshot.getKey());
                            list.add(notification);
                        }
                        recyclerView.setAdapter(adapter);
                        recyclerView.hideShimmerAdapter();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        return view;
    }
}
