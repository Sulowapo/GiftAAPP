package com.itson.giftapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itson.giftapp.databinding.ActivityRegisterBinding
import com.itson.giftapp.view.MainActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            // Aquí implementa la lógica para registrar una nueva cuenta
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Aquí debes agregar la lógica para validar los datos del registro
            // Por ejemplo, puedes verificar que todos los campos estén completos y que el formato del correo electrónico sea válido
            if (isValidRegistration(name, email, password)) {
                // Si la validación es exitosa, puedes realizar el registro del usuario
                // Por ejemplo, puedes enviar los datos al backend para crear una nueva cuenta
                // Luego, navega a la actividad principal
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Muestra un mensaje de error si hay algún problema con los datos de registro
                // Por ejemplo, usando Toast o mostrando un TextView en el layout
            }
        }

        binding.tvLogin.setOnClickListener {
            // Navega a la actividad de inicio de sesión si el usuario ya tiene una cuenta
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidRegistration(name: String, email: String, password: String): Boolean {
        // Aquí debes implementar la lógica para validar los datos del registro
        // Por ejemplo, puedes verificar que todos los campos estén completos y que el formato del correo electrónico sea válido
        return name.isNotBlank() && email.isNotBlank() && password.isNotBlank()
    }
}
