package com.movies.pagination_library_3.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.movies.pagination_library_3.API_KEY
import com.movies.pagination_library_3.data.models.MoviesDetailsData
import com.movies.pagination_library_3.data.retrofit.ApiInterface
import java.lang.Exception

class MoviePagingSource(private val apiService: ApiInterface) :
    PagingSource<Int, MoviesDetailsData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesDetailsData> {

        return try {
            val position = params.key ?: 1
            val response = apiService.getTopPopularMovies(API_KEY, "en-US", position)
            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, MoviesDetailsData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}