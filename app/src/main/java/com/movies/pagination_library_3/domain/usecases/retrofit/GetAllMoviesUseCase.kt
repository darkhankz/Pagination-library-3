package com.movies.pagination_library_3.domain.usecases.retrofit

import com.movies.pagination_library_3.data.repository.MainRepositoryImpl

class GetAllMoviesUseCase(private val mainRepositoryImpl: MainRepositoryImpl) {
    fun invoke() = mainRepositoryImpl.getAllMovies()
}