package com.entity.app.ui.theme

import androidx.compose.ui.graphics.Color

val baseLightPalette = EntityColors(
  mainText = Color(0xFF3D454C),
  bgMain = Color(0xFFFFFFFF),
  minorText = Color(0xCC7A8A99),
  bgMinor = Color(0xFFF3F4F5),
  tintColor = Color.Magenta,
  mainOnControlText = Color(0xFF3D454C),
  bgControlMain = Color(0xFFF3F4F5),
  minorOnControlText = Color(0xCC7A8A99),
  bgControlMinor = Color(0xFFF3F4F5),
  errorColor = Color(0xFFFF3377)
)

val baseDarkPalette = EntityColors(
  mainText = Color(0xFFFFFFFF),
  bgMain = Color(0xFF000000),
  minorText = Color(0xFF969696),
  bgMinor = Color(0xFF3D454C),
  tintColor = Color.Magenta,
  mainOnControlText = Color(0xFF3D454C),
  bgControlMain = Color(0xFFF3F4F5),
  minorOnControlText = Color(0xCC7A8A99),
  bgControlMinor = Color(0xFFF3F4F5),
  errorColor = Color(0xFFFF3377)
)

val shimmerColorShades = listOf(
  Color(0xCC7A8A99).copy(0.9f),
  Color(0xCC7A8A99).copy(0.2f),
  Color(0xCC7A8A99).copy(0.9f)
)