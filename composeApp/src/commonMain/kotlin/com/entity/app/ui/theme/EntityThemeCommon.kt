package com.entity.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

data class EntityColors(
  val mainText: Color,
  val bgMain: Color,
  val minorText: Color,
  val bgMinor: Color,
  val tintColor: Color,
  val mainOnControlText: Color,
  val bgControlMain: Color,
  val minorOnControlText: Color,
  val bgControlMinor: Color,
  val errorColor: Color,
)

data class EntityTypography(
  val heading: TextStyle,
  val body: TextStyle,
  val toolbar: TextStyle,
  val caption: TextStyle,
  val title: TextStyle,
  val subtitle: TextStyle,
)

data class EntityShape(
  val small: RoundedCornerShape,
  val medium: RoundedCornerShape,
  val big: RoundedCornerShape,
)

object EntityThemeProvider {
  @Composable
  fun colors() = LocalEntityColors.current

  @Composable
  fun typography() = LocalEntityTypography.current

  @Composable
  fun shapes() = LocalEntityShape.current
}

enum class SirenSize {
  Small, Medium, Big
}

val LocalEntityColors = staticCompositionLocalOf<EntityColors> {
  error("No colors provided")
}

val LocalEntityTypography = staticCompositionLocalOf<EntityTypography> {
  error("No font provided")
}

val LocalEntityShape = staticCompositionLocalOf<EntityShape> {
  error("No shapes provided")
}