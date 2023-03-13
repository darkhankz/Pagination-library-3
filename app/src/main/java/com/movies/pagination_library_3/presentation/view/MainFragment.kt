package com.movies.pagination_library_3.presentation.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.movies.pagination_library_3.MAIN
import com.movies.pagination_library_3.R
import com.movies.pagination_library_3.data.repository.MainRepositoryImpl
import com.movies.pagination_library_3.databinding.FragmentMainBinding
import com.movies.pagination_library_3.data.repository.MyViewModelFactory
import com.movies.pagination_library_3.domain.usecases.retrofit.GetAllMoviesUseCase
import com.movies.pagination_library_3.presentation.view.adapter.MoviePagerAdapter
import com.movies.pagination_library_3.presentation.viewModel.MainViewModel
import kotlinx.coroutines.launch

class MainFragment : Fragment(), MenuProvider {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val adapter = MoviePagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.recyclerViewFragment.adapter = adapter
        initMenuHost()
        return binding.root
    }

    private fun initMenuHost() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelFactory()
        initAdapterClickListener()
        viewModel.init()
        observer()
        Log.d("AAA", "Fragment Created")


    }

    private fun initAdapterClickListener() {
        adapter.setOnMovieClickListener(object : MoviePagerAdapter.OnMovieClickListener {
            override fun onMovieClick(movieId: Int) {
                val fragment = DetailFragment()
                val bundle = Bundle()
                bundle.putInt("movie_id", movieId)
                fragment.arguments = bundle
                MAIN.navController.navigate(R.id.action_mainFragment_to_detailFragment, bundle)
            }
        })
        viewModel.initLoadState(adapter)
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressDialog.isVisible = isLoading
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }

    }


    private fun initViewModelFactory() {
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(
                getAllMoviesUseCase = GetAllMoviesUseCase(mainRepositoryImpl = MainRepositoryImpl())
            )
        )[MainViewModel::class.java]
    }


    private fun observer() {
        lifecycleScope.launch {
            viewModel.getMovieList().observe(viewLifecycleOwner) {
                it?.let {
                    adapter.submitData(lifecycle, it)
                }
            }
        }
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.item_favorite -> {
                MAIN.navController.navigate(R.id.action_mainFragment_to_favoriteFragment)
                true
            }
            else -> false
        }
    }

}