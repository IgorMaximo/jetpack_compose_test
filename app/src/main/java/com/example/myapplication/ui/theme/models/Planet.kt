package com.example.myapplication.ui.theme.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Planet(
    val name: String,
    val description: String,
    val image: Int
) : Parcelable

