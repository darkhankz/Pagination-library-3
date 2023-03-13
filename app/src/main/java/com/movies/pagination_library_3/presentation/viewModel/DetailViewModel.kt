package com.movies.pagination_library_3.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.pagination_library_3.data.models.MoviesDetailsData
import com.movies.pagination_library_3.data.models.trailers.TrailersResult
import com.movies.pagination_library_3.domain.usecases.retrofit.FetchTrailersUseCase
import com.movies.pagination_library_3.domain.usecases.retrofit.GetMoviesDetailsUseCase
import com.movies.pagination_library_3.domain.usecases.room.DeleteMovieUseCase
import com.movies.pagination_library_3.domain.usecases.room.InsertMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getMoviesDetailsUseCase: GetMoviesDetailsUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val fetchTrailersUseCase: FetchTrailersUseCase
) : ViewModel() {

    private val _movieTrailersLiveData = MutableLiveData<List<TrailersResult>>()
    val movieTrailersLiveData: LiveData<List<TrailersResult>> = _movieTrailersLiveData

    private val _moviesDetails = MutableLiveData<MoviesDetailsData>()
    val moviesDetails: LiveData<MoviesDetailsData> = _moviesDetails

    val errorMessage = MutableLiveData<String>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun getMoviesDetails(movieId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = getMoviesDetailsUseCase.invoke(movieId = movieId)
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
            insertMovieUseCase.invoke(moviesData = moviesData) {
                onSuccess()
            }
        }

    fun delete(moviesData: MoviesDetailsData, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteMovieUseCase.invoke(moviesData) {
                onSuccess()
            }
        }

    fun fetchTrailers(movieId: Int) {
        viewModelScope.launch {
            val response = fetchTrailersUseCase.invoke(movieId)
            Log.d("APIResponse", response.toString())
            _movieTrailersLiveData.postValue(response.body()?.results)

        }
    }
}