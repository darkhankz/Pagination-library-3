package com.movies.pagination_library_3.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.pagination_library_3.data.MoviesDetailsData
import com.movies.pagination_library_3.data.trailers.TrailersResponse
import com.movies.pagination_library_3.data.trailers.TrailersResult
import com.movies.pagination_library_3.model.repository.MainRepository
import com.movies.pagination_library_3.model.repository.MainRepositoryImpl
import com.movies.pagination_library_3.moviesRoomImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteViewModel : ViewModel(){
    val movieTrailers = MutableLiveData<List<TrailersResult>>()
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
        val response = mMainRepository.fetchTrailers(movieId)
        response.enqueue(object : Callback<TrailersResponse> {
            override fun onResponse(
                call: Call<TrailersResponse>,
                response: Response<TrailersResponse>
            ) {
                if (response.isSuccessful) {
                    movieTrailers.postValue(response.body()?.results)
                    Log.d("viewModel", "trails ")

                }
            }

            override fun onFailure(call: Call<TrailersResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


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