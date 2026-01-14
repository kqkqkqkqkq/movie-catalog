package k.moviecatalog.features.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import k.moviecatalog.App
import k.moviecatalog.R
import k.moviecatalog.databinding.FragmentProfileBinding
import k.moviecatalog.di.AppComponentImpl
import k.moviecatalog.repositories.models.Gender
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels {
        AppComponentImpl.viewModelFactory {
            ProfileViewModel()
        }
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        setupButtons()
        observeState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: ProfileUiState) {
        binding.progress.visibility = if (state.isLoading) View.VISIBLE else View.INVISIBLE

        state.profile?.let { profile ->
            binding.usernameTv.text = profile.nickName ?: getString(R.string.unknown_username)
            binding.email.text = profile.email
            binding.name.text = profile.name
            binding.birthDate.text =
                profile.birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            when (profile.gender) {
                Gender.MALE -> binding.genderToggle.check(R.id.male_btn)
                Gender.FEMALE -> binding.genderToggle.check(R.id.female_btn)
                Gender.UNKNOW -> binding.genderToggle.clearChecked()
            }
        }
        binding.error.isVisible = state.error != null
        state.error.let {
            binding.error.text = it
        }

        println(state.profile)
    }

    private fun setupButtons() {
        binding.logoutBtn.setOnClickListener {
            viewModel.logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}