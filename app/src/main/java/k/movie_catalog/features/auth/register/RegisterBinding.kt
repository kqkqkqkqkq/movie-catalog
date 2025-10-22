package k.movie_catalog.features.auth.register

import k.movie_catalog.databinding.FragmentRegisterBinding

class RegisterBinding(
    registerBinding: FragmentRegisterBinding,
) {
    val root = registerBinding.root
    val username = registerBinding.usernameEt
    val email = registerBinding.emailEt
    val name = registerBinding.nameEt
    val password = registerBinding.passwordEt
    val repeatPassword = registerBinding.repeatPasswordEt
    val birthDate = registerBinding.birthDateEt
    val gender = registerBinding.genderToggle
    val registerButton = registerBinding.registerBtn
    val loginButton = registerBinding.loginBtn

    val nameTextInput = registerBinding.nameTil
    val emailTextInput = registerBinding.emailTil
    val usernameTextInput = registerBinding.usernameTil
    val passwordTextInput = registerBinding.passwordTil
    val repeatPasswordTextInput = registerBinding.repeatPasswordTil
    val birthDateTextInput = registerBinding.birthDateTil
}