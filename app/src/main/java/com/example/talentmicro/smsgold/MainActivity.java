package com.example.talentmicro.smsgold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    Context context = MainActivity.this;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.lv_list)
    RecyclerView lvMessageList;

    private ArrayList<ListDto> messageList;
    private MessageListAdapter messageListAdapter;
    String name,message;
    ArrayList<String> c,ch;
    DatabaseReference databaseReference1;
    int i;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        initializeUiElements();

    }

    private void initToolbar() {
        try {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle(Html.fromHtml(("Messages")));
            setSupportActionBar(mToolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setNavigationIcon(context.getResources().getDrawable(R.drawable.ic_action_back));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeUiElements() {
        messageList = new ArrayList<>();
        c = new ArrayList<>();
        ch = new ArrayList<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users");
        databaseReference1 = firebaseDatabase.getReference("messages");
        databaseReference.child(uid).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                        User user = childSnapshot.getValue(User.class);
                        if(!user.getName().equals(name)){
                            messageList.add(new ListDto(user.name,"",0));
                           // ch.add(user.name);
                            //c.add(name+"_"+user.name);
                        }
                }
                PopulateHotelList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void PopulateHotelList() {
        try {
            if (messageList != null && !messageList.isEmpty()) {
                messageListAdapter = new MessageListAdapter(context, messageList,name);
                lvMessageList.setLayoutManager(new LinearLayoutManager(context));
                lvMessageList.setAdapter(messageListAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.profile){
            Intent intent = new Intent(context, ProfileActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.logout){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
  /*  public void makeList(){

        for( i =0;i<ch.size();i++){
            databaseReference1.child(c.get(i)).orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            ListDto l = ds.getValue(ListDto.class);
                            message = l.getMessage();
                        }
                    }
                    else{
                        message = "";
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            messageList.add(new ListDto(ch.get(i),message,0));
        }
            PopulateHotelList();
    }*/
}
