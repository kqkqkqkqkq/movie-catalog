package k.moviecatalog.features.collections.icons

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import k.moviecatalog.R
import k.moviecatalog.constants.UiConstants
import k.moviecatalog.databinding.FragmentIconBottomSheetBinding

class IconBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_icon_bottom_sheet) {

    private var _binding: FragmentIconBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var onIconSelectedListener: ((CollectionIcon) -> Unit)? = null
    fun setOnIconSelectedListener(onIconSelect: (CollectionIcon) -> Unit) {
        onIconSelectedListener = onIconSelect
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIconBottomSheetBinding.bind(view)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = IconAdapter { icon ->
            onIconClick(icon)
        }
        adapter.submitList(CollectionIcon.icons)
        // TODO("make grid prettier then now")
        binding.iconsRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), UiConstants.GRID_CELLS)
        binding.iconsRecyclerView.adapter = adapter
    }

    private fun onIconClick(icon: CollectionIcon) {
        onIconSelectedListener?.invoke(icon)
        dismiss()
    }
}