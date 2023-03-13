package com.movies.pagination_library_3.domain.usecases.room

import androidx.lifecycle.LiveData
import com.movies.pagination_library_3.data.models.MoviesDetailsData
import com.movies.pagination_library_3.data.repository.room.MoviesDao
import com.movies.pagination_library_3.data.repository.room.MoviesRoomImpl

class GetAllFavoriteMoviesUseCase(private val moviesDao: MoviesDao) {
        operator fun invoke(): LiveData<List<MoviesDetailsData>> {
            return moviesDao.getAllFavoriteMovies()
    }
}