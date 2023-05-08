package com.entity.app.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RippleTransparentButton(
  onClick: () -> Unit,
  modifier: Modifier,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  content: @Composable RowScope.() -> Unit
) {
  Button(
    onClick = onClick,
    contentPadding = contentPadding,
    elevation = null,
    colors = ButtonDefaults.buttonColors(
      backgroundColor = Color.Transparent,
      disabledBackgroundColor = Color.Transparent,
    ),
    modifier = modifier,
    content = content
  )
}