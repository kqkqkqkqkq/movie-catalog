package k.movie_catalog.features.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: Long? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.btnRegister.setOnClickListener {
            showDatePicker()
//            authComponent.viewModel.testRegister() TODO("")
        }
        binding.btnLogin.setOnClickListener {
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
        binding.etBirthDate.setText(dateFormatter.format(date))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}