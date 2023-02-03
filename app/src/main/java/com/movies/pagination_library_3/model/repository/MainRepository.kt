package com.movies.pagination_library_3.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.movies.pagination_library_3.data.MoviesDetailsData
import com.movies.pagination_library_3.data.trailers.TrailersResponse
import retrofit2.Call
import retrofit2.Response

interface MainRepository {
    fun getAllMovies(): LiveData<PagingData<MoviesDetailsData>>
    suspend fun getMoviesDetails(movieId: Int): Response<MoviesDetailsData>
    fun fetchTrailers(movieId: Int): Call<TrailersResponse>

}