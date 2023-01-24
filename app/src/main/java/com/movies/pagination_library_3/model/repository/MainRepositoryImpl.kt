package com.movies.pagination_library_3.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.movies.pagination_library_3.data.MoviesDetailsData
import com.movies.pagination_library_3.data.NETWORK_PAGE_SIZE
import com.movies.pagination_library_3.model.retrofit.ApiInterface

class MainRepositoryImpl: MainRepository {
    override fun getAllMovies(): LiveData<PagingData<MoviesDetailsData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(ApiInterface.RetrofitService.api)
            }
            , initialKey = 1
        ).liveData
    }

}