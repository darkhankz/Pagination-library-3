package com.movies.pagination_library_3.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.movies.pagination_library_3.data.models.MoviesDetailsData
import com.movies.pagination_library_3.data.repository.room.MoviesDao
import com.movies.pagination_library_3.domain.usecases.room.GetAllFavoriteMoviesUseCase


class FavoriteViewModel(moviesDao: MoviesDao) : ViewModel() {
    private val getAllFavoriteMoviesUseCase = GetAllFavoriteMoviesUseCase(moviesDao = moviesDao)
    fun getAllMovies(): LiveData<List<MoviesDetailsData>> {
        return getAllFavoriteMoviesUseCase.invoke()
    }
}