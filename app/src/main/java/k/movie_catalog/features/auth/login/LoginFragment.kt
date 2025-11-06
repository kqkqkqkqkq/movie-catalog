package k.movie_catalog.features.auth.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentLoginBinding
import k.movie_catalog.di.viewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory {
            LoginViewModel(
                authRepository = App.appComponent.authRepository,
                tokenRepository = App.appComponent.tokenRepository,
                dispatcherProvider = App.appComponent.dispatcherProvider,
            )
        }
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        observeViewModel()
        setupButtons()
        setupTextFields()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun setupButtons() {
        binding.loginBtn.setOnClickListener {
            viewModel.login()
        }
        binding.registerBtn.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.logoImage to "logo_transition"
            )
            findNavController().navigate(R.id.action_login_to_register, null, null, extras)
        }
    }

    private fun setupTextFields() {
        binding.usernameEt.doOnTextChanged { username, _, _, _ ->
            viewModel.updateLoginState(
                viewModel.loginState.value.loginCredential.copy(
                    username = username.toString()
                )
            )
        }
        binding.passwordEt.doOnTextChanged { password, _, _, _ ->
            viewModel.updateLoginState(
                viewModel.loginState.value.loginCredential.copy(
                    password = password.toString()
                )
            )
        }
    }

    private fun updateUI(state: LoginUiState) {
        updateLoginButton(state)
        updateErrorState(state)
        updateLoadingState(state)
    }

    private fun updateLoginButton(state: LoginUiState) {
        val isFormValid = viewModel.validateForm()
        val isEnabledButton = isFormValid && !state.isLoading
        binding.loginBtn.isEnabled = isEnabledButton
    }

    private fun updateErrorState(state: LoginUiState) {
        binding.error.isVisible = state.error != null
        state.error.let {
            binding.error.text = it
        }
    }

    private fun updateLoadingState(state: LoginUiState) {
        binding.progress.isVisible = state.isLoading

        if (state.isLoading) {
            binding.loginBtn.text = getString(R.string.loading)
            binding.loginBtn.isEnabled = false
        } else {
            binding.loginBtn.text = getString(R.string.login)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}