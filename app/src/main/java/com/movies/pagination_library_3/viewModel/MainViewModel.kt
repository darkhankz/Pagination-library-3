package com.movies.pagination_library_3.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.movies.pagination_library_3.MyApplication
import com.movies.pagination_library_3.data.MoviesDetailsData
import com.movies.pagination_library_3.model.repository.*
import com.movies.pagination_library_3.model.repository.room.MoviesRoomDatabase
import com.movies.pagination_library_3.model.repository.room.MoviesRoomImpl
import com.movies.pagination_library_3.moviesRoomImpl


class MainViewModel(private val mMainRepository: MainRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()

    fun getMovieList(): LiveData<PagingData<MoviesDetailsData>> {
        return mMainRepository.getAllMovies().cachedIn(viewModelScope)
    }

    fun init() {
        val daoMovies = MoviesRoomDatabase.getInstance(MyApplication.appContext).getMoviesDao()
        moviesRoomImpl = MoviesRoomImpl(daoMovies)
    }

}


