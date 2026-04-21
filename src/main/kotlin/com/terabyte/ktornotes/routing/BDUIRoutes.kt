package com.terabyte.ktornotes.routing

import com.terabyte.ktornotes.models.bdui.ButtonComponent
import com.terabyte.ktornotes.models.bdui.ColumnComponent
import com.terabyte.ktornotes.models.bdui.CreateNoteAction
import com.terabyte.ktornotes.models.bdui.RowComponent
import com.terabyte.ktornotes.models.bdui.TextComponent
import com.terabyte.ktornotes.models.bdui.TextFieldComponent
import com.terabyte.ktornotes.models.bdui.attributes.HorizontalAlignment
import com.terabyte.ktornotes.models.bdui.attributes.HorizontalArrangement
import com.terabyte.ktornotes.models.bdui.attributes.Margin
import com.terabyte.ktornotes.models.bdui.attributes.Padding
import com.terabyte.ktornotes.models.bdui.attributes.SizeSpec
import com.terabyte.ktornotes.models.bdui.attributes.TextStyle
import com.terabyte.ktornotes.models.bdui.attributes.VerticalAlignment
import com.terabyte.ktornotes.models.bdui.attributes.VerticalArrangement
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.BDUIRoutes() {
    authenticate {
        route("/api/bd_ui") {

            get("/create_note") {
                val screen = ColumnComponent(
                    width = SizeSpec.MatchParent,
                    height = SizeSpec.MatchParent,
                    verticalArrangement = VerticalArrangement.Center,
                    horizontalAlignment = HorizontalAlignment.Center,
                    children = listOf(
                        RowComponent(
                            width = SizeSpec.MatchParent,
                            height = SizeSpec.WrapContent,
                            padding = Padding(
                                top = 16,
                                bottom = 16,
                                start = 16,
                                end = 16
                            ),
                            verticalAlignment = VerticalAlignment.Center,
                            horizontalArrangement = HorizontalArrangement.Center,
                            backgroundColorHex = "#FF4CAF50",
                            children = listOf(
                                TextComponent(
                                    width = SizeSpec.WrapContent,
                                    height = SizeSpec.WrapContent,
                                    text = "Create note",
                                    style = TextStyle(
                                        fontSize = 22,
                                        colorHex = "#FFFFFFFF"
                                    )
                                )
                            )
                        ),
                        ColumnComponent(
                            width = SizeSpec.MatchParent,
                            height = SizeSpec.Weight(1.0f),
                            padding = Padding(
                                top = 16,
                                bottom = 16,
                                start = 16,
                                end = 16
                            ),
                            verticalArrangement = VerticalArrangement.Center,
                            horizontalAlignment = HorizontalAlignment.Center,
                            children = listOf(
                                TextFieldComponent(
                                    width = SizeSpec.MatchParent,
                                    height = SizeSpec.WrapContent,
                                    singleLine = true,
                                    hint = "Note title",
                                    style = TextStyle(
                                        fontSize = 16
                                    )
                                ),
                                TextFieldComponent(
                                    width = SizeSpec.MatchParent,
                                    height = SizeSpec.WrapContent,
                                    margin = Margin(
                                        top = 32
                                    ),
                                    singleLine = false,
                                    hint = "Note text",
                                    style = TextStyle(
                                        fontSize = 16
                                    )
                                ),
                                ButtonComponent(
                                    width = SizeSpec.WrapContent,
                                    height = SizeSpec.WrapContent,
                                    margin = Margin(
                                        top = 32
                                    ),
                                    text = "Create",
                                    textColorHex = "#FFFFFFFF",
                                    backgroundColorHex = "#FF4CAF50",
                                    action = CreateNoteAction
                                )
                            )
                        )
                    )
                )
                call.respond(screen)
            }

        }
    }
}
