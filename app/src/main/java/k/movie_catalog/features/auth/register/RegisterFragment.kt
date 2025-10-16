package k.movie_catalog.features.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import k.movie_catalog.App
import k.movie_catalog.databinding.FragmentRegisterBinding
import k.movie_catalog.di.viewModelFactory
import k.movie_catalog.features.auth.login.LoginViewModel
import kotlin.getValue

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels {
        viewModelFactory {
            RegisterViewModel(authRepository = App.appComponent.authRepository)
        }
    }
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


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
//            authComponent.viewModel.testRegister() TODO("")
        }
        binding.btnLogin.setOnClickListener {
//            authComponent.viewModel.onOpenLogin() TODO("")
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