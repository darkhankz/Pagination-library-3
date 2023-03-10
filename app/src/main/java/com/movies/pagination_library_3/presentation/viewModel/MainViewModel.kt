package com.movies.pagination_library_3.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.movies.pagination_library_3.MyApplication
import com.movies.pagination_library_3.data.models.MoviesDetailsData
import com.movies.pagination_library_3.data.repository.room.MoviesRoomDatabase
import com.movies.pagination_library_3.data.repository.room.MoviesRoomImpl
import com.movies.pagination_library_3.domain.usecases.retrofit.GetAllMoviesUseCase
import com.movies.pagination_library_3.moviesRoomImpl
import com.movies.pagination_library_3.presentation.view.adapter.MoviePagerAdapter


class MainViewModel(private val getAllMoviesUseCase: GetAllMoviesUseCase) : ViewModel() {
    val errorMessage = MutableLiveData<String>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getMovieList(): LiveData<PagingData<MoviesDetailsData>> {
        return getAllMoviesUseCase.invoke().cachedIn(viewModelScope)
    }

    fun init() {
        val daoMovies = MoviesRoomDatabase.getInstance(MyApplication.appContext).getMoviesDao()
        moviesRoomImpl = MoviesRoomImpl(daoMovies)
    }

    fun initLoadState(adapter: MoviePagerAdapter) {
        Log.d("dataCheck", "initLoadState: called")

        adapter.addLoadStateListener { loadState ->
            // update loading state
            _loading.value = loadState.refresh is LoadState.Loading ||
                    loadState.append is LoadState.Loading
            Log.d(
                "dataCheck",
                "initLoadState: refresh=${loadState.refresh}, append=${loadState.append}, prepend=${loadState.prepend}"
            )
            Log.d("dataCheck", "initLoadState: loading=${_loading.value}")

            // update error message
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                Log.d("dataCheck", "initLoadState: error=${it.error}")
                errorMessage.value = it.error.toString()
            }
        }
    }
}


