package com.terabyte.ktornotes.routing

import com.terabyte.ktornotes.models.bdui.ColumnComponent
import com.terabyte.ktornotes.models.bdui.attributes.HorizontalAlignment
import com.terabyte.ktornotes.models.bdui.attributes.SizeSpec
import com.terabyte.ktornotes.models.bdui.attributes.VerticalArrangement
import io.ktor.server.auth.authenticate
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

                    )
                )
            }

        }
    }
}