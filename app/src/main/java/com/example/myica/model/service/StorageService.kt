
package com.example.myica.model.service

import com.example.myica.model.Task
import kotlinx.coroutines.flow.Flow

interface StorageService {
  val tasks: Flow<List<Task>>

  suspend fun getTask(taskId: String): Task?

  suspend fun save(task: Task): String
  suspend fun update(task: Task)
  suspend fun delete(taskId: String)
  suspend fun deleteAllForUser(userId: String)
}
