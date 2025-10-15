package k.movie_catalog.features.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import k.movie_catalog.BaseFragment
import k.movie_catalog.api.api.AuthApi
import k.movie_catalog.databinding.FragmentLoginBinding
import k.movie_catalog.features.auth.AuthViewModel
import k.movie_catalog.features.auth.di.AuthComponent
import k.movie_catalog.features.auth.di.IAuthComponentDependencies
import k.movie_catalog.repositories.models.LoginCredential

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authComponent by lazy {
        val dependencies = object : IAuthComponentDependencies {
            override val authApi: AuthApi = appComponent.authApi
            override val router: Router = appComponent.router
        }
        AuthComponent(dependencies)
    }

    private val viewModel by lazy {
        getViewModel<AuthViewModel>(authComponent.viewModelFactory)
    }

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
            viewModel.login()
        }
        binding.btnRegister.setOnClickListener {
            viewModel.onOpenRegister()
        }
    }

    private fun setupTextFields() {
        binding.etUsername.doOnTextChanged { text, _, _, _ ->
            val currentState = viewModel.loginState.value ?: LoginState(LoginCredential("", ""))
            viewModel.updateLoginState(
                currentState.copy(
                    loginCredential = currentState.loginCredential.copy(
                        userName = text?.toString() ?: ""
                    )
                )
            )
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            val currentState = viewModel.loginState.value ?: LoginState(LoginCredential("", ""))
            viewModel.updateLoginState(
                currentState.copy(
                    loginCredential = currentState.loginCredential.copy(
                        password = text?.toString() ?: ""
                    )
                )
            )
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