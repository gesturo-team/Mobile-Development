package com.example.myapplication.data.pref

import android.content.Context

fun saveOnboardingStatus(context: Context, yes: Boolean) {
    val editOnBoard = context.getSharedPreferences("share_onboard", Context.MODE_PRIVATE).edit()
    editOnBoard.putBoolean("finished", yes)
    editOnBoard.apply()
}

fun isOnboardingCompleted(context: Context): Boolean {
    return context.getSharedPreferences("share_onboard", Context.MODE_PRIVATE).getBoolean("finished", false)
}