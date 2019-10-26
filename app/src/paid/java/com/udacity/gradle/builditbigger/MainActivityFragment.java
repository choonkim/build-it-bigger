package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jokescreen.JokeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivityFragment extends Fragment {

    private boolean isTesting = false;
    private String mJoke;

    private Unbinder mUnbinder;

    @BindView(R.id.progressbar_loading)
    ProgressBar mProgressBar;

    public MainActivityFragment() {
        // constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mUnbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.button_main_get_joke)
    public void tellJoke() {
        showLoading();
        GetJokeAsyncTask getJokeAsyncTask = new GetJokeAsyncTask(new GetJokeAsyncTask.OnEventListener<String>() {
            @Override
            public void onSuccess(String joke) {
                hideLoading();
                onJokeRetrieved(joke);
            }
        });
        getJokeAsyncTask.execute();
    }

    private void onJokeRetrieved(String joke) {
        mJoke = joke;
        if (!isTesting) {
            startJokeScreen();
        }
    }

    private void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void startJokeScreen() {
        startActivity(JokeActivity.jokeScreenIntent(getActivity(), mJoke));
    }

    @VisibleForTesting
    public String getJoke() {
        return mJoke;
    }

    @VisibleForTesting
    public void setJoke(String joke) {
        mJoke = joke;
    }

    @VisibleForTesting
    public void setTesting() {
        isTesting = true;
    }
}
