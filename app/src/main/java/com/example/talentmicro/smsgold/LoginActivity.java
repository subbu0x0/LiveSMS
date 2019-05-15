package com.example.talentmicro.smsgold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Context context = LoginActivity.this;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.et_user_id)
    EditText etUserId;

    @Bind(R.id.et_password)
    EditText etPassword;

    @Bind(R.id.tv_signIn)
    TextView tvSignIn;

    @Bind(R.id.tv_signup)
    TextView tvSignUp;

    @Bind(R.id.tv_forgot_password)
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initToolbar();
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
        handleUiElement();
    }

    private void initToolbar() {
        try {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle(Html.fromHtml("SignIn"));
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
        tvSignIn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String email, pwd;
                                            email = etUserId.getText().toString();
                                            pwd = etPassword.getText().toString();
                                            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) {
                                                Toast.makeText(getApplicationContext(), "fields shouldn't be empty",
                                                        Toast.LENGTH_LONG).show();
                                            } else {
                                                mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (!task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Incorrect password and gmail or network failure",
                                                                    Toast.LENGTH_LONG).show();
                                                        } else {
                                                            Intent intent = new Intent(context, MainActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                            return;
                                                        }
                                                    }

                                                });
                                            }
                                        }
                                    });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email = etUserId.getText().toString();
               if(TextUtils.isEmpty(email)){
                   Toast.makeText(getApplicationContext(),"enter a registered email id",
                           Toast.LENGTH_SHORT).show();
               }
               else{
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Reset Link Sent Successfully",Toast.LENGTH_LONG)
                                    .show();
                        }
                            else {
                                Toast.makeText(getApplicationContext(),"invalid email or network error",Toast.LENGTH_LONG)
                                        .show();
                            }
                        }

                    });
               }
            }
        });
    }
}
