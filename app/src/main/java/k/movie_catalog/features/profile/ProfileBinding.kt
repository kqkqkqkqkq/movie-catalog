package k.movie_catalog.features.profile

import k.movie_catalog.databinding.FragmentProfileBinding

class ProfileBinding(
    profileBinding: FragmentProfileBinding,
) {
    val root = profileBinding.root
    val avatar = profileBinding.avatar
    val username = profileBinding.usernameTv
    val emailText = profileBinding.emailTv
    val email = profileBinding.email
    val nameText = profileBinding.nameTv
    val name = profileBinding.name
    val birthDateText = profileBinding.birthDateTv
    val birthDate = profileBinding.birthDate
    val genderText = profileBinding.genderTv
    val gender = profileBinding.genderToggle
    val progress = profileBinding.progress
    val error = profileBinding.error
    val logoutButton = profileBinding.logoutBtn
}