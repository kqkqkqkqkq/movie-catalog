package k.movie_catalog.features.auth.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentRegisterBinding
import k.movie_catalog.di.viewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModels {
        viewModelFactory {
            RegisterViewModel(
                authRepository = App.appComponent.authRepository,
                tokenRepository = App.appComponent.tokenRepository,
            )
        }
    }
    private var _binding: RegisterBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: Long? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val registerBinding = FragmentRegisterBinding.bind(view)
        _binding = RegisterBinding(registerBinding)
        setupButtons()
    }

    private fun setupButtons() {
        binding.registerButton.setOnClickListener {
            showDatePicker()
//            authComponent.viewModel.testRegister() TODO("")
        }
        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
//            .setTitleText(getString(R.string.select_birth_date))
            .setSelection(selectedDate ?: MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedDate = selection
            updateSelectedDate(selection)
        }

        datePicker.show(childFragmentManager, "DATE_PICKER")
    }

    private fun updateSelectedDate(timestamp: Long) {
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(timestamp)
        binding.birthDate.setText(dateFormatter.format(date))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}