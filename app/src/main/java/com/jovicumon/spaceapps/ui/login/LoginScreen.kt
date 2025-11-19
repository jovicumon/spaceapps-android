package com.jovicumon.spaceapps.ui.login

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    // Estados para los campos del formulario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Estados para posibles mensajes de error en los campos
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // Snackbar para avisar de credenciales incorrectas
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    // Pequeña función para validar el formulario
    fun validateForm(): Boolean {
        var isValid = true

        // Valido el email mínimamente (no vacío y con "@")
        if (email.isBlank()) {
            emailError = "El email no puede estar vacío"
            isValid = false
        } else if (!email.contains("@")) {
            emailError = "Formato de email no válido"
            isValid = false
        } else {
            emailError = null
        }

        // Valido la contraseña (no vacía y algo de longitud)
        if (password.isBlank()) {
            passwordError = "La contraseña no puede estar vacía"
            isValid = false
        } else if (password.length < 4) {
            passwordError = "La contraseña debe tener al menos 4 caracteres"
            isValid = false
        } else {
            passwordError = null
        }

        return isValid
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Iniciar sesión",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = emailError != null,
                    singleLine = true
                )
                if (emailError != null) {
                    Text(
                        text = emailError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campo contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = passwordError != null,
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                if (passwordError != null) {
                    Text(
                        text = passwordError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de login
                Button(
                    onClick = {
                        // Primero valido formulario
                        if (validateForm()) {
                            // Aquí compruebo credenciales "de mentira" para la práctica
                            // Si quiero, puedo cambiar estos valores por otros.
                            val validEmail = "admin@lasalle.es"
                            val validPassword = "admin1234"

                            if (email == validEmail && password == validPassword) {
                                onLoginSuccess()
                            } else {
                                // Si las credenciales no coinciden, aviso por snackbar
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Credenciales incorrectas"
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Iniciar sesión")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botón "He olvidado mis datos de acceso"
                TextButton(
                    onClick = {
                        // Abro una URL en el navegador.
                        val uri = Uri.parse("https://lasallefp.com/contactar/")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    }
                ) {
                    Text("He olvidado mis datos de acceso")
                }
            }
        }
    }
}
