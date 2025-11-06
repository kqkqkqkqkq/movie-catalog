package k.movie_catalog.features.auth.register

import android.graphics.Color
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
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.MaterialContainerTransform
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentRegisterBinding
import k.movie_catalog.di.viewModelFactory
import k.movie_catalog.repositories.models.Gender
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_activity_main
            duration = 500
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_activity_main
            duration = 500
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }
    }

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
        updateLoadingState(state)
    }

    private fun updateRegisterButton(state: RegisterUiState) {
        val isEnabledButton = !state.isLoading && viewModel.validateRegisterForm()
        binding.registerBtn.isEnabled = isEnabledButton
    }

    private fun updateErrorState(state: RegisterUiState) {
        binding.error.isVisible = state.error != null
        state.error.let {
            binding.error.text = it
        }
    }

    private fun updateLoadingState(state: RegisterUiState) {
        binding.progress.isVisible = state.isLoading

        if (state.isLoading) {
            binding.registerBtn.text = getString(R.string.loading)
            binding.registerBtn.isEnabled = false
        } else {
            binding.registerBtn.text = getString(R.string.register)
        }
    }

    private fun setupButtons() {
        binding.registerBtn.setOnClickListener {
            viewModel.register()
        }
        binding.loginBtn.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.logo to "logo_transition"
            )
            findNavController().navigate(R.id.action_register_to_login, null, null, extras)
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
            val localDate = Instant.ofEpochMilli(selection)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            viewModel.updateUserState(
                viewModel.registerState.value.user.copy(
                    birthDate = localDate
                )
            )
        }

        datePicker.show(childFragmentManager, "DATE_PICKER")
    }

    private fun formatDate(timestamp: Long): String {
        val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormatter.format(Date(timestamp))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}