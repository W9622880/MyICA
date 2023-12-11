
package com.example.myica.screens.plans

import androidx.compose.runtime.mutableStateOf
import com.example.myica.model.Plan
import com.example.myica.model.service.ConfigurationService
import com.example.myica.model.service.LogService
import com.example.myica.model.service.StorageService
import com.example.myica.navigation.EDIT_PLAN_SCREEN
import com.example.myica.navigation.SETTINGS_SCREEN
import com.example.myica.navigation.Plan_ID
import com.example.myica.screens.TodoListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlansViewModel @Inject constructor(
  logService: LogService,
  private val storageService: StorageService,
  private val configurationService: ConfigurationService
) : TodoListViewModel(logService) {
  val options = mutableStateOf<List<String>>(listOf())

  val plans = storageService.plans

  fun loadPlanOptions() {
    val hasEditOption = configurationService.isShowTaskEditButtonConfig
    options.value = PlanActionOption.getOptions(hasEditOption)
  }

  fun onTaskCheckChange(plan: Plan) {
    launchCatching { storageService.update(plan.copy(completed = !plan.completed)) }
  }

  fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_PLAN_SCREEN)

  fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

  fun onPlanActionClick(openScreen: (String) -> Unit, plan: Plan, action: String) {
    when (PlanActionOption.getByTitle(action)) {
      PlanActionOption.EditPlan -> openScreen("$EDIT_PLAN_SCREEN?$Plan_ID={${plan.id}}")
      PlanActionOption.ToggleFlag -> onFlagPlanClick(plan)
      PlanActionOption.DeletePlan -> onDeletePlanClick(plan)
    }
  }

  private fun onFlagPlanClick(plan: Plan) {
    launchCatching { storageService.update(plan.copy(flag = !plan.flag)) }
  }

  private fun onDeletePlanClick(plan: Plan) {
    launchCatching { storageService.delete(plan.id) }
  }
}
