package com.entity.app.ui.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.entity.app.ui.theme.EntityTheme

@Composable
fun EntityCircularProgressIndicator(modifier: Modifier) {
  CircularProgressIndicator(
    modifier = modifier,
    color = EntityTheme.colors().mainText,
    strokeWidth = 1.dp,
    backgroundColor = Color.Transparent,
  )
}
