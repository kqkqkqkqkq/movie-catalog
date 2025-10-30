package k.movie_catalog.features.collections.create

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentCollectionsCreateBinding
import k.movie_catalog.di.viewModelFactory
import k.movie_catalog.features.collections.icons.IconBottomSheetFragment
import kotlinx.coroutines.launch

class CreateCollectionsFragment : Fragment(R.layout.fragment_collections_create) {

    private val viewModel: CreateCollectionsViewModel by viewModels {
        viewModelFactory {
            CreateCollectionsViewModel(
                collectionsRepository = App.appComponent.collectionsRepository,
                dispatcherProvider = App.appComponent.dispatcherProvider,
            )
        }
    }

    private var _binding: FragmentCollectionsCreateBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionsCreateBinding.bind(view)
        setupButtons()
        setupTextFields()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectionsState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: CreateCollectionsUiState) {
        binding.error.isVisible = state.error != null
        state.error.let {
            binding.error.text = it
        }
        binding.progress.isVisible = state.isLoading

        val iconResId = viewModel.collectionsState.value.collection.icon
        if (iconResId != null) {
            binding.icon.setImageResource(iconResId)
        }
    }

    private fun setupTextFields() {
        binding.titleEt.doOnTextChanged { title, _, _, _ ->
            viewModel.updateCollection(
                viewModel.collectionsState.value.collection.copy(
                    title = title.toString(),
                )
            )
        }
    }

    private fun setupButtons() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.saveBtn.setOnClickListener {
            viewModel.createCollection {
                findNavController().navigateUp()
            }
        }
        binding.chooseIconBtn.setOnClickListener {
            showIconPickerBottomSheet()
        }
    }

    private fun showIconPickerBottomSheet() {
        val bottomSheet = IconBottomSheetFragment()
        bottomSheet.setOnIconSelectedListener { icon ->
            viewModel.updateCollection(
                viewModel.collectionsState.value.collection.copy(
                    icon = icon.icon,
                )
            )
        }
        bottomSheet.show(parentFragmentManager, "IconBottomSheet")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}