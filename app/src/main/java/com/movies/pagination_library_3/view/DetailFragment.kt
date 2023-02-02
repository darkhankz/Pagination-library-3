package com.movies.pagination_library_3.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.movies.pagination_library_3.MAIN
import com.movies.pagination_library_3.R
import com.movies.pagination_library_3.data.MoviesDetailsData
import com.movies.pagination_library_3.databinding.FragmentDetailBinding
import com.movies.pagination_library_3.model.repository.SaveShared
import com.movies.pagination_library_3.viewModel.FavoriteViewModel
import kotlinx.coroutines.launch


class DetailFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private var isFavorite = false
    private lateinit var favoriteClick: ImageView
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        favoriteClick = binding.imgDetailFavorite
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel = FavoriteViewModel()
        val movieId = arguments?.getInt("movie_id")
        if (movieId != null) {
            favoriteViewModel.getMoviesDetails(movieId)
            initObservers()
            Log.d("MovieDetailsActivity", "Movie ID: $movieId")
        }
    }
    private fun initObservers() {
        favoriteViewModel.apply {
            moviesDetails.observe(MAIN) {
                setMovieInformation(it)
            }
        }
    }



    private fun setMovieInformation(movieDetails: MoviesDetailsData){
        val valueBoolean = SaveShared.getFavorite(MAIN, movieDetails.id.toString())
        if (isFavorite != valueBoolean){
            favoriteClick.setImageResource(R.drawable.baseline_favorite_24)

        } else{
            favoriteClick.setImageResource(R.drawable.baseline_favorite_border_24)

        }
        binding.moviesDetailsTitle.text = movieDetails.title
        binding.moviesDetailsBodyOverview.text = movieDetails.overview
        binding.moviesDetailsDate.text = movieDetails.release_date
        binding.moviesDetailsScore.text = movieDetails.vote_average.toString()
        context?.let { Glide.with(it.applicationContext).load("https://image.tmdb.org/t/p/w300"+movieDetails.backdrop_path).into(binding.moviesDetailsImageBanner) }

        favoriteClick.setOnClickListener{
            isFavorite = if(isFavorite == valueBoolean) {
                favoriteClick.setImageResource(R.drawable.baseline_favorite_24)
                SaveShared.setFavorite(MAIN, movieDetails.id.toString(), true )
                favoriteViewModel.insert(movieDetails){}
                true
            } else {
                favoriteClick.setImageResource(R.drawable.baseline_favorite_border_24)
                SaveShared.setFavorite(MAIN, movieDetails.id.toString(), false )
                favoriteViewModel.delete(movieDetails){}

                false

            }
        }

    }
}