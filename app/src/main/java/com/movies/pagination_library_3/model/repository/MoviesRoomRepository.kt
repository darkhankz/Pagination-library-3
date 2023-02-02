package com.movies.pagination_library_3.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.movies.pagination_library_3.data.MoviesDetailsData

//moviesRepository
interface MoviesRoomRepository {
    val allMovies: LiveData<List<MoviesDetailsData>>
    suspend fun insertMovie(moviesData: MoviesDetailsData, onSuccess:() -> Unit)
    suspend fun deleteMovie(moviesData: MoviesDetailsData, onSuccess:() -> Unit)

}