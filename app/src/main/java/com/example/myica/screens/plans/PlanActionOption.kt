package com.example.myica.screens.plans

enum class PlanActionOption(val title: String) {
  EditPlan("Edit plan"),
  ToggleFlag("Toggle flag"),
  DeletePlan("Delete plan");

  companion object {
    fun getByTitle(title: String): PlanActionOption {
      values().forEach { action -> if (title == action.title) return action }
      return EditPlan
    }

    fun getOptions(hasEditOption: Boolean): List<String> {
      val options = mutableListOf<String>()
      values().forEach { taskAction ->
        if (hasEditOption || taskAction != EditPlan) {
          options.add(taskAction.title)
        }
      }
      return options
    }
  }
}
