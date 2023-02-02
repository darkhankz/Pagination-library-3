package com.movies.pagination_library_3.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.movies.pagination_library_3.MAIN
import com.movies.pagination_library_3.data.MoviesDetailsData
import com.movies.pagination_library_3.model.repository.*
import com.movies.pagination_library_3.moviesRoomImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val mMainRepository : MainRepository): ViewModel() {
    val errorMessage = MutableLiveData<String>()

    val context = MAIN.application
    fun getMovieList(): LiveData<PagingData<MoviesDetailsData>> {
        return mMainRepository.getAllMovies().cachedIn(viewModelScope)
    }

    fun init() {
        val daoMovies = MoviesRoomDatabase.getInstance(context).getMoviesDao()
        moviesRoomImpl = MoviesRoomImpl(daoMovies)
    }

}
