package com.movies.pagination_library_3.domain.usecases.room

import com.movies.pagination_library_3.data.models.MoviesDetailsData
import com.movies.pagination_library_3.data.repository.room.MoviesRoomImpl

class DeleteMovieUseCase(private val moviesRoomImpl: MoviesRoomImpl) {
    suspend fun invoke(moviesData: MoviesDetailsData,  onSuccess: () -> Unit) = moviesRoomImpl.deleteMovie(moviesData = moviesData, onSuccess = onSuccess)
}