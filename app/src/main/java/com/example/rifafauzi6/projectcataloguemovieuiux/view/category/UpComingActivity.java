package com.example.rifafauzi6.projectcataloguemovieuiux.view.category;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;

import com.example.rifafauzi6.projectcataloguemovieuiux.api.BaseApiService;
import com.example.rifafauzi6.projectcataloguemovieuiux.api.Server;
import com.example.rifafauzi6.projectcataloguemovieuiux.adapter.MovieAdapter;
import com.example.rifafauzi6.projectcataloguemovieuiux.BuildConfig;
import com.example.rifafauzi6.projectcataloguemovieuiux.model.Movies;
import com.example.rifafauzi6.projectcataloguemovieuiux.model.ResponseMovies;
import com.example.rifafauzi6.projectcataloguemovieuiux.view.MainActivity;
import com.example.rifafauzi6.projectcataloguemovieuiux.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingActivity extends AppCompatActivity {

    RecyclerView rvMovies;
    private MovieAdapter adapter;
    List<Movies> listMovies = new ArrayList<>();
    ProgressDialog loading;
    BaseApiService apiService;

    private final String api_key = BuildConfig.MOVIE_DB_API_KEY;
    private final String language = "en-US";

//    public static final String KEY_TEAM_DETAIL = "team_detail";
//    public static final String KEY_TEAM_DETAIL_TRANSITION_NAME = "strTeamBadge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvMovies = findViewById(R.id.rv_movies);

        apiService = Server.getAPIService();

        adapter = new MovieAdapter(this, listMovies);

        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMovies.setAdapter(adapter);

        refresh();

        if(getSupportActionBar() != null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        apiService.getUpComingMovie(api_key, language).enqueue(new Callback<ResponseMovies>() {
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

}
