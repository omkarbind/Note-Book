package com.om.notebook.ui

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.om.notebook.R
import com.om.notebook.utils.Prefs
import com.om.notebook.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val isLoginSuccess by viewModel.isLoginSuccess

    // 🔥 Autofill
    LaunchedEffect(Unit) {
        if (Prefs.isRemember(context)) {
            email = Prefs.getEmail(context)
            password = Prefs.getPassword(context)
            rememberMe = true
        }
    }

    // 🔥 Navigation after login
    LaunchedEffect(isLoginSuccess) {
        if (isLoginSuccess) {

            if (rememberMe) {
                Prefs.saveUser(context, email, password, true)
            } else {
                Prefs.clear(context)
            }

            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    val alphaAnim = remember { Animatable(0f) }
    val offsetY = remember { Animatable(100f) }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(1f, tween(1000))
        offsetY.animateTo(0f, tween(1000))
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.backgroung_login),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .graphicsLayer {
                    alpha = alphaAnim.value
                    translationY = offsetY.value
                },
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Welcome Back 👋", fontSize = 26.sp)

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },

                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),

                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisible = !passwordVisible
                            }) {
                                Icon(
                                    imageVector = if (passwordVisible)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        },

                        modifier = Modifier.fillMaxWidth()
                    )

//                    Spacer(modifier = Modifier.height(10.dp))
//
//                    // ✅ Remember Me
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Checkbox(
//                            checked = rememberMe,
//                            onCheckedChange = { rememberMe = it }
//                        )
//                        Text("Remember Me")
//                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Forgot Password?",
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable {
                                navController.navigate("forgot")
                            }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            viewModel.login(email, password)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Login")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Don't have an account? Register",
                        modifier = Modifier.clickable {
                            navController.navigate("register")
                        }
                    )
                }
            }
        }
    }
}