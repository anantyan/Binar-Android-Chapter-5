package id.anantyan.moviesapp.ui.main.home_detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import com.google.android.material.snackbar.Snackbar
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.databinding.FragmentHomeDetailBinding
import id.anantyan.moviesapp.repository.MoviesRepository
import id.anantyan.moviesapp.ui.main.MainSharedViewModel
import id.anantyan.moviesapp.ui.main.home.HomeViewModelFactory
import id.anantyan.moviesapp.ui.main.profile.adapter
import id.anantyan.utils.Resource
import id.anantyan.utils.checkInternet
import id.anantyan.utils.dividerVertical

class HomeDetailFragment : Fragment() {

    private var _binding: FragmentHomeDetailBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainSharedViewModel by activityViewModels()
    private val viewModel: HomeDetailViewModel by viewModels {
        HomeViewModelFactory(MoviesRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
        onBindObserver()
    }

    @SuppressLint("SetTextI18n")
    private fun onBindObserver() {
        viewModel.getMovieByIdResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressHomeDetail.visibility = View.GONE
                    binding.layoutHomeDetail.visibility = View.VISIBLE
                    binding.backdropPath.load(it.data?.backdropPath) {
                        crossfade(true)
                        size(ViewSizeResolver(binding.backdropPath))
                    }
                    binding.imgPosterPath.load(it.data?.posterPath) {
                        crossfade(true)
                        placeholder(R.drawable.ic_outline_image_24)
                        error(R.drawable.ic_outline_image_not_supported_24)
                        transformations(RoundedCornersTransformation(16F))
                        size(ViewSizeResolver(binding.imgPosterPath))
                    }
                    binding.txtTitle.text = it.data?.title
                    binding.releaseDate.text = it.data?.releaseDate
                    binding.status.text = "Status : ${it.data?.status}"
                    binding.voteAverage.text = "${it.data?.voteAverage}/10"
                    binding.voteAverageStar.numStars = 5
                    binding.voteAverageStar.stepSize = 0.5F
                    binding.voteAverageStar.rating = (it.data?.voteAverage?.div(2))?.toFloat() ?: 0f
                    binding.homepage.text = if (it.data?.homepage.isNullOrEmpty()) "-" else it.data?.homepage
                    binding.homepage.setOnClickListener { v ->
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(if (it.data?.homepage.isNullOrEmpty()) "not-found" else it.data?.homepage))
                        startActivity(browserIntent)
                    }
                    binding.runtime.text = "Durasi : ${it.data?.runtime} menit"
                    binding.overview.text = it.data?.overview
                    adapterGenres.differ(it.data?.genres!!)
                    binding.rvGenres.adapter = adapterGenres.init()
                }
                is Resource.Loading -> {
                    binding.progressHomeDetail.visibility = View.VISIBLE
                    binding.layoutHomeDetail.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.progressHomeDetail.visibility = View.GONE
                    binding.layoutHomeDetail.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        viewModel.getCreditsByIdResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    adapterCaster.differ(it.data!!)
                    binding.rvCaster.adapter = adapterCaster.init()
                    binding.progressCaster.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.progressCaster.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressCaster.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        requireContext().checkInternet().observe(viewLifecycleOwner) {
            if (it == true) {
                sharedViewModel.movieId.observe(viewLifecycleOwner) { i ->
                    viewModel.getMovieById(i.toString())
                }
            } else {
                onSnackbar("No internet access!")
            }
        }
    }

    private fun onBindView() {
        binding.rvCaster.setHasFixedSize(true)
        binding.rvCaster.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvCaster.itemAnimator = DefaultItemAnimator()
        binding.rvCaster.isNestedScrollingEnabled = true

        binding.rvGenres.setHasFixedSize(true)
        binding.rvGenres.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvGenres.itemAnimator = DefaultItemAnimator()
        binding.rvGenres.isNestedScrollingEnabled = true
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