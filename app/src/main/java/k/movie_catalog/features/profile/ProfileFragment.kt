package k.movie_catalog.features.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.constants.Constants.DATE_PATTERN
import k.movie_catalog.databinding.FragmentProfileBinding
import k.movie_catalog.di.viewModelFactory
import k.movie_catalog.repositories.models.Gender
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

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
        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: ProfileUiState) {
        binding.progress.visibility = if (state.isLoading) View.VISIBLE else View.INVISIBLE

        state.profile?.let { profile ->
            binding.username.text = profile.nickName ?: getString(R.string.unknown_username)
            binding.email.text = profile.email
            binding.name.text = profile.name
            binding.birthDate.text =
                profile.birthDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN))
            when (profile.gender) {
                Gender.MALE -> binding.gender.check(R.id.male_btn)
                Gender.FEMALE -> binding.gender.check(R.id.female_btn)
                Gender.UNKNOW -> binding.gender.clearChecked()
            }
        }

        if (state.error == null) {
            binding.error.visibility = View.INVISIBLE
        } else {
            binding.error.visibility = View.VISIBLE
            binding.error.text = state.error
        }
    }

    private fun setupButtons() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }
}