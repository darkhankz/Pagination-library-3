package com.movies.pagination_library_3.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movies.pagination_library_3.MAIN
import com.movies.pagination_library_3.R
import com.movies.pagination_library_3.databinding.FragmentFavoriteBinding
import com.movies.pagination_library_3.presentation.view.adapter.FavoriteAdapter
import com.movies.pagination_library_3.presentation.viewModel.FavoriteViewModel

class FavoriteFragment : Fragment(), FavoriteAdapter.OnItemClickListener {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var mMoviesRecycler: RecyclerView
    private lateinit var mMoviesAdapter: FavoriteAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        mMoviesRecycler = binding.recyclerViewFavoriteFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel = FavoriteViewModel()
        mMoviesRecycler.layoutManager = GridLayoutManager(context, 2)
        initObservers()

    }


    private fun initObservers() {
        favoriteViewModel.apply {
            getAllMovies().observe(MAIN) {
                mMoviesAdapter = FavoriteAdapter(it.asReversed(), this@FavoriteFragment)
                mMoviesRecycler.adapter = mMoviesAdapter
            }
        }
    }

    override fun onItemClick(movieId: Int) {
        val fragment = DetailFragment()
        val bundle = Bundle()
        bundle.putInt("movie_id", movieId)
        fragment.arguments = bundle
        MAIN.navController.navigate(R.id.action_favoriteFragment_to_detailFragment, bundle)
    }
}
