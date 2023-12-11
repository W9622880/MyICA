package com.example.myica.common.ext

import com.example.myica.model.Plan

fun Plan?.hasDueDate(): Boolean {
    return this?.dueDate.orEmpty().isNotBlank()
}

fun Plan?.hasDueTime(): Boolean {
    return this?.dueTime.orEmpty().isNotBlank()
}