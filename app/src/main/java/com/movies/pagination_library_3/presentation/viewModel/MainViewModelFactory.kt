package com.movies.pagination_library_3.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movies.pagination_library_3.domain.usecases.retrofit.GetAllMoviesUseCase

class MainViewModelFactory(private val getAllMoviesUseCase: GetAllMoviesUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(getAllMoviesUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}