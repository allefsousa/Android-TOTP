package com.developer.allefsousa.totop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import com.developer.allefsousa.toptp.TotpAuthenticator
import com.developer.allefsousa.totop.databinding.ActivityGeneratorBinding
import java.net.Authenticator

private const val SECRET = "TTDPFTLZE7BZBL7PEOGWEAK3IH7ASXUS"
class GeneratorActivity : AppCompatActivity(R.layout.activity_generator) {
    lateinit var binding: ActivityGeneratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val authenticator = TotpAuthenticator(SECRET.toByteArray())
        Log.d(GeneratorActivity::class.java.name, "onCreate: ${authenticator.generate()}")

        binding.btnGenerate.setOnClickListener {
            generated(authenticator)
        }

        binding.secret.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                generated(authenticator)
                return@setOnEditorActionListener true
            }
            false
        }


    }

    private fun generated(authenticator: TotpAuthenticator) {
        if (binding.secret.text!!.isEmpty()) {
            binding.textSecret.text = "Secret: ${SECRET}"
            binding.textCode.text = "TOTP Code: ${authenticator.generate()}"
        } else {
            val newAuth = TotpAuthenticator(binding.secret.text.toString().toByteArray())
            binding.textSecret.text = "Secret: ${binding.secret.text.toString()}"
            binding.textCode.text = "TOTP Code: ${newAuth.generate()}"
            binding.secret.text!!.clear()
        }
    }
}