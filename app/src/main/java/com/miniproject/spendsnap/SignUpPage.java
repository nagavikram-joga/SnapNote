package com.miniproject.spendsnap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpPage extends AppCompatActivity {

    private EditText emailEditText,passwordEditText;
    private Button signUpButton;
    private FloatingActionButton fab;
    private TextView signInTextView;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private DatabaseReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        emailEditText = findViewById(R.id.signup_email);
        passwordEditText = findViewById(R.id.signup_password);
        signUpButton = findViewById(R.id.btn_update_password);
        signInTextView = findViewById(R.id.signup_signin_text);
        fab = findViewById(R.id.floatingActionButton_signup);

        firebaseAuth=FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(view -> {
            // Retrieve the user input from the EditText fields
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if(email.isEmpty() && password.isEmpty())
            {
                Toast.makeText(SignUpPage.this , "Please enter e-mail & password" , Toast.LENGTH_SHORT).show();
            }
            else if(email.isEmpty())
            {
                Toast.makeText(SignUpPage.this , "Please enter e-mail" , Toast.LENGTH_SHORT).show();
            }
            else if(password.isEmpty())
            {
                Toast.makeText(SignUpPage.this , "Please enter password" , Toast.LENGTH_SHORT).show();
            }
            else if(password.length()<7){
                Toast.makeText(SignUpPage.this , "Password should be greater than 7 characters." , Toast.LENGTH_SHORT).show();
            }
            else {

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext() , "Registration Successful" , Toast.LENGTH_SHORT).show();
                            sendEmailVerification();
                        }
                        else {
                            Toast.makeText(SignUpPage.this , "Failed to register or User already exists." , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        signInTextView.setOnClickListener(view -> finish());
        fab.setOnClickListener(view -> finish());
    }

    //send email for verification
    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(SignUpPage.this , "Verification e-mail is sent,verify to sign up successfully." , Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    Intent intent = new Intent(SignUpPage.this,LoginPage.class);
                    intent.putExtra("Email",emailEditText.getText().toString().trim());
                    intent.putExtra("Password", passwordEditText.getText().toString().trim());
                    finish();
                    startActivity(intent);
                }
            });
        }
        else {
            Toast.makeText(this , "Failed to send e-mail for verification" , Toast.LENGTH_SHORT).show();
        }
    }
}
