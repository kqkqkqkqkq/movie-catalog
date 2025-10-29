package k.movie_catalog.features.collections.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentCollectionsBinding
import k.movie_catalog.databinding.FragmentCollectionsCreateBinding
import k.movie_catalog.databinding.FragmentCollectionsDetailsBinding
import k.movie_catalog.di.viewModelFactory
import k.movie_catalog.features.collections.CollectionsAdapter
import k.movie_catalog.features.collections.CollectionsViewModel
import k.movie_catalog.repositories.models.Collection
import kotlinx.coroutines.launch

class DetailsCollectionsFragment : Fragment(R.layout.fragment_collections_details) {

    private val args: DetailsCollectionsFragmentArgs by navArgs()

    private var _binding: FragmentCollectionsDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionsDetailsBinding.bind(view)
        setupButtons()
        setupText()
    }

    private fun setupButtons() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.editBtn.setOnClickListener {
            val action = DetailsCollectionsFragmentDirections
                .actionCollectionsDetailsToEdit(args.collection)
            findNavController().navigate(action)
        }
    }

    private fun setupText() {
        binding.title.text = args.collection.title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}