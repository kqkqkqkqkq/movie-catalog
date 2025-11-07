package k.movie_catalog.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import k.movie_catalog.constants.SecurityConstants
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object Crypto {

    private val cipher = Cipher.getInstance(SecurityConstants.TRANSFORMATION)
    private val keyStore = KeyStore
        .getInstance("AndroidKeyStore")
        .apply { load(null) }

    private fun getKey(): SecretKey {
        val existingKey = keyStore
            .getEntry(SecurityConstants.KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator
            .getInstance(SecurityConstants.ALGORITHM)
            .apply {
                init(
                    KeyGenParameterSpec.Builder(
                        SecurityConstants.KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or
                                KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(SecurityConstants.BLOCK_MODE)
                        .setEncryptionPaddings(SecurityConstants.PADDING)
                        .setRandomizedEncryptionRequired(true)
                        .setUserAuthenticationRequired(false)
                        .build()
                )
            }
            .generateKey()
    }

    fun encrypt(bytes: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return iv + encrypted
    }

    fun decrypt(bytes: ByteArray): ByteArray {
        val iv = bytes.copyOfRange(0, cipher.blockSize)
        val data = bytes.copyOfRange(cipher.blockSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        return cipher.doFinal(data)
    }
}