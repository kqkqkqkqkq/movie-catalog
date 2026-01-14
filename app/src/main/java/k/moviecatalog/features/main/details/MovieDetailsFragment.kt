package k.moviecatalog.features.main.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import k.moviecatalog.App
import k.moviecatalog.R
import k.moviecatalog.databinding.FragmentMovieDetailsBinding
import k.moviecatalog.di.AppComponentImpl
import k.moviecatalog.themes.MovieCatalogTheme
import java.util.UUID

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel: MovieDetailsViewModel by viewModels {
        AppComponentImpl.viewModelFactory {
            MovieDetailsViewModel()
        }
    }

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieDetailsBinding.bind(view)
        setupComposeView()
    }

    private fun setupComposeView() {
        binding.composeView.setContent {
            val id = UUID.fromString(args.movieId)
            viewModel.loadMovieDetails(id)
            MovieCatalogTheme {
                MovieDetailsScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        findNavController().navigateUp()
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}