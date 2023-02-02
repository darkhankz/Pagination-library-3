package com.movies.pagination_library_3

import com.movies.pagination_library_3.model.repository.MoviesRoomImpl
import com.movies.pagination_library_3.view.MainActivity

lateinit var MAIN: MainActivity
const val NETWORK_PAGE_SIZE = 25
const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "6e76ecffda0a59dc4f19a343c6e7648a"
lateinit var moviesRoomImpl: MoviesRoomImpl
