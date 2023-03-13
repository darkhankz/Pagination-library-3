package com.movies.pagination_library_3.domain.usecases.retrofit

import com.movies.pagination_library_3.data.repository.MainRepository
import com.movies.pagination_library_3.data.repository.MainRepositoryImpl

class FetchTrailersUseCase(private val mainRepositoryImpl: MainRepositoryImpl) {
    suspend fun invoke(movieId: Int) = mainRepositoryImpl.fetchTrailers(movieId = movieId)
}