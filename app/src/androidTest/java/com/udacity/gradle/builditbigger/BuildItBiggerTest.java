package com.udacity.gradle.builditbigger;


import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.isEmptyString;

@RunWith(AndroidJUnit4.class)
public class BuildItBiggerTest {

    @Test
    public void verifyStringNotNullOrEmpty() throws Exception {
        final MainActivityFragment mainActivityFragment = new MainActivityFragment();
        mainActivityFragment.setTesting();

        GetJokeAsyncTask getJokeAsyncTask = new GetJokeAsyncTask(new GetJokeAsyncTask.OnEventListener<String>() {
            @Override
            public void onSuccess(String joke) {
                mainActivityFragment.setJoke(joke);

            }
        });
        getJokeAsyncTask.execute();
        Thread.sleep(5000);
        assertThat(mainActivityFragment.getJoke(), not(isEmptyString()));
    }
}
