

package com.example.myica.screens.plans

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myica.R.drawable as AppIcon
import com.example.myica.R.string as AppText
import com.example.myica.common.composable.ActionToolbar
import com.example.myica.common.ext.smallSpacer
import com.example.myica.common.ext.toolbarActions

//@OptIn(ExperimentalLifecycleComposeApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun PlansScreen(
  openScreen: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: PlansViewModel = hiltViewModel()
) {

  val plans = viewModel
    .plans
    .collectAsStateWithLifecycle(emptyList())

  val options by viewModel.options

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = { viewModel.onAddClick(openScreen) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = modifier.padding(16.dp)
      ) {
        Icon(Icons.Filled.Add, "Add")
      }
    }
  ) {
    Column(modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()) {
      ActionToolbar(
        title = AppText.plans,
        modifier = Modifier.toolbarActions(),
        endActionIcon = AppIcon.ic_settings,
        endAction = { viewModel.onSettingsClick(openScreen) }
      )

      Spacer(modifier = Modifier.smallSpacer())

      LazyColumn {
        items(plans.value, key = { it.id }) { taskItem ->
          PlanItem(
            plan = taskItem,
            options = options,
            onCheckChange = { viewModel.onTaskCheckChange(taskItem) },
            onActionClick = { action -> viewModel.onPlanActionClick(openScreen, taskItem, action) }
          )
        }
      }
    }
  }

  LaunchedEffect(viewModel) { viewModel.loadPlanOptions() }
}