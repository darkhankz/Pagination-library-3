package com.movies.pagination_library_3.domain.usecases.room

import com.movies.pagination_library_3.data.models.MoviesDetailsData
import com.movies.pagination_library_3.data.repository.room.MoviesDao
import com.movies.pagination_library_3.data.repository.room.MoviesRoomImpl

class InsertMovieUseCase(private val moviesRoomImpl: MoviesRoomImpl) {
    suspend fun invoke(moviesData: MoviesDetailsData, onSuccess: () -> Unit) = moviesRoomImpl.insertMovie(moviesData = moviesData, onSuccess = onSuccess)
}