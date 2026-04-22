package com.terabyte.ktornotes.models.bdui

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class Action {
    abstract val type: String
}


@Serializable
@SerialName("ToastAction")
data class ToastAction(
    val message: String
) : Action() {
    override val type = "ToastAction"
}



@Serializable
@SerialName("CreateNoteAction")
object CreateNoteAction: Action() {
    override val type = "CreateNoteAction"
}
