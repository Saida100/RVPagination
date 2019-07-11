package com.example.rvpagination.pagination_rv_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rvpagination.R;

import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Movie> movies;
    private Context context;

    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }
//
//    public List<Movie> getMovies() {
//        return movies;
//    }
//
//    public void setMovies(List<Movie> movies) {
//        this.movies = movies;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                MovieVH movieVH = (MovieVH) holder;
                movieVH.textView.setText(movie.getTitle());
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    @Override
    public int getItemViewType(int position) {
              position= (position == movies.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
        Log.e("loggetItemViewType", String.valueOf(position));
        Log.e("logisLoadingAdded", String.valueOf(isLoadingAdded));
        return position;
    }


    public void add(Movie mc) {
        movies.add(mc);
        notifyItemInserted(movies.size() - 1);
        //  NotifyItemInserted -Сигналы, вновь вставке элемента в указанной позиции.
    }

    public void addAll(List<Movie> mcList) {
        for (Movie mc : mcList) {
            add(mc);
        }
    }

    public void remove(Movie city) {
        int position = movies.indexOf(city);
        if (position > -1) {
            movies.remove(position);
            notifyItemRemoved(position);
            // notifyItemRemoved-Сообщает, что был удален элемент в указанной позиции.
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = movies.size() - 1;
        Movie item = getItem(position);

        if (item != null) {
            movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Movie getItem(int position) {
        return movies.get(position);
    }


    public class MovieVH extends RecyclerView.ViewHolder {
        private TextView textView;
        public MovieVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}

