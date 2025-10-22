package k.movie_catalog.features.auth.login

import k.movie_catalog.databinding.FragmentLoginBinding

class LoginBinding(
    loginBinding: FragmentLoginBinding,
) {
    val root = loginBinding.root
    val loginButton = loginBinding.loginBtn
    val registerButton = loginBinding.registerBtn
    val usernameEditText = loginBinding.usernameEt
    val passwordEditText = loginBinding.passwordEt
    val progress = loginBinding.progress
    val errorText = loginBinding.error
    val usernameTextInput = loginBinding.usernameTil
    val passwordTextInput = loginBinding.usernameTil
}