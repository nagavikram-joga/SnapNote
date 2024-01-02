package com.miniproject.spendsnap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editnoteactivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    Intent data;
    EditText mtitle,mcontent;
    FloatingActionButton msaveeditnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnoteactivity);

        mtitle=findViewById(R.id.editTitleOfNote);
        mcontent=findViewById(R.id.editcontentofnote);
        msaveeditnote=findViewById(R.id.saveeditnote);
        data=getIntent();

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar=findViewById(R.id.toolbarofeditnote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String noteTitle = data.getStringExtra("title");
        String noteContent = data.getStringExtra("content");
        mcontent.setText(noteContent);
        mtitle.setText(noteTitle);

        msaveeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save the note

                String newTitle=mtitle.getText().toString();
                String newContent=mcontent.getText().toString();
                if(newContent.isEmpty()|| newTitle.isEmpty())
                {
                    Toast.makeText(editnoteactivity.this , "Something is empty" , Toast.LENGTH_SHORT).show();
                }
                else{
                    DocumentReference documentReference =firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(data.getStringExtra("noteId"));
                    Map<String,Object> note=new HashMap<>();
                    note.put("title",newTitle);
                    note.put("content",newContent);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(editnoteactivity.this , "Note is updated successfully" , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(editnoteactivity.this, MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(editnoteactivity.this , "Failed to Update" , Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            startActivity(new Intent(editnoteactivity.this, MainActivity.class));
            finish();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}