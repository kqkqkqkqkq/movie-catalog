package k.movie_catalog.features.auth.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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

    private var _binding: LoginBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginBinding = FragmentLoginBinding.bind(view)
        _binding = LoginBinding(loginBinding)
        observeViewModel()
        setupButtons()
        setupTextFields()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                updateUI(state)
            }
        }
    }


    private fun setupButtons() {
        binding.loginButton.setOnClickListener {
            viewModel.login()
        }
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }
    }


    private fun setupTextFields() {
        binding.usernameEditText.doOnTextChanged { username, _, _, _ ->
            viewModel.updateUsername(username.toString())
        }
        binding.passwordEditText.doOnTextChanged { password, _, _, _ ->
            viewModel.updatePassword(password.toString())
        }
    }


    private fun updateUI(state: LoginUiState) {
        updateLoginButton(state)
        updateErrorState(state)
        updateLoadingState(state)
    }


    private fun updateLoginButton(state: LoginUiState) {
        val isFormValid = state.loginCredential.username.isNotBlank() &&
                state.loginCredential.password.isNotBlank()

        val isEnabledButton = isFormValid && !state.isLoading
        binding.loginButton.isEnabled = isEnabledButton

        if (isEnabledButton) {
            binding.loginButton.setTextColor(androidx.appcompat.R.attr.colorPrimary)
        } else {
            binding.loginButton.setBackgroundColor(androidx.appcompat.R.attr.colorPrimary)
//            binding.loginButton.setTextColor(disabledTextColor)
        }
    }

    private fun updateErrorState(state: LoginUiState) {
        val error = state.error
        println("DEBUG: Error state = $error")
        if (error != null) {
            binding.errorTextView.visibility = View.VISIBLE
            binding.errorTextView.text = error
        } else {
            binding.errorTextView.visibility = View.INVISIBLE
        }
    }

    private fun updateLoadingState(state: LoginUiState) {
        binding.progressBar.isVisible = state.isLoading

        if (state.isLoading) {
            binding.loginButton.text = "Loading..."
            binding.loginButton.isEnabled = false
        } else {
            binding.loginButton.text = "Login"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}