package com.spartons.daggerwithretrofit;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spartons.daggerwithretrofit.adapter.CarCategoryAdapter;
import com.spartons.daggerwithretrofit.backend.ServiceUtil;
import com.spartons.daggerwithretrofit.components.DaggerServiceApplicationComponent;
import com.spartons.daggerwithretrofit.modules.ApplicationContextModule;
import com.spartons.daggerwithretrofit.response.CarCategoryResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<CarCategoryResponse> {

    @Inject
    Picasso picasso;
    @Inject
    ServiceUtil serviceUtil;

    private RecyclerView mainRecyclerView;
    private CarCategoryAdapter carCategoryAdapter;
    private List<CarCategoryResponse.CarCategory> carCategories = new ArrayList<>();
    private ProgressBar mainProgressBar;
    private Call<CarCategoryResponse> carCategoriesCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerServiceApplicationComponent.builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build()
                .inject(this);
        initViews();
        setRecyclerViewProperties();
        carCategoriesCallback = serviceUtil.getCarCategories();
        carCategoriesCallback.enqueue(this);
    }

    private void setRecyclerViewProperties() {
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setHasFixedSize(true);
        carCategoryAdapter = new CarCategoryAdapter(carCategories, this);
        mainRecyclerView.setAdapter(carCategoryAdapter);
    }

    private void initViews() {
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        mainProgressBar = findViewById(R.id.mainProgressBar);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (carCategoriesCallback != null && carCategoriesCallback.isExecuted())
            carCategoriesCallback.cancel();
    }

    @Override
    public void onResponse(@NonNull Call<CarCategoryResponse> call, @NonNull Response<CarCategoryResponse> response) {
        if (mainProgressBar.getVisibility() == View.VISIBLE)
            mainProgressBar.setVisibility(View.GONE);
        if (response.isSuccessful()) {
            if (response.body().getCarCategories() != null && response.body().getCarCategories().size() > 0) {
                this.carCategories.addAll(response.body().getCarCategories());
                carCategoryAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Communication error internet not connect!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call<CarCategoryResponse> call, @NonNull Throwable t) {
        if (mainProgressBar.getVisibility() == View.VISIBLE)
            mainProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Communication error internet not connect!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        carCategoriesCallback = null;
        carCategoryAdapter = null;
        carCategories.clear();
        carCategories = null;
        serviceUtil = null;
        picasso = null;
    }
}
