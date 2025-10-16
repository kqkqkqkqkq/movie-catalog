package k.movie_catalog.features.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import k.movie_catalog.App
import k.movie_catalog.databinding.FragmentLoginBinding
import k.movie_catalog.di.viewModelFactory

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory {
            LoginViewModel(authRepository = App.appComponent.authRepository)
        }
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupTextFields()
    }


    private fun setupButtons() {
        binding.btnLogin.setOnClickListener {
//            viewModel.login()
        }
        binding.btnRegister.setOnClickListener {
//            viewModel.onOpenRegister()
        }
    }

    private fun setupTextFields() {
        binding.etUsername.doOnTextChanged { text, _, _, _ ->
//            val currentState = viewModel.loginState.value ?: LoginState(LoginCredential("", ""))
//            viewModel.updateLoginState(
//                currentState.copy(
//                    loginCredential = currentState.loginCredential.copy(
//                        userName = text?.toString() ?: ""
//                    )
//                )
//            )
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
//            val currentState = viewModel.loginState.value ?: LoginState(LoginCredential("", ""))
//            viewModel.updateLoginState(
//                currentState.copy(
//                    loginCredential = currentState.loginCredential.copy(
//                        password = text?.toString() ?: ""
//                    )
//                )
//            )
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