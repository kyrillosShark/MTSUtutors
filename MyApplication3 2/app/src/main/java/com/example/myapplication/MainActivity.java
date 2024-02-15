package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private EditText editUsername, editPassword, editEmail, editProfilePic;
    private Button btnSubmit;
    private TextView txtLoginfo;
    private Spinner accountType;
    boolean isSigningup = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);

        btnSubmit = findViewById(R.id.btnSubmit);
        txtLoginfo = findViewById(R.id.txtLoginInfo);
        Spinner accountType=(Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountType.setAdapter(myAdapter);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,FriendActivity.class));
            finish();

        }
        btnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editEmail.getText().toString().isEmpty()|| editPassword.getText().toString().isEmpty()){
                    if(isSigningup && editUsername.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this,"Invalid Input",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(isSigningup){
                   handleSignUp();

                }
                else{
                    handleLogin();
                }

            }
        });

        txtLoginfo.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view)
        {
            if(isSigningup)
            {
                isSigningup=false;
                editUsername.setVisibility(View.GONE);
                btnSubmit.setText("Log in");
                txtLoginfo.setText("Don't have an account? Sign up");
            }
            else{
                isSigningup=true;
                editUsername.setVisibility(View.VISIBLE);
                btnSubmit.setText("Sign up");
                txtLoginfo.setText("Already have an account? Log in");
            }
        }
        });

    }

    private void handleSignUp()
        {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString()).addOnCompleteListener((task) ->{
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User(editUsername.getText().toString(),"",editEmail.getText().toString()));
                    startActivity(new Intent(MainActivity.this,FriendActivity.class));
                    Toast.makeText( MainActivity.this,"Signed up successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            });

        }








 private void handleLogin(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this,FriendActivity.class));
                    Toast.makeText( MainActivity.this,"Logged in successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        });


    }


}









