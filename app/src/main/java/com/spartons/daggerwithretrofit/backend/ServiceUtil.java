package com.spartons.daggerwithretrofit.backend;


import com.spartons.daggerwithretrofit.response.CarCategoryResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by Ahsen Saeed on 12/4/2017.
 */

public interface ServiceUtil {

    @GET("GetCarsCategories.php")
    Call<CarCategoryResponse> getCarCategories();
}
