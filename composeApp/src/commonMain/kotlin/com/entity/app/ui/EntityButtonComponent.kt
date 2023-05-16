package com.entity.app.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.entity.app.ui.theme.EntityTheme

@Composable
fun EntityButtonComponent(
  modifier: Modifier = Modifier,
  text: String,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {

  val interactionSource = remember { MutableInteractionSource() }
  val rippleColor = Color.White

  Button(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .defaultMinSize(minHeight = 46.dp)
      .indication(
        interactionSource = interactionSource,
        indication = rememberRipple(
          color = rippleColor
        )
      ),
    onClick = onClick,
    interactionSource = interactionSource,
    border = BorderStroke(
      1.dp, if (enabled) EntityTheme.colors().bgControlMain else EntityTheme.colors().bgControlMain.copy(
        ContentAlpha.disabled
      )
    ),
    colors = ButtonDefaults.buttonColors(
      backgroundColor = Color.Black,
      disabledBackgroundColor = Color.Black
    ),
    enabled = enabled
  ) {
    Text(
      text = text,
      style = EntityTheme.typography().title,
      color = if (enabled) EntityTheme.colors().mainText else EntityTheme.colors().mainText.copy(
        ContentAlpha.disabled
      )
    )
  }
}