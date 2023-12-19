package com.full.fichaje

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessAlarm
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AssuredWorkload
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.full.crm.network.API
import com.full.fichaje.navigation.NavigationManager
import com.full.fichaje.ui.theme.FICHAJETheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        API.mainActivity = this

        setContent{
            FICHAJETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavigationManager.InitializeNavigator()

                }
            }
        }
    }
}


@Composable
fun OptionsBar(modifier: Modifier = Modifier, selectedIcon: Int) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(height = 75.dp)
                .background(color = Color(0xFF6F19D2))
                .border(border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.51f))))
        Row(modifier.fillMaxWidth()){

            if (API.isAdministrator) {
                Column (
                    modifier = modifier
                        .weight(1f)
                        .fillMaxWidth()
                ){
                    Icon(
                        tint = if (selectedIcon == 0) Color(0xFFAE71F5) else Color.White,
                        imageVector = Icons.Rounded.AssuredWorkload,
                        contentDescription = "Vector",
                        modifier = Modifier
                            .size(size = 50.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                            .clickable {
                                if (selectedIcon != 0) NavigationManager.instance?.navigate(
                                    "administration"
                                )
                            }
                    )
                }
            }

            Column (
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ){
                Icon(
                    tint = if (selectedIcon == 1) Color(0xFFAE71F5) else Color.White,
                    imageVector = Icons.Rounded.DateRange,
                    contentDescription = "Vector",
                    modifier = Modifier
                        .size(size = 50.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        .clickable { if (selectedIcon != 1) NavigationManager.instance?.navigate("fichajes") }
                )
            }

            Column (
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ){
                Icon(
                    tint = if (selectedIcon == 2) Color(0xFFAE71F5) else Color.White,
                    imageVector = Icons.Rounded.AccessAlarm,
                    contentDescription = "Vector",
                    modifier = Modifier
                        .size(size = 50.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        .clickable { if (selectedIcon != 2) NavigationManager.instance?.navigate("fichajes") }
                )
            }

            Column (
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ){
                Icon(
                    tint = if (selectedIcon == 3) Color(R.color.primaryDescendant) else Color.White,
                    imageVector = Icons.Rounded.ExitToApp,
                    contentDescription = "Vector",
                    modifier = Modifier
                        .size(size = 50.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        .clickable { if (selectedIcon != 3) API.logout() }
                )
            }
        }
    }
}

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Preview()
@Composable
private fun PruebaPreview() {
    MaterialTheme {
        //Agenda()
        //Login(loginViewModel = LoginViewModel())
    }
}