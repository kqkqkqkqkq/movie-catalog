package k.movie_catalog.features.collections.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentCollectionsDetailsBinding

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