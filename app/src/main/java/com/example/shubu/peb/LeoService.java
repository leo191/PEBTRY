package com.example.shubu.peb;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 24/07/17.
 */

public class LeoService {
    static ProgressDialog progressDialog;

    static Context context;
    static String Key;
    private static LEOvisitors retLEOvisitors;

    public String getKey() {
        return Key;
    }


    public LeoService(Context context)
    {
        progressDialog = new ProgressDialog(context);
        this.context=context;
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait..");
    }

    public void setKey(String key) {
        Key = key;
    }



    public static void  addVisitors(LEOvisitors vl){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Visi");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Visi");

        String id = databaseReference.push().getKey();
        databaseReference.child(id).setValue(vl);

    }

    public static void KeyRetrival(String carno,Leoface callback)
    {
            progressDialog.show();
            new checkVis(carno,callback).execute();

    }




    static LEOvisitors existed=null;

    public  static  void fetchDetailsVisitors(String key,Leoface callback)
    {
        progressDialog.show();
        new fetchUserData(key,callback).execute();



    }



    public static void calDuOut(String id,long millis)
    {
        String path = "Visi/"+id;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(path);


        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis)-TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis()),
                (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)))
                        -(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis()))),
                (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))-
                        (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()))));


        databaseReference.child("duration").setValue(hms);
        GregorianCalendar gc = new GregorianCalendar();
        databaseReference.child("outTime").setValue(gc.get(Calendar.HOUR)+":"+gc.get(Calendar.MINUTE)+":"+gc.get(Calendar.SECOND));



    }





    public static class checkVis extends AsyncTask<Void,Void,LEOvisitors>
    {
        String so =null;

        String  rt;
        String k;
        String carNo;
        Leoface callback;
        public checkVis(String carNo, Leoface callback){
            this.carNo = carNo;
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected LEOvisitors doInBackground(Void... leOvisitorses) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Visi");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot dp:dataSnapshot.getChildren()
                            ) {
                        retLEOvisitors = dp.getValue(LEOvisitors.class);
                        if(retLEOvisitors.carNo.equals(carNo))
                        {
                            so = dp.getKey();

                            //Toast.makeText(context, dp.child("duration").getValue().toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                            break;
                        }
                    }

                   callback.getKey(so);

                }



                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }



        @Override
        protected void onPostExecute(LEOvisitors rt) {
            progressDialog.cancel();

            super.onPostExecute(rt);
        }




    }





    public static class fetchUserData extends AsyncTask<Void,Void,Void>
    {
        String k;
        Leoface callback;
        public fetchUserData(String key,Leoface cal){k=key;callback=cal;}
        @Override
        protected Void doInBackground(Void... voids) {

            String path = "Visi/"+k;
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(path);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    existed = dataSnapshot.getValue(LEOvisitors.class);
                    callback.getuser(existed);
                    progressDialog.cancel();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            return null;
        }
    }





}





