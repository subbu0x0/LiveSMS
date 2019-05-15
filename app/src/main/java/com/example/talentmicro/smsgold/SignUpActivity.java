package com.example.talentmicro.smsgold;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    Context context = SignUpActivity.this;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.et_mobile_no)
    EditText etMobileNo;

    @Bind(R.id.et_name)
    EditText etName;

    @Bind(R.id.et_email)
    EditText etEmail;

    @Bind(R.id.et_pwd)
    EditText etPwd;

    @Bind(R.id.et_confm_pwd)
    EditText etConfirmPassword;

    @Bind(R.id.submit)
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        initToolbar();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile_no,name,email,pwd,c_pwd;
                mobile_no = etMobileNo.getText().toString();
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                pwd = etPwd.getText().toString();
                c_pwd = etConfirmPassword.getText().toString();
                if(TextUtils.isEmpty(mobile_no)||TextUtils.isEmpty(name)||TextUtils.isEmpty(email)
                        ||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(c_pwd)){
                    Toast.makeText(getApplicationContext(),"All fields must be filled",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!pwd.equals(c_pwd)){
                        Toast.makeText(getApplicationContext(),"Passwords donot match",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        final DatabaseReference dbRef = firebaseDatabase.getReference("users");
                        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"User Already Exists or Email doesn't exist",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String uid = mAuth.getCurrentUser().getUid();
                                    DatabaseReference dbref1 = dbRef.child(uid);
                                    User user = new User(name,uid,email,mobile_no);
                                    dbRef.child(uid).setValue(user);
                                    //mAuth.signOut();
                                    Toast.makeText(getApplicationContext(),"Registration Successfull",
                                            Toast.LENGTH_LONG).show();
                                    etMobileNo.setText("");
                                    etConfirmPassword.setText("");
                                    etEmail.setText("");
                                    etName.setText("");
                                    etPwd.setText("");
                                    etConfirmPassword.setText("");
                                }
                            }
                        });

                    }
                }
            }
        });
        handleUiElement();
    }

    private void initToolbar() {
        try {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle(Html.fromHtml("SignUp"));
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

    private void handleUiElement() {

    }

}
