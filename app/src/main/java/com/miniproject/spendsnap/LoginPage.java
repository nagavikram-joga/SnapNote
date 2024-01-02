package com.miniproject.spendsnap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginPage extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private TextView signUpTextView,forgotPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page); // Replace with your layout file name

        emailEditText = findViewById(R.id.fp_UsernameOrPassword);
        passwordEditText = findViewById(R.id.fp_password);
        signInButton = findViewById(R.id.fp_sign_in_btn);
        signUpTextView = findViewById(R.id.sign_in_txt);
        forgotPassword=findViewById(R.id.lp_fp);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            finish();
            startActivity(new Intent(LoginPage.this, MainActivity.class));
        }
        String mail=(String)getIntent().getStringExtra("Email");
        String password1 = (String)getIntent().getStringExtra("Password");
        this.emailEditText.setText(mail);
        this.passwordEditText.setText(password1);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this,ForgotPassword.class);
                intent.putExtra("emailFromLoginPage",emailEditText.getText().toString().trim());
                startActivity(intent);
            }
        });

        // Set a click listener for the "Sign In" button
        signInButton.setOnClickListener(view -> {
            // Retrieve the user input from the EditText fields
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if(email.isEmpty() && password.isEmpty())
            {
                Toast.makeText(LoginPage.this , "Enter your e-mail & password" , Toast.LENGTH_SHORT).show();
            }
            else if(email.isEmpty())
            {
                Toast.makeText(LoginPage.this , "Enter your e-mail" , Toast.LENGTH_SHORT).show();
            }
            else if(password.isEmpty()) {
                Toast.makeText(LoginPage.this , "Enter your password" , Toast.LENGTH_SHORT).show();
            }
            else{
                //Login the user
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            checkMailVerification();
                        }
                        else {
                            Toast.makeText(LoginPage.this , "Account doesn't exist." , Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }

        });

        // Set a click listener for the "Sign Up" TextView
        signUpTextView.setOnClickListener(view -> startActivity(new Intent(LoginPage.this, SignUpPage.class)));
    }

    private void checkMailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified())
        {
            Toast.makeText(this , "Logged In Successfully." , Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LoginPage.this, MainActivity.class));
        }
        else{
            Toast.makeText(this , "Verify your mail first." , Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
