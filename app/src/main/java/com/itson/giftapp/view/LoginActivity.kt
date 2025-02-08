package com.itson.giftapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itson.giftapp.databinding.ActivityLoginBinding
import com.itson.giftapp.view.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            // Aquí implementa la lógica para iniciar sesión
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Aquí debes agregar la lógica para verificar las credenciales del usuario
            // Si las credenciales son válidas, navega a la actividad principal
            if (isValidCredentials(email, password)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Muestra un mensaje de error si las credenciales son incorrectas
                // Por ejemplo, usando Toast o mostrando un TextView en el layout
            }
        }

        binding.tvRegister.setOnClickListener {
            // Navega a la actividad de registro si el usuario aún no tiene una cuenta
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidCredentials(email: String, password: String): Boolean {
        // Aquí debes implementar la lógica para verificar las credenciales del usuario
        // Por ejemplo, puedes validar el formato del correo electrónico y la longitud de la contraseña
        // También puedes realizar la autenticación con un backend o una base de datos local
        return email.isNotBlank() && password.isNotBlank()
    }
}
