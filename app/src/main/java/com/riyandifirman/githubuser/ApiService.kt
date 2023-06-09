package com.riyandifirman.githubuser

import com.riyandifirman.githubuser.response.DetailUserResponse
import com.riyandifirman.githubuser.response.GithubResponse
import com.riyandifirman.githubuser.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // fungsi untuk mendapatkan data user dari github api
    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ) : Call<GithubResponse>

    // fungsi untuk menampilkan data user yang dicari
    @GET("search/users")
    fun getSearchData(
        @Query("q") query: String
    ) : Call<SearchResponse>

    // fungsi untuk menampilkan detail user
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    // fungsi untuk menampilkan follower user
    @GET("users/{username}/followers")
    fun getFollowerUser(
        @Path("username") username: String
    ) : Call<ArrayList<User>>

    // fungsi untuk menampilkan following user
    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ) : Call<ArrayList<User>>
}