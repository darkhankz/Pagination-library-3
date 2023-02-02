package com.movies.pagination_library_3.model.retrofit

import com.movies.pagination_library_3.BASE_URL
import com.movies.pagination_library_3.data.MoviesData
import com.movies.pagination_library_3.data.MoviesDetailsData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/popular")
    suspend fun getTopPopularMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesData>
    @GET("movie/{movie_id}")
    suspend fun getMoviesDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String)
    : Response<MoviesDetailsData>

    object RetrofitService {
        val api: ApiInterface by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}

