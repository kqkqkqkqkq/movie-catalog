package k.movie_catalog.features.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import k.movie_catalog.R

class CollectionsFragment : Fragment(R.layout.fragment_collections) {

    private val viewModel: CollectionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_collections, container, false)
    }
}