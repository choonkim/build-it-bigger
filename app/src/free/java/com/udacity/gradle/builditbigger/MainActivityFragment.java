package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jokescreen.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivityFragment extends Fragment {

    private boolean isTesting = false;
    private String mJoke;
    private InterstitialAd mInterstitialAd;

    private Unbinder mUnbinder;

    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.progressbar_loading)
    ProgressBar mProgressBar;

    public MainActivityFragment() {
        // constructor
    }

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        MobileAds.initialize(getActivity(), BuildConfig.ADMOB_APP_ID);

        mUnbinder = ButterKnife.bind(this, root);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.intersticial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startJokeScreen();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
        mAdView.loadAd(adRequest);
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
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d(TAG, "Ad did not load correctly.");
            }
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
