package com.dreamycoding.firebaseapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etUserName, etEmail;
    private Button btnAdd;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("server/saving-data/fireblog");

    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = new ArrayList<>();


        etUserName = (EditText) findViewById(R.id.etUserName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(btnAddClickListener);


    }

    View.OnClickListener btnAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNewUser(etUserName.getText().toString(), etEmail.getText().toString());
        }
    };

    private void addNewUser(String userName, String email) {
        DatabaseReference postsRef = ref.child("posts");

        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(new User(userName, email));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(MainActivity.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
