package com.positive.themeselector.api;

import com.positive.themeselector.model.ThemeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {


    @GET("VideoCatgoryList.php")
    Call<ThemeResponse> getThemes();
}
