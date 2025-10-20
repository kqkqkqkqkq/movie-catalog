package k.movie_catalog.features.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentProfileBinding
import k.movie_catalog.di.viewModelFactory
import k.movie_catalog.features.auth.login.LoginBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels {
        viewModelFactory {
            ProfileViewModel(
                authRepository = App.appComponent.authRepository,
                tokenRepository = App.appComponent.tokenRepository,
                dispatcherProvider = App.appComponent.dispatcherProvider,
            )
        }
    }
    private var _binding: ProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profileBinding = FragmentProfileBinding.bind(view)
        _binding = ProfileBinding(profileBinding)
        setupButtons()
    }

    private fun setupButtons() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}