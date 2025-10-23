package k.movie_catalog.features.auth.register

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
import com.google.android.material.datepicker.MaterialDatePicker
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentRegisterBinding
import k.movie_catalog.di.viewModelFactory
import k.movie_catalog.features.auth.login.LoginUiState
import k.movie_catalog.repositories.models.Gender
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModels {
        viewModelFactory {
            RegisterViewModel(
                authRepository = App.appComponent.authRepository,
                tokenRepository = App.appComponent.tokenRepository,
                dispatcherProvider = App.appComponent.dispatcherProvider,
            )
        }
    }
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)
        observeViewModel()
        setupButtons()
        setupTextFields()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: RegisterUiState) {
        updateRegisterButton(state)
        updateErrorState(state)
//        updateLoadingState(state)
    }

    private fun updateRegisterButton(state: RegisterUiState) {
        val isEnabledButton = state.isLoading
        binding.registerBtn.isEnabled = isEnabledButton
    }

    private fun updateErrorState(state: RegisterUiState) {
        binding.error.isVisible = state.error != null
        state.error.let {
            binding.error.text = it
        }
    }

    private fun setupButtons() {
        binding.registerBtn.setOnClickListener {
            viewModel.register()
        }
        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }
        binding.maleBtn.setOnClickListener {
            viewModel.updateUserState(
                viewModel.registerState.value.user.copy(
                    gender = Gender.MALE
                )
            )
        }
        binding.femaleBtn.setOnClickListener {
            viewModel.updateUserState(
                viewModel.registerState.value.user.copy(
                    gender = Gender.FEMALE
                )
            )
        }
    }

    private fun setupTextFields() {
        binding.usernameEt.doOnTextChanged { username, _, _, _ ->
            viewModel.updateUserState(
                viewModel.registerState.value.user.copy(
                    username = username.toString()
                )
            )
        }
        binding.nameEt.doOnTextChanged { name, _, _, _ ->
            viewModel.updateUserState(
                viewModel.registerState.value.user.copy(
                    name = name.toString()
                )
            )
        }
        binding.passwordEt.doOnTextChanged { password, _, _, _ ->
            viewModel.updateUserState(
                viewModel.registerState.value.user.copy(
                    password = password.toString()
                )
            )
        }
        binding.repeatPasswordEt.doOnTextChanged { passwordRepeat, _, _, _ ->
            viewModel.updateUserState(
                viewModel.registerState.value.user.copy(
                    passwordRepeat = passwordRepeat.toString()
                )
            )
        }
        binding.emailEt.doOnTextChanged { email, _, _, _ ->
            viewModel.updateUserState(
                viewModel.registerState.value.user.copy(
                    email = email.toString()
                )
            )
        }
        binding.birthDateEt.doOnTextChanged { birthDate, _, _, _ ->
            viewModel.updateUserState(
                viewModel.registerState.value.user.copy(
                    birthDate = birthDate.toString()
                )
            )
        }
        binding.birthDateEt.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            binding.birthDateEt.setText(formatDate(selection))

            viewModel.updateUserState(
                viewModel.registerState.value.user.copy(
                    birthDate = formatDate(selection)
                )
            )
        }

        datePicker.show(childFragmentManager, "DATE_PICKER")
    }

    private fun formatDate(timestamp: Long): String {
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormatter.format(Date(timestamp))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}