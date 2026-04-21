package com.terabyte.ktornotes.models.bdui

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("Action")
sealed class Action {
}


@Serializable
@SerialName("ToastAction")
data class ToastAction(
    val message: String
) : Action() {
}


@Serializable
@SerialName("CreateNoteAction")
object CreateNoteAction: Action() {
}
