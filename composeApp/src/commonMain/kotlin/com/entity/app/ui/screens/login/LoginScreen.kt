package com.entity.app.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.entity.app.ui.EntityButtonComponent
import com.entity.app.ui.screens.login.LoginScreenAction.AuthSuccess
import com.entity.app.ui.screens.login.LoginScreenEvent.PasswordChange
import com.entity.app.ui.screens.login.LoginScreenEvent.UsernameChange
import com.entity.app.ui.screens.login.LoginScreenViewState.Normal
import com.entity.app.ui.theme.EntityTheme

object LoginScreen : Screen {
  @Composable
  override fun Content() {
    val screenModel = rememberScreenModel { LoginScreenViewModel() }
    val viewState by screenModel.viewStates().collectAsState()
    val viewAction by screenModel.viewActions().collectAsState(null)

    when (val state = viewState) {
      is Normal -> {
        Column(
          modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text("ENTITY", color = EntityTheme.colors().mainText, fontSize = 48.sp)

          val colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = EntityTheme.colors().mainText,
            backgroundColor = Color.Transparent,
            focusedBorderColor = EntityTheme.colors().mainText,
            focusedLabelColor = EntityTheme.colors().mainText,
            cursorColor = EntityTheme.colors().mainText,
            placeholderColor = EntityTheme.colors().minorText,
            unfocusedLabelColor = EntityTheme.colors().mainText,
            unfocusedBorderColor = EntityTheme.colors().mainText,
            disabledBorderColor = EntityTheme.colors().mainText.copy(ContentAlpha.disabled),
            disabledLabelColor = EntityTheme.colors().mainText.copy(ContentAlpha.disabled),
            disabledTextColor = EntityTheme.colors().mainText.copy(ContentAlpha.disabled),
            disabledPlaceholderColor = EntityTheme.colors().minorText.copy(ContentAlpha.disabled)
          )

          OutlinedTextField(
            value = state.username,
            onValueChange = {
              screenModel.obtainEvent(UsernameChange(it))
            },
            label = { Text(text = "Username") },
            placeholder = { Text(text = "Enter username...") },
            colors = colors,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            singleLine = true,
            enabled = !state.isLoading
          )

          OutlinedTextField(
            value = state.password,
            onValueChange = {
              screenModel.obtainEvent(PasswordChange(it))
            },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter password...") },
            colors = colors,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            enabled = !state.isLoading
          )

          if (state.notificationText.isNotBlank()) {
            Text(
              state.notificationText,
              color = EntityTheme.colors().errorColor,
              style = EntityTheme.typography().title,
              modifier = Modifier.padding(top = 16.dp),
              textAlign = TextAlign.Center
            )
          }

          EntityButtonComponent(
            modifier = Modifier.padding(top = 16.dp),
            text = "Sign in",
            enabled = !state.isLoading
          ) {
            screenModel.obtainEvent(LoginScreenEvent.OnLoginClick)
          }
          EntityButtonComponent(
            modifier = Modifier.padding(top = 16.dp),
            text = "Sign up",
            enabled = !state.isLoading
          ) {
            screenModel.obtainEvent(LoginScreenEvent.OnRegistrationClick)
          }
        }
      }
    }

    when (val action = viewAction) {
      AuthSuccess -> { }

      null -> {
        //no-op
      }
    }
  }
}