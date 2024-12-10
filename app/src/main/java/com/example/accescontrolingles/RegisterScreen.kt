package com.example.accescontrolingles

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.accescontrolingles.model.Alumno
import com.example.accescontrolingles.ui.theme.styles
import com.example.accescontrolingles.utils.Response
import com.example.accescontrolingles.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    goToHomeScreen: (Alumno) -> Unit,
    backHome: () -> Unit
) {

    val signUpState by authViewModel.signUpFlow.collectAsState(Response.Finish)

    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var nombreAlumno by remember { mutableStateOf("") }
    var apellidoAlumno by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var cargando by remember { mutableStateOf(false) }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (registerButton, title, usernameTextField,
            matriculaTxtFld, nombreAlumnoTextField, apellidoAlumnoTextField,backButton, mailTextField,
            passwordTextField, confirmPasswordTextField, apellidosTextField) = createRefs()

        Text(text = "Registrar padre/tutor", modifier = Modifier.constrainAs(title) {
            top.linkTo(parent.top, margin = 30.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }, fontSize = 30.sp, fontFamily = FontFamily(Font(R.font.raleway_light)))

        TextField(
            value = nombre, onValueChange = { nombre = it },
            label = { Text("Nombre del padre", fontFamily = FontFamily(Font(R.font.raleway_light))) },
            modifier = Modifier.constrainAs(usernameTextField) {
                top.linkTo(title.bottom, margin = 26.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints

            }, textStyle = styles
        )
        TextField(
            value = apellidos, onValueChange = { apellidos = it },
            label = { Text("Apellido del padre", fontFamily = FontFamily(Font(R.font.raleway_light))) },
            modifier = Modifier.constrainAs(apellidosTextField) {
                top.linkTo(usernameTextField.bottom, margin = 26.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints

            }, textStyle = styles
        )
        TextField(
            value = nombreAlumno, onValueChange = { nombreAlumno = it },
            label = { Text("Nombre del alumno", fontFamily = FontFamily(Font(R.font.raleway_light))) },
            modifier = Modifier.constrainAs(nombreAlumnoTextField) {
                top.linkTo(apellidosTextField.bottom, margin = 26.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints

            }, textStyle = styles
        )
        TextField(
            value = apellidoAlumno, onValueChange = { apellidoAlumno = it },
            label = { Text("Apellido del alumno", fontFamily = FontFamily(Font(R.font.raleway_light))) },
            modifier = Modifier.constrainAs(apellidoAlumnoTextField) {
                top.linkTo(nombreAlumnoTextField.bottom, margin = 26.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints

            }, textStyle = styles
        )
        TextField(
            value = correo, onValueChange = { correo = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text("Correo", fontFamily = FontFamily(Font(R.font.raleway_light))) },
            modifier = Modifier.constrainAs(mailTextField) {
                top.linkTo(apellidoAlumnoTextField.bottom, margin = 46.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints

            }, textStyle = styles
        )
        TextField(
            value = password, onValueChange = { password = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text("Contraseña", fontFamily = FontFamily(Font(R.font.raleway_light))) },
            modifier = Modifier.constrainAs(passwordTextField) {
                top.linkTo(mailTextField.bottom, margin = 26.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints

            }, textStyle = styles
        )
        TextField(
            value = confirmPassword, onValueChange = { confirmPassword = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = {
                Text(
                    "Confirmar contraseña",
                    fontFamily = FontFamily(Font(R.font.raleway_light))
                )
            },
            modifier = Modifier.constrainAs(confirmPasswordTextField) {
                top.linkTo(passwordTextField.bottom, margin = 26.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints

            }, textStyle = styles
        )

        Button(onClick = {
            if (nombre.isEmpty()) {
                Toast.makeText(context, "Agrega tu nombre", Toast.LENGTH_SHORT).show()
            } else if (apellidos.isEmpty()) {
                Toast.makeText(context, "Agrega tus apellidos", Toast.LENGTH_SHORT).show()
            } else if (nombreAlumno.isEmpty()) {
                Toast.makeText(context, "Agrega nombre del alumno", Toast.LENGTH_SHORT).show()
            } else if (correo.isEmpty()) {
                Toast.makeText(context, "Ingresa un correo", Toast.LENGTH_SHORT).show()
            } else if (!correo.contains("@")) {
                Toast.makeText(context, "Ingresa un coreo valido", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(context, "Ingresa una contraseña", Toast.LENGTH_SHORT).show()
            } else if (confirmPassword.isEmpty()) {
                Toast.makeText(context, "Confirma tu contraseña", Toast.LENGTH_SHORT).show()
            } else {
                if (password == confirmPassword) {
                    authViewModel.signUp(correo, password)
                } else {
                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }, modifier = Modifier.constrainAs(registerButton) {
            top.linkTo(confirmPasswordTextField.bottom, margin = 26.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)

        }) {
            Text(text = "Registrar", fontFamily = FontFamily(Font(R.font.raleway_light)))

        }


        Button(onClick = {
            backHome()
        }, modifier = Modifier.constrainAs(backButton) {
            top.linkTo(confirmPasswordTextField.bottom, margin = 26.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            bottom.linkTo(parent.bottom, margin = 16.dp)

        }) {
            Text(text = "Regresar", fontFamily = FontFamily(Font(R.font.raleway_light)))
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = ""
            )

        }
    }
    if (cargando){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Text(
                text = "Cargando...",
                fontFamily = FontFamily(Font(R.font.raleway_light))
            )

        }
    }
    LaunchedEffect(key1 = signUpState) {

        authViewModel.signUpFlow.collect { signUpFlow ->
            when (signUpFlow) {

                is Response.Failed -> {


                    Toast.makeText(
                        context,
                        (signUpState as Response.Failed).exception.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Response.Finish -> { cargando = false }
                Response.Loading -> {
                    cargando = true
                }

                is Response.Success<Boolean> -> {
                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    goToHomeScreen(
                        Alumno(
                            uid = "",
                            nombre = nombre,
                            matricula = correo.substring(0, 8),
                            apellido = apellidos,
                            correo = correo,
                            nombreAlumno = nombreAlumno,
                            apellidoAlumno = apellidoAlumno,
                            isIn = false
                        )
                    )

                }
            }
    }
}
}