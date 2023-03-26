package com.developer.allefsousa.totop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    }
}