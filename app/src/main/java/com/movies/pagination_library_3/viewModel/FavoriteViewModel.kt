package com.movies.pagination_library_3.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.pagination_library_3.data.MoviesDetailsData
import com.movies.pagination_library_3.data.trailers.TrailersResult
import com.movies.pagination_library_3.model.repository.MainRepository
import com.movies.pagination_library_3.model.repository.MainRepositoryImpl
import com.movies.pagination_library_3.moviesRoomImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteViewModel : ViewModel() {
    private val _movieTrailersLiveData = MutableLiveData<List<TrailersResult>>()
    val movieTrailersLiveData: LiveData<List<TrailersResult>> = _movieTrailersLiveData

    private val mMainRepository: MainRepository = MainRepositoryImpl()

    private val _moviesDetails = MutableLiveData<MoviesDetailsData>()
    val moviesDetails: LiveData<MoviesDetailsData> = _moviesDetails

    val errorMessage = MutableLiveData<String>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun getAllMovies(): LiveData<List<MoviesDetailsData>> {
        return moviesRoomImpl.allMovies
    }

    fun getMoviesDetails(movieId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = mMainRepository.getMoviesDetails(movieId)
                _moviesDetails.value = response.body()
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }


    fun insert(moviesData: MoviesDetailsData, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            moviesRoomImpl.insertMovie(moviesData) {
                onSuccess()
            }
        }

    fun delete(moviesData: MoviesDetailsData, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            moviesRoomImpl.deleteMovie(moviesData) {
                onSuccess()
            }
        }

    fun fetchTrailers(movieId: Int) {
        viewModelScope.launch {
            val response = mMainRepository.fetchTrailers(movieId)
            Log.d("APIResponse", response.toString())
            _movieTrailersLiveData.postValue(response.body()?.results)

        }
    }
}