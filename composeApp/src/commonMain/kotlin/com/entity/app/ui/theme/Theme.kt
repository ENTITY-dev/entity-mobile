package com.entity.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.core.component.inject
import androidx.compose.ui.unit.sp
import com.entity.app.data.FeedListRepository
import com.entity.app.ui.theme.LocalEntityShape
import com.entity.app.ui.theme.LocalEntityTypography
import com.entity.app.ui.theme.SirenSize
import com.entity.app.ui.theme.baseDarkPalette
import com.entity.app.ui.theme.baseLightPalette
import com.entity.app.ui.theme.SirenSize.Big
import com.entity.app.ui.theme.SirenSize.Medium
import com.entity.app.ui.theme.SirenSize.Small

@Composable
fun EntityTheme(
  textSize: SirenSize = Medium,
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colors = if (darkTheme) {
    baseDarkPalette
  } else {
    baseLightPalette
  }

  val typography = EntityTypography(
    heading = TextStyle(
      fontSize = when (textSize) {
        Small -> 12.sp
        Medium -> 18.sp
        Big -> 32.sp
      },
      fontWeight = FontWeight.Normal
    ),
    body = TextStyle(
      fontSize = when (textSize) {
        Small -> 10.sp
        Medium -> 14.sp
        Big -> 18.sp
      },
      fontWeight = FontWeight.Normal,
    ),
    toolbar = TextStyle(
      fontSize = when (textSize) {
        Small -> 14.sp
        Medium -> 18.sp
        Big -> 20.sp
      },
      fontWeight = FontWeight.Normal
    ),
    caption = TextStyle(
      fontSize = when (textSize) {
        Small -> 10.sp
        Medium -> 12.sp
        Big -> 14.sp
      }
    ),
    title = TextStyle(
      fontSize = when (textSize) {
        Small -> 10.sp
        Medium -> 16.sp
        Big -> 14.sp
      }
    ),
    subtitle = TextStyle(
      fontSize = when (textSize) {
        Small -> 10.sp
        Medium -> 12.sp
        Big -> 14.sp
      }
    )
  )

  val shapes = EntityShape(
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(16.dp),
    big = RoundedCornerShape(20.dp)
  )

  CompositionLocalProvider(
    LocalEntityColors provides colors,
    LocalEntityTypography provides typography,
    LocalEntityShape provides shapes,
    content = content
  )
}