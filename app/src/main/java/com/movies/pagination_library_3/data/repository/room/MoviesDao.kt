package com.movies.pagination_library_3.data.repository.room


import androidx.lifecycle.LiveData
import androidx.room.*
import com.movies.pagination_library_3.data.models.MoviesDetailsData

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(moviesData: MoviesDetailsData)

    @Delete
    suspend fun delete(moviesData: MoviesDetailsData)

    @Query("SELECT * FROM movie_table")
    fun getAllFavoriteMovies(): LiveData<List<MoviesDetailsData>>
}