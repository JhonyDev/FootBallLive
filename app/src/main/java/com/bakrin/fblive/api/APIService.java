package com.bakrin.fblive.api;


import com.bakrin.fblive.model.Pojo.CountryResponse;
import com.bakrin.fblive.model.Pojo.CreatedUser;
import com.bakrin.fblive.model.Pojo.FixtureItem;
import com.bakrin.fblive.model.Pojo.H2HResponse;
import com.bakrin.fblive.model.Pojo.LeagueListResponse;
import com.bakrin.fblive.model.Pojo.LeagueTableResponse;
import com.bakrin.fblive.model.Pojo.LiveFixtureResponse;
import com.bakrin.fblive.model.Pojo.StatsResponse;
import com.bakrin.fblive.model.Pojo.TeamSearchResponse;
import com.bakrin.fblive.model.Pojo.TopScorerResponse;
import com.bakrin.fblive.model.models.NotificationPriority;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @GET("countries")
//    @GET("v2/countries")
    Call<CountryResponse> getCountiesList();

    @GET("fixtures/live")
    Call<LiveFixtureResponse> getLiveFixtureList();

    @GET("fixtures/date/{date}")
//    @GET("v2/fixtures/date/{date}")
    Call<LiveFixtureResponse> getByDateFixtureList(@Path("date") String date);

    @GET("teams/search/{name}")
    Call<TeamSearchResponse> getSearchTeamList(@Path("name") String date);

    @GET("leagues/country/{country_name}")
//    @GET("/v2/leagues/country/{country_name}")
    Call<LeagueListResponse> getLeagueByCountryList(@Path("country_name") String countryCode);

    @GET("leagues")
    Call<LeagueListResponse> getLeagueAllList();

    @GET("fixtures/team/{team_id}")
//    @GET("/v2/fixtures/team/{team_id}")
    Call<LiveFixtureResponse> getTeamFixtureList(@Path("team_id") int countryCode);

    @GET("fixtures/id/{id}")
//    @GET("/v2/fixtures/id/{id}")
    Call<LiveFixtureResponse> getFixtureById(@Path("id") int fixtureID);

    @GET("leagueTable/{id}")
//    @GET("/v2/leagueTable/{id}")
    Call<LeagueTableResponse> getLeagueTable(@Path("id") int countryCode);

    @GET("fixtures/league/{id}")
//    @GET("/v2/fixtures/league/{id}")
    Call<LiveFixtureResponse> getLeagueFixture(@Path("id") int leagueId);


    @GET("topscorers/{id}")
//    @GET("/v2/topscorers/{id}")
    Call<TopScorerResponse> getTopScorers(@Path("id") int leagueId);


    @GET("fixtures/id/{id}")
//    @GET("/v2/fixtures/id/{id}")
    Call<StatsResponse> getFixtureStat(@Path("id") int fixtureId);

    @GET("fixtures/h2h/{homeId}/{awayId}")
//    @GET("/v2/fixtures/h2h/{homeId}/{awayId}")
    Call<H2HResponse> getH2H(@Path("homeId") int homeId, @Path("awayId") int awayId);

    @POST("api/post_user_id")
    Call<CreatedUser> postUserId(@Body CreatedUser createdUser);

    @POST("api/post_notification_priority")
    Call<NotificationPriority> postFixtureItem(@Body NotificationPriority fixtureItem);

}
