package com.kamtechs.ifcgeorgiatech;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;

public class NewsActivity extends AppCompatActivity {

    public ArrayList<NewsPost> newsPosts = new ArrayList<NewsPost>();
    public NewsPostAdapter newsPostAdapter;
    public RecyclerView newsRecyclerView;
    public ProgressBar progressBar;
    public TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarnews);

        newsRecyclerView = (RecyclerView) findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setHasFixedSize(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        errorTextView = (TextView) findViewById(R.id.errorTextView);

        errorTextView.setVisibility(View.INVISIBLE);
        newsRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsPostAdapter = new NewsPostAdapter(this, newsPosts);
        newsRecyclerView.setAdapter(newsPostAdapter);

        /*newsRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.util.Log.i("Console Output: ", "Fruits! - " + items[position]);
            }
        });*/

        new getNewsPostTask().execute();

    }


    private class getNewsPostTask extends AsyncTask<Void, Void, ArrayList<NewsPost>> {

        protected ArrayList<NewsPost> doInBackground(Void... nothing) {
            return JsonHelper.getNewsPosts();
        }

        protected void onPostExecute(ArrayList<NewsPost> result) {
            progressBar.setVisibility(View.INVISIBLE);
            if (result.size() == 0) {
                errorTextView.setVisibility(View.VISIBLE);
            } else {
                newsPosts = result;
                newsPostAdapter = new NewsPostAdapter(NewsActivity.this, newsPosts);
                newsRecyclerView.setAdapter(newsPostAdapter);
                newsPostAdapter.notifyDataSetChanged();
                newsRecyclerView.setVisibility(View.VISIBLE);
                new getNewsPostPicturesTask().execute(0);
            }
        }
    }


    private class getNewsPostPicturesTask extends AsyncTask<Integer, Integer, Bitmap> {

        int currentIndex;

        protected Bitmap doInBackground(Integer... current) {
            currentIndex = current[0];
            NewsPost currentNewsPosts = newsPosts.get(currentIndex);
            if (currentNewsPosts.hasPicture) {
                return JsonHelper.getBitmapFromURL(currentNewsPosts.imageURL);
            } else {
                return null;
            }
        }

       /* protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }*/

        protected void onPostExecute(Bitmap result) {
            newsPosts.get(currentIndex).picture = result;
            newsPostAdapter.notifyDataSetChanged();
            if (currentIndex < newsPosts.size()-1) {
                new getNewsPostPicturesTask().execute(currentIndex + 1);
            }
        }
    }
}
