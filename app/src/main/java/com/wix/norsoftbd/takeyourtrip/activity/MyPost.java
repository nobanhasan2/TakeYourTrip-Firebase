package com.wix.norsoftbd.takeyourtrip.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wix.norsoftbd.takeyourtrip.Interface.Itemclicklistener;
import com.wix.norsoftbd.takeyourtrip.R;
import com.wix.norsoftbd.takeyourtrip.common.common;
import com.wix.norsoftbd.takeyourtrip.model.post;
import com.wix.norsoftbd.takeyourtrip.viewholder.postViewholder;

public class MyPost extends AppCompatActivity {
    RecyclerView Mypost;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference Mypost_reference,user;
    FirebaseRecyclerAdapter<post,postViewholder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        database=FirebaseDatabase.getInstance();
        Mypost_reference=database.getReference("Posts");
        user=database.getReference("Users");
        Mypost =(RecyclerView)findViewById(R.id.recykler_MYpost);
        //     liscatagory.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(getApplicationContext());
        Mypost.setLayoutManager(layoutManager);
        loadMypost();
    }

    private void loadMypost() {
        adapter=new FirebaseRecyclerAdapter<post, postViewholder>(post.class,R.layout.post_recykler_layout,postViewholder.class,Mypost_reference.orderByChild("user").equalTo(common.currentUser)) {
            @Override
            protected void populateViewHolder(final postViewholder viewHolder, final post model, int position) {

                viewHolder.title.setText(model.getTitle());
                viewHolder.location.setText(model.getLocation());
                user.child(model.getUser()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("imageUrl").exists()) {
                            ;
                            Picasso.with(getApplicationContext()).load(dataSnapshot.child("imageUrl").getValue().toString()).into(viewHolder.profile_icon);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                viewHolder.setItemclicklistener(new Itemclicklistener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent i =new Intent(getApplicationContext(),EditActivity.class);
                         i.putExtra("unicode",model.getId());
                        i.putExtra("Title",model.getTitle());
                        i.putExtra("Discription",model.getDescription());
                        i.putExtra("User",model.getUser());
                        i.putExtra("Validity",model.getValidity());
                        i.putExtra("Email",model.getValidity());
                        i.putExtra("Phone",model.getValidity());
                        i.putExtra("Location",model.getLocation());
                        startActivity(i);
                    }
                });

            }
        };
        adapter.notifyDataSetChanged();
        Mypost.setAdapter(adapter);
    }
}
