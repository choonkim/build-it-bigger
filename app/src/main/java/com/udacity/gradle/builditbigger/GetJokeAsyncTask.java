package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class GetJokeAsyncTask extends AsyncTask<Void, Void, String> {

    private static MyApi myApi = null;

    private OnEventListener<String> mCallBack;

    private final String TAG = GetJokeAsyncTask.class.getSimpleName();

    public GetJokeAsyncTask(OnEventListener callback) {
        mCallBack = callback;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApi == null) {
            MyApi.Builder builder = new
                    MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) {
                            request.setDisableGZipContent(true);
                        }
                    });
            myApi = builder.build();
        }
        try {
            return myApi.getJoke().execute().getData();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return "";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (mCallBack != null) {
            mCallBack.onSuccess(result);
        }
    }

    public interface OnEventListener<T> {
        void onSuccess(T object);
    }
}
