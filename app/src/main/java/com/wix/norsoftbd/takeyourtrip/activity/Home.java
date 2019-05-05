package com.wix.norsoftbd.takeyourtrip.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.wix.norsoftbd.takeyourtrip.Interface.Itemclicklistener;
import com.wix.norsoftbd.takeyourtrip.R;
import com.wix.norsoftbd.takeyourtrip.common.common;
import com.wix.norsoftbd.takeyourtrip.model.post;
import com.wix.norsoftbd.takeyourtrip.utils.saveLoginSession;
import com.wix.norsoftbd.takeyourtrip.viewholder.postViewholder;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseRecyclerAdapter<post,postViewholder> adapter;
    NavigationView navigationView;
    RecyclerView liscatagory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference user,post_reference;
    public String image;
    String nameBar;
    MaterialEditText s;
    Button Search;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        database=FirebaseDatabase.getInstance();
        user=database.getReference("Users");
        post_reference=database.getReference("Posts");
        s= findViewById(R.id.editTextSearch);
       Search= findViewById(R.id.buttonSearch);
       tv=findViewById(R.id.Notfound);
        chekUser();

        SharedPreferences SP = getApplicationContext().getSharedPreferences("NAME", 0);

         common.currentUser=SP.getString("Name",null);
         common.userType=SP.getString("type",null);
         common.username=SP.getString("uname",null);



        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View headerView = navigationView.getHeaderView(0);
        final Menu nav_Menu = navigationView.getMenu();
        TextView nav_name=(TextView)headerView.findViewById(R.id.nav_name);



        if(common.userType!=null && common.userType.equals("User") ) {
            navigationView.getMenu().findItem(R.id.nav_drawer_post).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_Mypost).setVisible(false);
        }



        nav_name.setText(common.username);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#077a14"));
        setSupportActionBar(toolbar);

        liscatagory =(RecyclerView)findViewById(R.id.recykler_Allpost);
        //     liscatagory.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(getApplicationContext());
        liscatagory.setLayoutManager(layoutManager);

        loadPosts();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s.getText().toString().equals(""))
                {
                    loadPosts();
                }
                else
                loadSearchpost();
            }
        });
    }



    private void loadPosts() {
      adapter=new FirebaseRecyclerAdapter<post, postViewholder>(post.class,R.layout.post_recykler_layout,postViewholder.class,post_reference) {
      @Override
      protected void populateViewHolder(final postViewholder viewHolder, final post model, int position) {
            viewHolder.title.setText(model.getTitle());
            viewHolder.location.setText(model.getLocation());
          user.child(model.getUser()).addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                  if(dataSnapshot.child("imageUrl").exists()) {
                      image= dataSnapshot.child("imageUrl").getValue().toString();
                      Picasso.with(getApplicationContext()).load(image).into(viewHolder.profile_icon);

                  }
              }

              @Override
              public void onCancelled(DatabaseError databaseError) {

              }
          });


            viewHolder.setItemclicklistener(new Itemclicklistener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {

                 Intent i =new Intent(getApplicationContext(),DetailActivity.class);

                    i.putExtra("Title",model.getTitle());
                    i.putExtra("Discription",model.getDescription());
                    i.putExtra("User",model.getUser());
                    i.putExtra("Validity",model.getValidity());
                    i.putExtra("Phone",model.getPhone());
                    i.putExtra("Email",model.getEmail());
                    i.putExtra("Location",model.getLocation());
                    i.putExtra("Pic",image);

                    startActivity(i);
                }
            });

      }
  };
        adapter.notifyDataSetChanged();


        liscatagory.setHasFixedSize(false);
        liscatagory.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_drawer_home) {
            // Handle the camera action
        } else if (id == R.id.nav_drawer_profile) {
            Intent i =new Intent(Home.this,Profile.class);
            startActivity(i);

        } else if (id == R.id.nav_drawer_post) {
            user.child(common.currentUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.child("userType").getValue().equals("User")){
                        Toast.makeText(getApplicationContext(),"Sorry You are not a Dealer",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent i =new Intent(Home.this,PostDeals.class);
                        startActivity(i);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
        else if (id == R.id.nav_Logout) {
            saveLoginSession.saveSharedSetting(Home.this, "ClipCodes", "true");
            saveLoginSession.SharedPrefesSAVE(getApplicationContext(), "","","");
            Intent LogOut = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(LogOut);
            finish();
        }
        else if (id == R.id.nav_Mypost) {
           Intent i=new Intent(getApplicationContext(),MyPost.class);
           startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void chekUser(){
        Boolean Check = Boolean.valueOf(saveLoginSession.readSharedSetting(Home.this, "ClipCodes", "true"));

        Intent introIntent = new Intent(Home.this, MainActivity.class);
        introIntent.putExtra("ClipCodes", Check);

        if (Check) {
            startActivity(introIntent);
        }

    }



    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        Intent i = new Intent(Home.this,Home.class);
        startActivity(i);
        Toast.makeText(getApplicationContext(),"Refreshed",Toast.LENGTH_LONG).show();
    }
    private void loadSearchpost() {
        tv.setText("");
        adapter=new FirebaseRecyclerAdapter<post, postViewholder>(post.class,R.layout.post_recykler_layout,postViewholder.class,post_reference.orderByChild("title").equalTo(s.getText().toString())) {
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

                        Intent i =new Intent(getApplicationContext(),DetailActivity.class);

                        i.putExtra("Title",model.getTitle());
                        i.putExtra("Discription",model.getDescription());
                        i.putExtra("User",model.getUser());
                        i.putExtra("Validity",model.getValidity());
                        i.putExtra("Phone",model.getPhone());
                        i.putExtra("Email",model.getEmail());
                        i.putExtra("Location",model.getLocation());
                        i.putExtra("Pic",image);

                        startActivity(i);
                    }
                });
            }
        };
        if (adapter.getItemCount() == 0){
            tv.setText("Not Found!!");
        }

        adapter.notifyDataSetChanged();
        liscatagory.setAdapter(adapter);

    }



}
