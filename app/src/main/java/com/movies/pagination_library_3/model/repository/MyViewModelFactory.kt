package com.movies.pagination_library_3.model.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movies.pagination_library_3.viewModel.MainViewModel

class MyViewModelFactory : ViewModelProvider.Factory {
    private val mainRepository: MainRepository = MainRepositoryImpl()
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(mainRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}