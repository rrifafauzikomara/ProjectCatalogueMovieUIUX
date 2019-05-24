package com.example.rifafauzi6.projectcataloguemovieuiux.view.category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rifafauzi6.projectcataloguemovieuiux.api.BaseApiService;
import com.example.rifafauzi6.projectcataloguemovieuiux.api.Server;
import com.example.rifafauzi6.projectcataloguemovieuiux.adapter.MovieAdapter;
import com.example.rifafauzi6.projectcataloguemovieuiux.BuildConfig;
import com.example.rifafauzi6.projectcataloguemovieuiux.model.Movies;
import com.example.rifafauzi6.projectcataloguemovieuiux.MovieItemClickListener;
import com.example.rifafauzi6.projectcataloguemovieuiux.model.ResponseMovies;
import com.example.rifafauzi6.projectcataloguemovieuiux.view.DetailMovieActivity;
import com.example.rifafauzi6.projectcataloguemovieuiux.view.MainActivity;
import com.example.rifafauzi6.projectcataloguemovieuiux.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularActivity extends AppCompatActivity {

    RecyclerView rvMovies;
    private MovieAdapter adapter;
    List<Movies> listMovies = new ArrayList<>();
    ProgressDialog loading;
    BaseApiService apiService;

    private final String api_key = BuildConfig.MOVIE_DB_API_KEY;
    private final String language = "en-US";
    private final String sort_by = "popularity.desc";
    private final String include_adult = "false";
    private final String include_video = "false";
    private final String page = "1";

//    public static final String KEY_TEAM_DETAIL = "team_detail";
//    public static final String KEY_TEAM_DETAIL_TRANSITION_NAME = "strTeamBadge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_popular);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvMovies = findViewById(R.id.rv_movies);

        apiService = Server.getAPIService();

        adapter = new MovieAdapter(getApplicationContext(), listMovies);

        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMovies.setAdapter(adapter);

        refresh();

        if(getSupportActionBar() != null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void refresh(){
        loading = ProgressDialog.show(this, null, "Please wait...", true, false);
        apiService.getPopularMovies(api_key, language).enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    listMovies = response.body().getMovies();
                    rvMovies.setAdapter(new MovieAdapter(getApplicationContext(), listMovies));
                    adapter.notifyDataSetChanged();
                }
                else {
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onMovieItemClick(Movies moviesItem, ImageView shareImageView) {
//        Intent intent = new Intent(this, DetailMovieActivity.class);
//        intent.putExtra(KEY_TEAM_DETAIL, (Serializable) moviesItem);
//        intent.putExtra(KEY_TEAM_DETAIL_TRANSITION_NAME, ViewCompat.getTransitionName(shareImageView));
//
//        ActivityOptionsCompat options =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(this, shareImageView, ViewCompat.getTransitionName(shareImageView));
//
//        startActivity(intent, options.toBundle());
//    }
}
