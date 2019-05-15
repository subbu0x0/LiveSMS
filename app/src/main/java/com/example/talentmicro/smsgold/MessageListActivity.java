package com.example.talentmicro.smsgold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageListActivity extends AppCompatActivity {

    Context context = MessageListActivity.this;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.messages_view)
    ListView lvMessageList;

    @Bind(R.id.send)
    ImageButton send;

    @Bind(R.id.editText)
    EditText msg;

    private ArrayList<ListDto> messageList;
    private OpenChatActivityAdapter chatActivityAdapter;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference db1,db2;
    String user,friend;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        user = bundle.getString("uname");
        friend = bundle.getString("cname");
        db1 = firebaseDatabase.getReference("messages").child(user+"_"+friend);
        db2 = firebaseDatabase.getReference("messages").child(friend+"_"+user);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = msg.getText().toString();
                msg.setText("");

                if(!messageText.equals("")){
                    ListDto msg1 = new ListDto(user,messageText,System.currentTimeMillis());
                    db1.push().setValue(msg1);
                    db2.push().setValue(msg1);
                }
            }
        });
        initializeUiElements();
        initToolbar();
    }

    private void initToolbar() {
        try {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle(Html.fromHtml((friend)));
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
 /*       db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                    ListDto l = childSnapshot.getValue(ListDto.class);
                    messageList.add(l);
                }
                PopulateHotelList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
          db1.orderByChild("date").addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              ListDto l = dataSnapshot.getValue(ListDto.class);
              messageList.add(l);
              PopulateHotelList();
          }

          @Override
          public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

          }

          @Override
          public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

          }

          @Override
          public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

    }

    private void PopulateHotelList() {
        try {
            if (messageList != null && !messageList.isEmpty()) {
                chatActivityAdapter = new OpenChatActivityAdapter(context, R.layout.their_message, messageList);
                lvMessageList.setAdapter(chatActivityAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}