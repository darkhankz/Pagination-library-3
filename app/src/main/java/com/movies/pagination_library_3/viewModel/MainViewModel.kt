package com.movies.pagination_library_3.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.movies.pagination_library_3.data.MoviesDetailsData
import com.movies.pagination_library_3.model.repository.MainRepository
import com.movies.pagination_library_3.model.repository.MainRepositoryImpl

class MainViewModel(private val mMoviesRepository : MainRepository): ViewModel() {
    val errorMessage = MutableLiveData<String>()

    fun getMovieList(): LiveData<PagingData<MoviesDetailsData>> {
        return mMoviesRepository.getAllMovies().cachedIn(viewModelScope)
    }

}