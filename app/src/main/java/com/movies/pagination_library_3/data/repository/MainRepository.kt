package com.movies.pagination_library_3.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.movies.pagination_library_3.data.models.MoviesDetailsData
import com.movies.pagination_library_3.data.models.trailers.TrailersResponse
import retrofit2.Response

interface MainRepository {
    fun getAllMovies(): LiveData<PagingData<MoviesDetailsData>>
    suspend fun getMoviesDetails(movieId: Int): Response<MoviesDetailsData>
    suspend fun fetchTrailers(movieId: Int): Response<TrailersResponse>
}