package k.movie_catalog.features.profile

import androidx.lifecycle.ViewModel
import k.movie_catalog.repositories.token.ITokenRepository

class ProfileViewModel(
    private val tokenRepository: ITokenRepository,
) : ViewModel() {

}