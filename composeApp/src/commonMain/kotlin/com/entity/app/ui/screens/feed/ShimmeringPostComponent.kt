package com.entity.app.ui.screens.feed//package com.entity.app.ui.components.feed
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.unit.dp
//import com.entity.app.ui.theme.EntityThemeProvider
//
//@Composable
//fun PlaceholderPostComponent(modifier: Modifier) {
//  Box(modifier) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//      Box(
//        modifier = Modifier
//          .fillMaxWidth()
//          .height(238.dp)
//          .background(EntityThemeProvider.colors().bgMinor),
//      )
//      Row(modifier = Modifier
//        .fillMaxWidth()
//        .padding(top = 12.dp)) {
//        Box(modifier = Modifier
//          .size(42.dp)
//          .clip(CircleShape)
//          .background(EntityThemeProvider.colors().bgMinor))
//        Column(modifier = Modifier.padding(start = 12.dp)) {
//          Box(modifier = Modifier
//            .height(16.dp)
//            .fillMaxWidth()
//            .shimmer(shimmerInstance)
//            .background(EntityTheme.colors.bgMinor))
//          Box(modifier = Modifier
//            .padding(top = 8.dp)
//            .height(12.dp)
//            .fillMaxWidth()
//            .shimmer(shimmerInstance)
//            .background(EntityTheme.colors.bgMinor))
//        }
//      }
//    }
//  }
//}