package com.movies.pagination_library_3.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.movies.pagination_library_3.API_KEY
import com.movies.pagination_library_3.NETWORK_PAGE_SIZE
import com.movies.pagination_library_3.data.models.MoviesDetailsData
import com.movies.pagination_library_3.data.models.trailers.TrailersResponse
import com.movies.pagination_library_3.data.retrofit.ApiInterface
import retrofit2.Response

class MainRepositoryImpl : MainRepository {
    private val apiInterface = ApiInterface.create()
    override fun getAllMovies(): LiveData<PagingData<MoviesDetailsData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(ApiInterface.create())
            }, initialKey = 1
        ).liveData
    }

    override suspend fun getMoviesDetails(movieId: Int): Response<MoviesDetailsData> {
        return apiInterface.getMoviesDetails(movieId, API_KEY)
    }

    override suspend fun fetchTrailers(movieId: Int): Response<TrailersResponse> {
        return apiInterface.getTrailers(movieId, API_KEY)

    }
}