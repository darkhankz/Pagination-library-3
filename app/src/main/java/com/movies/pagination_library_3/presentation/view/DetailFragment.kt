package com.movies.pagination_library_3.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.pagination_library_3.MAIN
import com.movies.pagination_library_3.R
import com.movies.pagination_library_3.data.models.MoviesDetailsData
import com.movies.pagination_library_3.databinding.FragmentDetailBinding
import com.movies.pagination_library_3.data.repository.SaveShared
import com.movies.pagination_library_3.data.repository.room.MoviesDao
import com.movies.pagination_library_3.presentation.view.adapter.TrailersAdapter
import com.movies.pagination_library_3.presentation.viewModel.FavoriteViewModel

class DetailFragment : Fragment() {
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var isFavorite = false
    private lateinit var favoriteClick: ImageView
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var trailersRecyclerView: RecyclerView
    private lateinit var trailersAdapter: TrailersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        favoriteClick = binding.imgDetailFavorite
        trailersRecyclerView = binding.trailersRecyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel = FavoriteViewModel()
        trailersRecyclerView.layoutManager = LinearLayoutManager(context)
        val movieId = arguments?.getInt("movie_id")

        favoriteViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressDialogDetailFragment.isVisible = isLoading

            favoriteViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                }
            }
        }
        movieId?.let {
            favoriteViewModel.getMoviesDetails(it)
            initObservers()
            favoriteViewModel.fetchTrailers(movieId)
            initObserversTrailers()
        }
    }



    private fun initObservers() {
        favoriteViewModel.apply {
            moviesDetails.observe(MAIN) {
                setMovieInformation(it)
            }
        }
    }

    private fun initObserversTrailers() {
        favoriteViewModel.apply {
            movieTrailersLiveData.observe(MAIN) {
                if (it != null) {
                    trailersAdapter = TrailersAdapter(it)
                    trailersRecyclerView.adapter = trailersAdapter
                }
            }
        }
    }


    private fun setMovieInformation(movieDetails: MoviesDetailsData) {
        val valueBoolean = updateFavoriteButtonImage(movieDetails)
        initViews(movieDetails)
        updateFavoriteStatus(valueBoolean, movieDetails)
    }

    private fun updateFavoriteStatus(
        valueBoolean: Boolean,
        movieDetails: MoviesDetailsData
    ) {
        favoriteClick.setOnClickListener {
            isFavorite = if (isFavorite == valueBoolean) {
                favoriteClick.setImageResource(R.drawable.baseline_favorite_24)
                SaveShared.setFavorite(MAIN, movieDetails.id.toString(), true)
                favoriteViewModel.insert(movieDetails) {}
                true
            } else {
                favoriteClick.setImageResource(R.drawable.baseline_favorite_border_24)
                SaveShared.setFavorite(MAIN, movieDetails.id.toString(), false)
                favoriteViewModel.delete(movieDetails) {}
                false
            }
        }
    }

    private fun initViews(movieDetails: MoviesDetailsData) {
        binding.moviesDetailsTitle.text = movieDetails.title
        binding.moviesDetailsBodyOverview.text = movieDetails.overview
        binding.moviesDetailsDate.text = movieDetails.release_date
        binding.moviesDetailsScore.text = movieDetails.vote_average.toString()
        context?.let {
            Glide.with(it.applicationContext)
                .load("https://image.tmdb.org/t/p/w300" + movieDetails.backdrop_path)
                .into(binding.moviesDetailsImageBanner)
        }
    }

    private fun updateFavoriteButtonImage(movieDetails: MoviesDetailsData): Boolean {
        val valueBoolean = SaveShared.getFavorite(MAIN, movieDetails.id.toString())
        if (isFavorite != valueBoolean) {
            favoriteClick.setImageResource(R.drawable.baseline_favorite_24)

        } else {
            favoriteClick.setImageResource(R.drawable.baseline_favorite_border_24)

        }
        return valueBoolean
    }


}