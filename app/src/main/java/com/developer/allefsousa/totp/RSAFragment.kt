package com.developer.allefsousa.totp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.developer.allefsousa.totop.R
import com.developer.allefsousa.totop.databinding.FragmentRsaBinding
import com.developer.allefsousa.totp.cryptography.RsaKeystoreWrapper


class RSAFragment : Fragment(R.layout.fragment_rsa) {
    private var _binding: FragmentRsaBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRsaBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnGenerate?.setOnClickListener {
            generateKeyPair()
        }

    }

    private fun generateKeyPair() {
        val rsaWrapper = RsaKeystoreWrapper()
        val pair = rsaWrapper.createAsymmetricKeyPair(binding?.alias?.text.toString())
        val pair2 = rsaWrapper.getAsymmetricKeyPair(binding?.alias?.text.toString())
        if (pair2 != null) {
            rsaWrapper.savePrivateKey(pair2,binding?.alias?.text.toString())
        }
        val stringBuilder = StringBuilder()
        stringBuilder.append("Key Alias = ${binding?.alias?.text.toString()}\n\n\n Chave Privada = ${pair2?.private}  \n\n\nChave Publica = ${pair2?.public}")
        binding?.result?.text = stringBuilder

        Log.d(RSAFragment::class.java.name, "Chave publica: ${pair2?.public?.algorithm}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = RSAFragment()
    }
}