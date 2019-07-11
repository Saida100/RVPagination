package com.example.rvpagination.pagination_rv_2;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.rvpagination.R;

import java.util.List;

public class Main2ActivityPG2 extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    PaginationAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView rv;
    ProgressBar progressBar;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int currentPage = PAGE_START;
    // https://blog.iamsuleiman.com/android-pagination-tutorial-getting-started-recyclerview/
    // https://github.com/Suleiman19/Android-Pagination-with-RecyclerView/blob/a1dd15095bdfc48fd41171061d1226963e07c0f8/app/src/main/res/layout/activity_main.xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_pg2);
        rv = (RecyclerView) findViewById(R.id.main_recycler);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        adapter = new PaginationAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        //setItemAnimator при добавлении или удалении элемента списка вызывается метод адаптера notifyItemInserted(int position) или notifyItemRemoved(int position) соответственно.
        //При желании можно написать собственную реализацию анимации, унаследовавшись от RecyclerView.ItemAnimator.
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


        // mocking network delay for API call
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFirstPage();
            }
        }, 1000);

    }


    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");
        List<Movie> movies = Movie.createMovies(adapter.getItemCount());
        progressBar.setVisibility(View.GONE);
        adapter.addAll(movies);

        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;

    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        List<Movie> movies = Movie.createMovies(adapter.getItemCount());
        adapter.removeLoadingFooter();
        isLoading = false;

        adapter.addAll(movies);

        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;

    }
}
