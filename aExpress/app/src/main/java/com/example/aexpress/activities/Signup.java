package com.example.aexpress.activities;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aexpress.R;
import com.example.aexpress.databinding.ActivityCartBinding;
import com.example.aexpress.databinding.ActivitySignupBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Signup extends AppCompatActivity {
    ActivitySignupBinding binding;

    EditText  etEmail, etUserId,etPassword, etReEnterPassword;
    TextView tvToggleLoginSignUp, tvTitleTxt, tvHaveAccount;
    ImageView googleButton;
    private FirebaseAuth mAuth;
    private GoogleSignInClient client;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Button registerLoginBtn = (Button) findViewById(R.id.registerLoginBtn);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etReEnterPassword = (EditText) findViewById(R.id.reEnterPassword);
        tvTitleTxt = (TextView) findViewById(R.id.titleTxt);
        tvHaveAccount = (TextView) findViewById(R.id.haveAccount);
        tvToggleLoginSignUp = (TextView) findViewById(R.id.toggleLoginSignUp);
        googleButton = (ImageView) findViewById(R.id.btnGoogle);
        mAuth = FirebaseAuth.getInstance();

        tvToggleLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = tvToggleLoginSignUp.getText().toString();
                if(txt.equalsIgnoreCase("Login")){
                    etReEnterPassword.setVisibility(View.GONE);
                    tvTitleTxt.setText("Login");
                    tvHaveAccount.setText("Don't have an account?");
                    tvToggleLoginSignUp.setText("Sign-up");
                    registerLoginBtn.setText("Login");
                }else{
                    etReEnterPassword.setVisibility(View.VISIBLE);
                    tvTitleTxt.setText("Sign Up");
                    tvHaveAccount.setText("Already have an account?");
                    tvToggleLoginSignUp.setText("Login");
                    registerLoginBtn.setText("Register");
                }
            }
        });

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               // .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this,options);


        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i,1234);
            }
        });

        registerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn = registerLoginBtn.getText().toString();
                if(btn.equalsIgnoreCase("Login")){
                    String email = String.valueOf(etEmail.getText());
                    String password = String.valueOf(etPassword.getText());
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(Signup.this, "Please fill out all login fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Signup.this, "login successful",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(Signup.this, ProfileActivity.class);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Toast.makeText(Signup.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }


                } else{
                    String email = String.valueOf(etEmail.getText());
                    String password = String.valueOf(etPassword.getText());
                    String RePassword = String.valueOf(etReEnterPassword.getText());
                    if (email.isEmpty() || password.isEmpty()  || RePassword.isEmpty()) {
                        Toast.makeText(Signup.this, "Please fill out all signup fields", Toast.LENGTH_SHORT).show();
                    }else if (!password.equals(RePassword)) {
                        Toast.makeText(Signup.this, "Passwords Not Match !!", Toast.LENGTH_SHORT).show();
                    } else {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Signup.this, "Account Create Successful.",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(Signup.this, ProfileActivity.class);

                                            startActivity(i);
                                            finish();

                                        } else {
                                            Toast.makeText(Signup.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                                    startActivity(intent);

                                }else {
                                    Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }

    }

}