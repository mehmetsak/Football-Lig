package com.mehmetsakiratasayin.football.service;

import com.mehmetsakiratasayin.football.model.Teams;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
public interface Interface {
    @GET("mehmetsak/Text/main/dp.json")

    Call<List<Teams>> getData();

}
