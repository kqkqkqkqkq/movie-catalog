package k.movie_catalog.features.auth.login

import k.movie_catalog.databinding.FragmentLoginBinding

class LoginBinding(
    loginBinding: FragmentLoginBinding
) {
    val root = loginBinding.root
    val loginButton = loginBinding.btnLogin
    val registerButton = loginBinding.btnRegister
    val usernameEditText = loginBinding.etUsername
    val passwordEditText = loginBinding.etPassword
    val progressBar = loginBinding.progressBar
    val errorTextView = loginBinding.tvError
    val usernameTextInputLayout = loginBinding.tilUserName
    val passwordTextInputLayout = loginBinding.tilPassword
}