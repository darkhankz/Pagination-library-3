package com.movies.pagination_library_3.model.repository

import androidx.lifecycle.LiveData
import com.movies.pagination_library_3.data.MoviesDetailsData

//MoviesRepositoryRealiziation
class MoviesRoomImpl(private val moviesDao: MoviesDao): MoviesRoomRepository {
    override val allMovies: LiveData<List<MoviesDetailsData>>
        get() = moviesDao.getAllMovies()

    override suspend fun insertMovie(moviesData: MoviesDetailsData, onSuccess: () -> Unit) {
        moviesDao.insert(moviesData)
        onSuccess()
    }

    override suspend fun deleteMovie(moviesData: MoviesDetailsData, onSuccess: () -> Unit) {
        moviesDao.delete(moviesData)
        onSuccess()
    }
}
