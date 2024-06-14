package com.example.myapplication.data.pref

import android.content.Context

fun saveOnboardingStatus(context: Context, completed: Boolean) {
    val sharedPreferences = context.getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("onboarding_completed", completed)
    editor.apply()
}

fun isOnboardingCompleted(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("onboarding_completed", false)
}