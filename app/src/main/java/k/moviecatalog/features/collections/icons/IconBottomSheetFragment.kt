package k.moviecatalog.features.collections.icons

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView
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
        setupGrid()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupGrid() {
        val icons = CollectionIcon.icons
        val span = UiConstants.GRID_CELLS
        val spacing = dpToPx(16)

        binding.iconsGrid.columnCount = span
        binding.iconsGrid.post {
            val itemSize = calculateItemSize(binding.iconsGrid.width, span, spacing)
            if (itemSize <= 0) return@post

            binding.iconsGrid.removeAllViews()

            icons.forEachIndexed { index, icon ->
                val cell = createIconCell(icon, index, span, spacing, itemSize)
                binding.iconsGrid.addView(cell)
            }
        }
    }

    private fun calculateItemSize(totalWidth: Int, span: Int, spacing: Int): Int {
        val totalSpacing = spacing * (span + 1)
        return (totalWidth - totalSpacing) / span
    }

    private fun createIconCell(
        icon: CollectionIcon,
        index: Int,
        span: Int,
        spacing: Int,
        size: Int,
    ): View {
        val column = index % span
        val row = index / span

        val view = layoutInflater.inflate(R.layout.item_icon, binding.iconsGrid, false)

        val left = if (column == 0) spacing else spacing / 2
        val right = if (column == span - 1) spacing else spacing / 2
        val top = if (row == 0) spacing else spacing / 2
        val bottom = spacing / 2

        view.layoutParams = GridLayout.LayoutParams(
            GridLayout.spec(row, GridLayout.FILL),
            GridLayout.spec(column, GridLayout.FILL)
        ).apply {
            width = size
            height = size
            setMargins(left, top, right, bottom)
        }

        val card = view.findViewById<MaterialCardView>(R.id.icon_container)
        val image = view.findViewById<ImageView>(R.id.icon)
        image.setImageResource(icon.icon)
        card.radius = size.toFloat()
        card.setOnClickListener {
            onIconSelectedListener?.invoke(icon)
            dismiss()
        }

        return view
    }

    private fun dpToPx(dp: Int) = (dp * resources.displayMetrics.density).toInt()
}