package k.movie_catalog.constants

import android.security.keystore.KeyProperties

object SecurityConstants {
    const val KEY_ALIAS = "movie_catalog"
    const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
    const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
    const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
}