package k.movie_catalog.features.profile

import k.movie_catalog.databinding.FragmentProfileBinding

class ProfileBinding(
    profileBinding: FragmentProfileBinding,
) {
    val root = profileBinding.root
    val logoutButton = profileBinding.logOutButton
    val username = profileBinding.tvUsername
    val email = profileBinding.email
    val name = profileBinding.name
    val progressBar = profileBinding.progressBar
    val error = profileBinding.tvError
}