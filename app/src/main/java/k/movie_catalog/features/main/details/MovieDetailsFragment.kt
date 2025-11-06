package k.movie_catalog.features.main.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentMovieDetailsBinding
import k.movie_catalog.di.AppComponentImpl
import k.movie_catalog.features.collections.edit.EditCollectionsViewModel
import k.movie_catalog.themes.MovieCatalogTheme
import kotlin.getValue

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel: MovieDetailsViewModel by viewModels {
        AppComponentImpl.viewModelFactory {
            MovieDetailsViewModel(
                moviesRepository = App.app.moviesRepository,
                favouritesRepository = App.app.favouritesRepository,
                dispatcherProvider = App.app.dispatcherProvider,
            )
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
            MovieCatalogTheme {
                MovieDetailsScreen(
                    movieId = args.movieId,
                    viewModel = viewModel,
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}