package id.anantyan.moviesapp.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.databinding.FragmentHomeBinding
import id.anantyan.moviesapp.repository.MoviesRepository
import id.anantyan.moviesapp.ui.main.MainSharedViewModel
import id.anantyan.utils.Resource
import id.anantyan.utils.checkInternet

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainSharedViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(MoviesRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView(view)
        onBindObserver()
    }

    private fun onBindView(view: View) {
        binding.rvTrending.setHasFixedSize(true)
        binding.rvTrending.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvTrending.itemAnimator = DefaultItemAnimator()
        binding.rvTrending.isNestedScrollingEnabled = true
        adapterTrending.onClick { _, movieId ->
            val destination = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movieId)
        }

        binding.rvPopular.setHasFixedSize(true)
        binding.rvPopular.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvPopular.itemAnimator = DefaultItemAnimator()
        binding.rvPopular.isNestedScrollingEnabled = true
        adapterPopular.onClick { _, movieId ->
            val destination = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movieId)
        }

        binding.rvTopRated.setHasFixedSize(true)
        binding.rvTopRated.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvTopRated.itemAnimator = DefaultItemAnimator()
        binding.rvTopRated.isNestedScrollingEnabled = true
        adapterTopRated.onClick { _, movieId ->
            val destination = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movieId)
        }

        binding.rvUpcoming.setHasFixedSize(true)
        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvUpcoming.itemAnimator = DefaultItemAnimator()
        binding.rvUpcoming.isNestedScrollingEnabled = true
        adapterUpComing.onClick { _, movieId ->
            val destination = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movieId)
        }

        binding.rvNowPlaying.setHasFixedSize(true)
        binding.rvNowPlaying.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvNowPlaying.itemAnimator = DefaultItemAnimator()
        binding.rvNowPlaying.isNestedScrollingEnabled = true
        adapterNowPlaying.onClick { _, movieId ->
            val destination = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movieId)
        }
    }

    private fun onBindObserver() {
        viewModel.trendingResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressTrending.visibility = View.GONE
                    adapterTrending.differ(it.data!!)
                    binding.rvTrending.adapter = adapterTrending.init()
                }
                is Resource.Loading -> {
                    binding.progressTrending.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressTrending.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        viewModel.popularResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressPopular.visibility = View.GONE
                    adapterPopular.differ(it.data!!)
                    binding.rvPopular.adapter = adapterPopular.init()
                }
                is Resource.Loading -> {
                    binding.progressPopular.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressPopular.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        viewModel.topRatedResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressTopRated.visibility = View.GONE
                    adapterTopRated.differ(it.data!!)
                    binding.rvTopRated.adapter = adapterTopRated.init()
                }
                is Resource.Loading -> {
                    binding.progressTopRated.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressTopRated.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        viewModel.nowPlayingResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressNowPlaying.visibility = View.GONE
                    adapterNowPlaying.differ(it.data!!)
                    binding.rvNowPlaying.adapter = adapterNowPlaying.init()
                }
                is Resource.Loading -> {
                    binding.progressNowPlaying.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressNowPlaying.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        viewModel.upcomingResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressUpcoming.visibility = View.GONE
                    adapterUpComing.differ(it.data!!)
                    binding.rvUpcoming.adapter = adapterUpComing.init()
                }
                is Resource.Loading -> {
                    binding.progressUpcoming.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressUpcoming.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        requireContext().checkInternet().observe(viewLifecycleOwner) {
            if (it == true) {
                viewModel.getMoviesApi()
            } else {
                onSnackbar("No internet access!")
            }
        }
    }

    private fun onSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}