package com.terabyte.ktornotes.models.bdui.attributes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class SizeSpec {
    abstract val type: String

    @Serializable
    @SerialName("fixed")
    data class Fixed(val dp: Int) : SizeSpec() {
        override val type = "fixed"
    }

    @Serializable
    @SerialName("wrapContent")
    object WrapContent : SizeSpec() {
        override val type = "wrapContent"
    }

    @Serializable
    @SerialName("matchParent")
    object MatchParent : SizeSpec() {
        override val type = "matchParent"
    }

    @Serializable
    @SerialName("weight")
    data class Weight(
        val weight: Float
    ) : SizeSpec() {
        override val type = "weight"
    }

}


@Serializable
@SerialName("padding")
data class Padding(
    val start: Int = 0,
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0
)


@Serializable
@SerialName("margin")
data class Margin(
    val start: Int = 0,
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0
)


@Serializable
sealed class VerticalAlignment {
    abstract val type: String

    @Serializable
    @SerialName("center")
    object Center : VerticalAlignment() {
        override val type = "center"
    }

    @Serializable
    @SerialName("top")
    object Top : VerticalAlignment() {
        override val type = "top"
    }

    @Serializable
    @SerialName("bottom")
    object Bottom : VerticalAlignment() {
        override val type = "bottom"
    }

}


@Serializable
sealed class HorizontalAlignment {
    abstract val type: String

    @Serializable
    @SerialName("center")
    object Center : HorizontalAlignment() {
        override val type = "center"
    }

    @Serializable
    @SerialName("end")
    object End : HorizontalAlignment() {
        override val type = "end"
    }

    @Serializable
    @SerialName("start")
    object Start : HorizontalAlignment() {
        override val type = "start"
    }

}

@Serializable
sealed class VerticalArrangement {
    abstract val type: String

    @Serializable
    @SerialName("center")
    object Center : VerticalArrangement() {
        override val type = "center"
    }


    @Serializable
    @SerialName("top")
    object Top : VerticalArrangement() {
        override val type = "top"
    }


    @Serializable
    @SerialName("bottom")
    object Bottom : VerticalArrangement() {
        override val type = "bottom"
    }


    @Serializable
    @SerialName("spaceBetween")
    object SpaceBetween : VerticalArrangement() {
        override val type = "spaceBetween"
    }


    @Serializable
    @SerialName("spaceEvenly")
    object SpaceEvenly : VerticalArrangement() {
        override val type = "spaceEvenly"
    }


    @Serializable
    @SerialName("spaceAround")
    object SpaceAround : VerticalArrangement() {
        override val type = "spaceAround"
    }


}


@Serializable
sealed class HorizontalArrangement {
    abstract val type: String

    @Serializable
    @SerialName("center")
    object Center : HorizontalArrangement() {
        override val type = "center"
    }

    @Serializable
    @SerialName("start")
    object Start : HorizontalArrangement() {
        override val type = "start"
    }

    @Serializable
    @SerialName("end")
    object End : HorizontalArrangement() {
        override val type = "end"
    }

    @Serializable
    @SerialName("spaceBetween")
    object SpaceBetween : HorizontalArrangement() {
        override val type = "spaceBetween"
    }

    @Serializable
    @SerialName("spaceEvenly")
    object SpaceEvenly : HorizontalArrangement() {
        override val type = "spaceEvenly"
    }

    @Serializable
    @SerialName("spaceAround")
    object SpaceAround : HorizontalArrangement() {
        override val type = "spaceAround"
    }

}
