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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText email;
    private TextView tv1;
    private FloatingActionButton fab1;
    private Button recover_btn;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        //getSupportActionBar().hide();

        email=findViewById(R.id.fp_UsernameOrPassword);
        recover_btn=findViewById(R.id.fp_sign_in_btn);
        fab1 = findViewById(R.id.floatingActionButton_fp);
        tv1=findViewById(R.id.txt_fp);
        firebaseAuth=FirebaseAuth.getInstance();

        String mailvalfromLoginPge =(String)getIntent().getStringExtra("emailFromLoginPage");
        this.email.setText(mailvalfromLoginPge);

        recover_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                if(mail.isEmpty())
                {
                    Toast.makeText(ForgotPassword.this , "Enter your e-mail first" , Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ForgotPassword.this , "Mail is sent.You can recover your password using mail." , Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this, LoginPage.class));
                            }
                            else {
                                Toast.makeText(ForgotPassword.this , "Email is  not registered." , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
            }
        });

        fab1.setOnClickListener(view -> finish());
        tv1.setOnClickListener(view-> finish());
    }
}
