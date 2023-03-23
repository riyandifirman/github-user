package com.riyandifirman.githubuser

import com.riyandifirman.githubuser.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // fungsi untuk mendapatkan data user dari github api
    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ) : Call<GithubResponse>

//    // fungsi untuk menampilkan data user yang dicari
//    @GET("search/users")
//    fun getSearchData(
//        @Query("q") query: String
//    ) : Call<SearchResponse>

//    // fungsi untuk menampilkan detail user
//    @GET("users/{username}")
//    fun getDetailUser(
//        @Path("username") username: String
//    ) : Call<DetailUserResponse>
}