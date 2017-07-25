package com.example.shubu.peb;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by shubu on 6/22/2017.
 */

public class ServerRequests {

    static ProgressDialog progressDialog;
    static Context context;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://10.0.2.2/pebbles/";

    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        this.context=context;
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait..");
    }

    public static void storeUserDataInBackground(User user, GetUserCallback userCallback) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallback).execute();
    }

    public static void fetchUserDataInBackground(User user, GetUserCallback callback) {
        progressDialog.show();
        new fetchUserDataAsyncTask(user, callback).execute();
    }
    public static void fetchAllUserInBackground(GetUserCallback userCallback){
        progressDialog.show();
        new FetchAllUserAsyncTask(userCallback).execute();
    }
    public static void deleteUser(User user, GetUserCallback userCallback){
        progressDialog.show();
        new deleteUserAsyncTask(user,userCallback).execute();
    }



    //LEO




    public static class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }


        @Override
        protected Void doInBackground(Void... params) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
            String id=databaseReference.push().getKey();
            user.id=id;
            databaseReference.child(id).setValue(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            User user=null;
            userCallback.done(user);

            super.onPostExecute(aVoid);
        }
    }


    public static class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;
        static User returnuser;

        public  fetchUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected User doInBackground(Void... params) {
            DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference().child("user");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dp:dataSnapshot.getChildren()){
                        returnuser =dp.getValue(User.class);
                        if (returnuser.username.equals(user.username)&& returnuser.password.equals(user.password)) {
                            Toast.makeText(context, returnuser.username,Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            /*ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");
            User returnedUser = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);
                JSONArray jsonArray=jObject.getJSONArray("user");

                if (jsonArray.length() == 0) {
                    returnedUser = null;

                } else {
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    String name = jsonObject.getString("name");
                    String age = jsonObject.getString("age");
                    int intage=Integer.parseInt(age);
                    returnedUser = new User(name, intage, user.username, user.password);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            */

            return returnuser;

        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);

            super.onPostExecute(returnedUser);
        }
    }
    public static class deleteUserAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallback;

        public  deleteUserAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "user_delete.php");
            User returnedUser = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                userCallback.done(result);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void returnedUser) {
            progressDialog.dismiss();

            super.onPostExecute(returnedUser);
        }
    }
    public static class FetchAllUserAsyncTask extends AsyncTask<Void,Void,Void> {
        GetUserCallback userCallback;
        public  FetchAllUserAsyncTask(GetUserCallback userCallback) {
            this.userCallback=userCallback;

        }
        @Override
        protected Void doInBackground(Void... params) {

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            User returneduser = null;
            int count=0;
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "get_all_names.php");
            try {
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                /*JSONObject jObject = new JSONObject(result);
                JSONArray jsonArray = jObject.getJSONArray("user");
                User[] userList=new User[jsonArray.length()];
                if (jsonArray.length() == 0) {
                    returneduser = null;

                } else {
                    while(count<jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        String name = jsonObject.getString("name");
                        String age = jsonObject.getString("age");
                        String username = jsonObject.getString("username");
                        String password = jsonObject.getString("password");
                        int intage = Integer.parseInt(age);
                        returneduser = new User(name, intage, username, password);
                        userList[count]=returneduser;
                        count++;
                    }
                }*/userCallback.done(result);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }
}
