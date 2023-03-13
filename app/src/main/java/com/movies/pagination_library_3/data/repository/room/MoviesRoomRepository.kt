package com.movies.pagination_library_3.data.repository.room

import androidx.lifecycle.LiveData
import com.movies.pagination_library_3.data.models.MoviesDetailsData

//moviesRepository
interface MoviesRoomRepository {
    val allMovies: LiveData<List<MoviesDetailsData>>
    suspend fun insertMovie(moviesData: MoviesDetailsData, onSuccess: () -> Unit)
    suspend fun deleteMovie(moviesData: MoviesDetailsData, onSuccess: () -> Unit)

}