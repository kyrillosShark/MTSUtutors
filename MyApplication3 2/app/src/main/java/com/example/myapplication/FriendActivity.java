package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myapplication.UsersAdapter.OnUserClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<User>users;
    private ProgressBar progressBar;
    private UsersAdapter usersAdapter;
    OnUserClickListener onUserClickListener;

    private SwipeRefreshLayout swipeRefreshLayout;

    String myImageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        progressBar= findViewById(R.id.progressBar);
        users=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler);
        swipeRefreshLayout=findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        onUserClickListener=new UsersAdapter.OnUserClickListener(){
            @Override
            public void onUserCLicked(int position) {
                startActivity(new Intent(FriendActivity.this,MessageActivity.class)
                       .putExtra("username_of_roommate",users.get(position).getUsername())
                        .putExtra( "email_of_roommate",users.get(position).getEmail())
                       .putExtra( "img_of_roommate",users.get(position).getProfilePicture())
                       .putExtra("my_img",myImageURL)
                );


            }

        };
        getUsers();

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.profilemenu,menu);
    return true;}

    @Override

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_item_profile)
        {
            startActivity(new Intent(FriendActivity.this,Profile.class));
        }
        return super.onOptionsItemSelected(item);
    }
    private void getUsers(){
        users.clear();
        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    users.add(dataSnapshot.getValue(User.class));
                }
                usersAdapter= new UsersAdapter(users,FriendActivity.this,onUserClickListener);
                recyclerView.setLayoutManager(new LinearLayoutManager(FriendActivity.this));
                recyclerView.setAdapter(usersAdapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                for(User user: users){
                    if(user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        myImageURL = user.getProfilePicture();
                        return;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
