package com.example.shubu.peb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditUser extends AppCompatActivity {
    User user;
    Button bDelete;
    EditText etName,etAge,etUsername,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        user= (User) getIntent().getSerializableExtra("User");
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bDelete = (Button) findViewById(R.id.bDelete);
        etName.setText(user.name);
        etAge.setText(String.valueOf(user.age));
        etUsername.setText(user.username);
        etPassword.setText(user.password);

    }
    public void delete(View view){
        ServerRequests serverRequests=new ServerRequests(this);
        serverRequests.deleteUser(user, new GetUserCallback() {
            @Override
            public void done(LEOvisitors returnedUser) {

            }

            @Override
            public void done(User returnedUser) {

            }

            @Override
            public void done(String returnedUser) {
                //Toast.makeText(EditUser.this,returnedUser,Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
}
