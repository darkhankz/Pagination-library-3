package com.movies.pagination_library_3.view

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
import androidx.paging.LoadState
import com.movies.pagination_library_3.MAIN
import com.movies.pagination_library_3.R
import com.movies.pagination_library_3.databinding.FragmentMainBinding
import com.movies.pagination_library_3.model.repository.MyViewModelFactory
import com.movies.pagination_library_3.view.adapter.MoviePagerAdapter
import com.movies.pagination_library_3.viewModel.MainViewModel
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
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelFactory()
        initAdapterClickListener()
        viewModel.init()
        errorObserver()
        initLoadState()
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
    }

    private fun errorObserver() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViewModelFactory() {
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory()
        )[MainViewModel::class.java]

    }

    private fun initLoadState() {
        adapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            )
                binding.progressDialog.isVisible = true
            else {
                binding.progressDialog.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(activity, it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
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