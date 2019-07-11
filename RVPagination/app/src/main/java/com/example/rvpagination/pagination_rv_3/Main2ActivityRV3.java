package com.example.rvpagination.pagination_rv_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.rvpagination.R;
import com.example.rvpagination.pagination_rv.PaginationScrollListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2ActivityRV3 extends AppCompatActivity {
    private MovieService movieService;
    private ProgressBar progressBar;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    private PaginationAdapter paginationAdapter;
// http://velmm.com/pagination-with-recyclerview-in-android/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_rv3);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressbar);

        movieService = ClientApi.getClient().create(MovieService.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        paginationAdapter = new PaginationAdapter(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(paginationAdapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {

                isLoading = true;
                currentPage += 1;
                loadNextPage();

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


        loadFirstPage();
    }

    private void loadNextPage() {

        movieService.getMovies().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                paginationAdapter.removeLoadingFooter();
                isLoading = false;

                List<Movie> results = response.body();
                paginationAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) paginationAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void loadFirstPage() {

        movieService.getMovies().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> results = response.body();
                progressBar.setVisibility(View.GONE);
                paginationAdapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

            }

        });
    }

}
