package com.example.reachme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachme.CommentActivity;
import com.example.reachme.Model.Notification;
import com.example.reachme.Model.User;
import com.example.reachme.R;
import com.example.reachme.databinding.Notification2sampleBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder>{

    ArrayList<Notification> list;
    Context context;

    public NotificationAdapter(ArrayList<Notification> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.notification2sample, viewGroup, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Notification notification = list.get(i);

        String type = notification.getType();


                FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(notification.getNotificationBy())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfilePhoto())
                                .placeholder(R.drawable.cover_placeholder)
                                .into(viewHolder.binding.profileImage);
                        if (type.equals("like")){
                            viewHolder.binding.notification.setText(Html.fromHtml("<b>" + user.getName() + "</b>" + " Liked your post"));
                        }else if (type.equals("comment")){
                            viewHolder.binding.notification.setText(Html.fromHtml("<b>" + user.getName() + "</b>" + " Commented on your post"));
                        }else {
                            viewHolder.binding.notification.setText(Html.fromHtml("<b>" + user.getName() + "</b>" + " Starts following you"));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                viewHolder.binding.openNotification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!type.equals("follow")){

                            FirebaseDatabase.getInstance().getReference()
                                    .child("notification")
                                    .child(notification.getPostedBy())
                                    .child(notification.getNotificationId())
                                    .child("checkOpen")
                                    .setValue(true);

                            viewHolder.binding.openNotification.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            Intent intent = new Intent(context, CommentActivity.class);
                            intent.putExtra("postId", notification.getPostId());
                            intent.putExtra("postedBy", notification.getPostedBy());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }


                    }
                });
//                Boolean checkOpen = notification.isCheckOpen();
//                if (checkOpen == true){
//                    viewHolder.binding.openNotification.setBackgroundColor(Color.parseColor("#FFF"));
//                }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        Notification2sampleBinding binding;

        public viewHolder(@NonNull View itemView) {

            super(itemView);
            binding = Notification2sampleBinding.bind(itemView);
        }
    }

}
