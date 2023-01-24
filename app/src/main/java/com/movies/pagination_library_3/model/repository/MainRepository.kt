package com.movies.pagination_library_3.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.movies.pagination_library_3.data.MoviesDetailsData

interface MainRepository {
    fun getAllMovies(): LiveData<PagingData<MoviesDetailsData>>
}