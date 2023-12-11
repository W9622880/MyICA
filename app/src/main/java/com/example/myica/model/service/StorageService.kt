
package com.example.myica.model.service

import com.example.myica.model.Plan
import kotlinx.coroutines.flow.Flow

interface StorageService {
  val plans: Flow<List<Plan>>

  suspend fun getPlan(planId: String): Plan?
  suspend fun save(plan: Plan): String
  suspend fun update(plan: Plan)
  suspend fun delete(planId: String)
  suspend fun deleteAllForUser(userId: String)
}
