

package com.example.myica.screens.plans

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myica.R.drawable as AppIcon
import com.example.myica.common.composable.DropdownContextMenu
import com.example.myica.common.ext.contextMenu
import com.example.myica.common.ext.hasDueDate
import com.example.myica.common.ext.hasDueTime
import com.example.myica.model.Plan
import com.example.myica.theme.DarkOrange
import java.lang.StringBuilder

@Composable
@ExperimentalMaterialApi
fun PlanItem(
  plan: Plan,
  options: List<String>,
  onCheckChange: () -> Unit,
  onActionClick: (String) -> Unit
) {
  Card(
    backgroundColor = MaterialTheme.colors.background,
    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth(),
    ) {
      Checkbox(
        checked = plan.completed,
        onCheckedChange = { onCheckChange() },
        modifier = Modifier.padding(8.dp, 0.dp)
      )

      Column(modifier = Modifier.weight(1f)) {
        Text(text = plan.title, style = MaterialTheme.typography.subtitle2)
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
          Text(text = getDueDateAndTime(plan), fontSize = 12.sp)
        }
      }

      if (plan.flag) {
        Icon(
          painter = painterResource(AppIcon.ic_flag),
          tint = DarkOrange,
          contentDescription = "Flag"
        )
      }

      DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
    }
  }
}

private fun getDueDateAndTime(plan: Plan): String {
  val stringBuilder = StringBuilder("")

  if (plan.hasDueDate()) {
    stringBuilder.append(plan.dueDate)
    stringBuilder.append(" ")
  }

  if (plan.hasDueTime()) {
    stringBuilder.append("at ")
    stringBuilder.append(plan.dueTime)
  }

  return stringBuilder.toString()
}
