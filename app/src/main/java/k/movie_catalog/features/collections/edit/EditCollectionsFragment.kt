package k.movie_catalog.features.collections.edit

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
import k.movie_catalog.databinding.FragmentCollectionsEditBinding
import k.movie_catalog.di.viewModelFactory
import k.movie_catalog.features.collections.CollectionsAdapter
import k.movie_catalog.features.collections.CollectionsViewModel
import k.movie_catalog.features.collections.details.DetailsCollectionsFragmentArgs
import k.movie_catalog.repositories.models.Collection
import kotlinx.coroutines.launch
import kotlin.getValue

class EditCollectionsFragment : Fragment(R.layout.fragment_collections_edit) {

    private val args: EditCollectionsFragmentArgs by navArgs()

    private var _binding: FragmentCollectionsEditBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionsEditBinding.bind(view)
        binding.titleEt.setText(args.collection.title)
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}