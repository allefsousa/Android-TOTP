package com.developer.allefsousa.toptp

import com.developer.allefsousa.toptp.config.TimeBasedOneTimePasswordConfig
import com.developer.allefsousa.toptp.enum.HmacAlgorithm
import com.developer.allefsousa.toptp.generator.OtpAuthUriBuilder
import com.developer.allefsousa.toptp.generator.TimeBasedOneTimePasswordGenerator
import java.util.*
import org.apache.commons.codec.binary.Base32
import java.util.concurrent.TimeUnit

class TotpAuthenticator(private val base32secret: ByteArray) {

    companion object {
        /**
         * Generates a 16-byte secret as a Base32-encoded string.
         *
         * Due to the overhead of 160% of the Base32 encoding, only 10 bytes are
         * needed for the random secret to generate a 16-byte array.
         */
        @Deprecated("Use ByteArray representation",
            replaceWith = ReplaceWith("createRandomSecretAsByteArray()"))
        fun createRandomSecret(): String {
            // val randomSecret = RandomSecretGenerator().createRandomSecret(10)
            return Base32().encodeAsString("".toByteArray())
        }

        /**
         * Generates a 16-byte secret as a Base32-encoded [ByteArray].
         *
         * Due to the overhead of 160% of the Base32 encoding, only 10 bytes are
         * needed for the random secret to generate a 16-byte array.
         */
        fun createRandomSecretAsByteArray(): ByteArray {
            //val randomSecret = RandomSecretGenerator().createRandomSecret(10)
            return Base32().encode("sd".toByteArray())
        }

        val CONFIG = TimeBasedOneTimePasswordConfig(30, TimeUnit.SECONDS, 6, HmacAlgorithm.SHA1)
    }


    private val timeBasedOneTimePasswordGenerator: TimeBasedOneTimePasswordGenerator =
        TimeBasedOneTimePasswordGenerator(Base32().decode(base32secret), CONFIG)


    @Deprecated("Use ByteArray representation",
        replaceWith = ReplaceWith("GoogleAuthenticator(ByteArray)"))
    constructor(base32secret: String) : this(base32secret.toByteArray())

    /**
     * Generates a code as a TOTP one-time password.
     *
     * @param timestamp the challenge for the code. The default value is the
     *                  current system time from [System.currentTimeMillis].
     */
    fun generate(timestamp: Date = Date(System.currentTimeMillis())): String {
        return timeBasedOneTimePasswordGenerator.generate(timestamp)
    }

    /**
     * Validates the given code.
     *
     * @param code the code calculated from the challenge to validate.
     * @param timestamp the used challenge for the code. The default value is the
     *                  current system time from [System.currentTimeMillis].
     */
    fun isValid(code: String, timestamp: Date = Date(System.currentTimeMillis())): Boolean {
        return code == generate(timestamp)
    }

    /**
     * Creates an [OtpAuthUriBuilder], which pre-configured with the secret, as
     * well as the fixed Google authenticator configuration for the algorithm,
     * code digits and time step.
     */
    fun otpAuthUriBuilder(): OtpAuthUriBuilder.Totp = timeBasedOneTimePasswordGenerator.otpAuthUriBuilder()

}