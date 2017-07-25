package com.example.shubu.peb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bLogout,visbtn,outbtn,existEdit;
    EditText etname,etAge,etUsername,etCarNo;
    UserLocalStore userLocalStore;
    LEOvisitors vl,lv;
    String idKey;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCarNo=(EditText) findViewById(R.id.carNoentry);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etname = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        bLogout = (Button) findViewById(R.id.bLogout);
        visbtn = (Button) findViewById(R.id.addVisitbtn);
        existEdit = (Button) findViewById(R.id.edit_existing_btn);
        existEdit.setVisibility(View.INVISIBLE);
        outbtn = (Button) findViewById(R.id.stopVisitbtn);
        myRef = database.getReference();
        bLogout.setOnClickListener(this);
        visbtn.setOnClickListener(this);
        outbtn.setOnClickListener(this);
        existEdit.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }







    @Override
    protected void onStart(){
        super.onStart();
//        if (authenticate() == true){
//            displayUserDetails();
//        }else{
//            startActivity(new Intent(MainActivity.this,Login.class));
//        }

    }
    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }

    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();
        startActivity(new Intent(this,Admin.class));
        etUsername.setText(user.username);
        etname.setText(user.name);
        etAge.setText(user.age + "");

    }

    String id="";
    String enCarNo = "WB1254";
     int i = 1;
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(this, Login.class));
                break;

            case R.id.addVisitbtn:

                LeoService leo = new LeoService(this);
                LeoService.KeyRetrival(etCarNo.getText().toString(), new Leoface(){

                    @Override
                    public void getuser(LEOvisitors leOvisitors) {

                    }

                    @Override
                    public void getKey(final String key) {
                        if(key!=null) {

                            if(key!=null ) {
                                id=key;
                                Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
                                existEdit.setVisibility(View.VISIBLE);


                            }

                        }
                        else{
                            i=1;
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                            View view = inflater.inflate(R.layout.edit_det, null);
                            dialogBuilder.setView(view);
                            final EditText name = (EditText)view.findViewById(R.id.nameed);
                            final EditText carNo = (EditText)view.findViewById(R.id.carNoed);
                            final EditText purpos = (EditText)view.findViewById(R.id.purposeed);
                            final EditText intime = (EditText)view.findViewById(R.id.intimeed);
                            final Button sub = (Button)view.findViewById(R.id.submitbtn);
                            carNo.setText(etCarNo.getText().toString());
                            final AlertDialog dialog = dialogBuilder.create();
                            dialog.show();

                            sub.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(name.getText().toString().trim().equals("") ||
                                            purpos.getText().toString().trim().equals("")||
                                            intime.getText().toString().trim().equals(""))
                                    {

                                    }else {
                                        GregorianCalendar gcalendar = new GregorianCalendar();
                                        LeoService.addVisitors(
                                                new LEOvisitors(name.getText().toString(),
                                                        "sdjnjk",
                                                        "dsjhbjh",
                                                        "44656151",
                                                        purpos.getText().toString(),
                                                        intime.getText().toString(),
                                                        gcalendar.get(Calendar.DATE) + "/" + gcalendar.get(Calendar.DATE) + "/" + gcalendar.get(Calendar.DATE),
                                                        System.currentTimeMillis(),
                                                        carNo.getText().toString()

                                                ));
                                        dialog.cancel();
                                    }

                                }
                            });

                        }

                    }
                });


                break;


            case R.id.stopVisitbtn:
               // boolean b =ServerRequests.isVisitorExisted(getApplicationContext(),vl.carNo);
                break;

            case R.id.edit_existing_btn:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("Edit?");
                dialogBuilder.setMessage("Edit");
//                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
//                View view = inflater.inflate(R.layout.edit_det, null);
                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LeoService.fetchDetailsVisitors(id, new Leoface() {
                            @Override
                            public void getuser(LEOvisitors leOvisitors) {
                                LeoService.calDuOut(id,leOvisitors.inTimeInmilis);
                            }

                            @Override
                            public void getKey(String key) {

                            }
                        });
                    }
                });
                final AlertDialog dialog = dialogBuilder.create();
                dialog.show();

                break;



        }

    }


    public void showData()
    {

    }


}
