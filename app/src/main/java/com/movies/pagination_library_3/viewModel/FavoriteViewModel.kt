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

class FavoriteViewModel : ViewModel(){

    private val _movieTrailersLiveData = MutableLiveData<List<TrailersResult>>()
    val movieTrailersLiveData: LiveData<List<TrailersResult>> = _movieTrailersLiveData

    private val mMainRepository : MainRepository = MainRepositoryImpl()

    private val _moviesDetails = MutableLiveData<MoviesDetailsData>()
    val moviesDetails: LiveData<MoviesDetailsData> = _moviesDetails

    fun getAllMovies(): LiveData<List<MoviesDetailsData>> {
        return moviesRoomImpl.allMovies
    }

    fun getMoviesDetails(movieId: Int) {
        viewModelScope.launch {
            val response = mMainRepository.getMoviesDetails(movieId)
            _moviesDetails.value = response.body()
        }
    }



        fun fetchTrailers(movieId: Int) {
            viewModelScope.launch {
               val response = mMainRepository.fetchTrailers(movieId)
                Log.d("APIResponse", response.toString())

                _movieTrailersLiveData.postValue(response.body()?.results)

            }
        }





//    fun fetchTrailers(movieId: Int) {
//            val response = mMainRepository.fetchTrailers(movieId)
//            movieTrailers.value = response.body()?.results
//    }


    fun insert(moviesData: MoviesDetailsData, onSuccess:() -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            moviesRoomImpl.insertMovie(moviesData){
                onSuccess()
            }
        }

    fun delete(moviesData: MoviesDetailsData, onSuccess:() -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            moviesRoomImpl.deleteMovie(moviesData){
                onSuccess()
            }
        }

}