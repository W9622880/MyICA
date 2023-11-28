package com.example.myica.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}