package com.movies.pagination_library_3.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movies.pagination_library_3.domain.usecases.retrofit.FetchTrailersUseCase
import com.movies.pagination_library_3.domain.usecases.retrofit.GetAllMoviesUseCase
import com.movies.pagination_library_3.domain.usecases.retrofit.GetMoviesDetailsUseCase
import com.movies.pagination_library_3.domain.usecases.room.DeleteMovieUseCase
import com.movies.pagination_library_3.domain.usecases.room.InsertMovieUseCase

class DetailViewModelFactory(
    private val getMoviesDetailsUseCase: GetMoviesDetailsUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val fetchTrailersUseCase: FetchTrailersUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            DetailViewModel(getMoviesDetailsUseCase, insertMovieUseCase, deleteMovieUseCase, fetchTrailersUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
