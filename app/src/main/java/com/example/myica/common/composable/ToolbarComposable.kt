package com.example.myica.common.composable

import android.Manifest
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.myica.model.service.PlanNotificationService
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun BasicToolbar(@StringRes title: Int) {
    TopAppBar(
        title = { Text(text = stringResource(id = title)) },
        backgroundColor = toolbarColor()
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActionToolbar(
    @StringRes title: Int,
    @DrawableRes endActionIcon: Int,
    modifier: Modifier,
    endAction: () -> Unit
) {

    TopAppBar(
        title = { Text(text = stringResource(id = title)) },
        backgroundColor = toolbarColor(),
        actions = {
            Box(modifier) {
                IconButton(onClick = endAction) {
                    Icon(
                        painter = painterResource(id = endActionIcon),
                        contentDescription = "Action"
                    )
                }
            }
        }
    )
}

@Composable
private fun toolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): Color {
    return if (darkTheme) MaterialTheme.colors.secondary else
        MaterialTheme.colors.primaryVariant
}