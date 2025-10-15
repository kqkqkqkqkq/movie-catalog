package k.movie_catalog.features.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.Router
import k.movie_catalog.BaseFragment
import k.movie_catalog.api.api.AuthApi
import k.movie_catalog.databinding.FragmentRegisterBinding
import k.movie_catalog.features.auth.AuthViewModel
import k.movie_catalog.features.auth.di.AuthComponent
import k.movie_catalog.features.auth.di.IAuthComponentDependencies

class RegisterFragment : BaseFragment() {
    private var _binding: FragmentRegisterBinding? = null
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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.btnRegister.setOnClickListener {
            authComponent.viewModel.testRegister()
        }
        binding.btnLogin.setOnClickListener {
            authComponent.viewModel.onOpenLogin()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }
}