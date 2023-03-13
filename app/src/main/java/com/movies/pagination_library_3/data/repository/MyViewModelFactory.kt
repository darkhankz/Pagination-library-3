package com.movies.pagination_library_3.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movies.pagination_library_3.domain.usecases.retrofit.GetAllMoviesUseCase
import com.movies.pagination_library_3.presentation.viewModel.MainViewModel

class MyViewModelFactory(private val getAllMoviesUseCase: GetAllMoviesUseCase) : ViewModelProvider.Factory {
    private val mainRepository: MainRepository = MainRepositoryImpl()
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(getAllMoviesUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}