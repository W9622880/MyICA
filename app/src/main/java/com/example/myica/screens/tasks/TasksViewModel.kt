
package com.example.myica.screens.tasks

import androidx.compose.runtime.mutableStateOf
import com.example.myica.model.Task
import com.example.myica.model.service.ConfigurationService
import com.example.myica.model.service.LogService
import com.example.myica.model.service.StorageService
import com.example.myica.navigation.EDIT_TASK_SCREEN
import com.example.myica.navigation.SETTINGS_SCREEN
import com.example.myica.navigation.TASK_ID
import com.example.myica.screens.TodoListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
  logService: LogService,
  private val storageService: StorageService,
  private val configurationService: ConfigurationService
) : TodoListViewModel(logService) {
  val options = mutableStateOf<List<String>>(listOf())

  val tasks = storageService.tasks

  fun loadTaskOptions() {
    val hasEditOption = configurationService.isShowTaskEditButtonConfig
    options.value = TaskActionOption.getOptions(hasEditOption)
  }

  fun onTaskCheckChange(task: Task) {
    launchCatching { storageService.update(task.copy(completed = !task.completed)) }
  }

  fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

  fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

  fun onTaskActionClick(openScreen: (String) -> Unit, task: Task, action: String) {
    when (TaskActionOption.getByTitle(action)) {
      TaskActionOption.EditTask -> openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}")
      TaskActionOption.ToggleFlag -> onFlagTaskClick(task)
      TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
    }
  }

  private fun onFlagTaskClick(task: Task) {
    launchCatching { storageService.update(task.copy(flag = !task.flag)) }
  }

  private fun onDeleteTaskClick(task: Task) {
    launchCatching { storageService.delete(task.id) }
  }
}
