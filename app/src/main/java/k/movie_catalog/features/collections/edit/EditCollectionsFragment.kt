package k.movie_catalog.features.collections.edit

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
import androidx.navigation.fragment.navArgs
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentCollectionsEditBinding
import k.movie_catalog.di.AppComponentImpl
import k.movie_catalog.features.collections.icons.IconBottomSheetFragment
import kotlinx.coroutines.launch

class EditCollectionsFragment : Fragment(R.layout.fragment_collections_edit) {

    private val args: EditCollectionsFragmentArgs by navArgs()

    private val viewModel: EditCollectionsViewModel by viewModels {
        AppComponentImpl.viewModelFactory {
            EditCollectionsViewModel(
                collectionsRepository = App.instance.collectionsRepository,
                dispatcherProvider = App.instance.dispatcherProvider,
            )
        }
    }

    private var _binding: FragmentCollectionsEditBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionsEditBinding.bind(view)
        viewModel.updateCollection(args.collection)
        observeViewModel()
        setupTextFields()
        setupButtons()
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

    private fun updateUI(state: EditCollectionsUiState) {
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
        binding.titleEt.setText(args.collection.title)
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
            viewModel.editCollection(args.collection.title) {
                findNavController().navigateUp()
            }
        }
        binding.deleteBtn.setOnClickListener {
            viewModel.deleteCollection(args.collection.title) {
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