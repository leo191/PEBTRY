package com.example.shubu.peb;

/**
 * Created by shubu on 6/22/2017.
 */

interface GetUserCallback {

    public abstract void done(LEOvisitors returnedUser);

    public abstract void done(User returnedUser);
    public abstract void done(String returnedUser);


}
