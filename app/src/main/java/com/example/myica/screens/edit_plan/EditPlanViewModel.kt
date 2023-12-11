package com.example.myica.screens.edit_plan

import androidx.compose.runtime.mutableStateOf
import com.example.myica.common.ext.idFromParameter
import com.example.myica.model.service.LogService
import com.example.myica.model.service.StorageService
import com.example.myica.navigation.PLAN_DEFULT_ID
import com.example.myica.screens.TodoListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.myica.model.Plan
import java.text.SimpleDateFormat
import java.util.*

@HiltViewModel
class EditPlanViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
) : TodoListViewModel(logService) {
    val plan = mutableStateOf(Plan())

    fun initialize(taskId: String) {
        launchCatching {
            if (taskId != PLAN_DEFULT_ID) {
                plan.value = storageService.getPlan(taskId.idFromParameter()) ?: Plan()
            }
        }
    }

    fun onTitleChange(newValue: String) {
        plan.value = plan.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        plan.value = plan.value.copy(description = newValue)
    }

    fun onUrlChange(newValue: String) {
        plan.value = plan.value.copy(url = newValue)
    }

    fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        val newDueDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
        plan.value = plan.value.copy(dueDate = newDueDate)
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        plan.value = plan.value.copy(dueTime = newDueTime)
    }

    fun onFlagToggle(newValue: String) {
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        plan.value = plan.value.copy(flag = newFlagOption)
    }

    fun onPriorityChange(newValue: String) {
        plan.value = plan.value.copy(priority = newValue)
    }

    fun onDoneClick(popUpScreen: () -> Unit) {
        launchCatching {
            val editedTask = plan.value
            if (editedTask.id.isBlank()) {
                storageService.save(editedTask)
            } else {
                storageService.update(editedTask)
            }
            popUpScreen()
        }
    }

    private fun Int.toClockPattern(): String {
        return if (this < 10) "0$this" else "$this"
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
    }
}