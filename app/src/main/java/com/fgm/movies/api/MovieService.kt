package com.fgm.movies.api

import com.fgm.movies.model.Film
import com.fgm.movies.model.Genres
import com.fgm.movies.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MovieService {

    @GET("films.json")
    fun getAllData(): Call<Movie>

}