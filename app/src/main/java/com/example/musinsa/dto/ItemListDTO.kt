package com.example.musinsa.dto


import com.google.gson.annotations.SerializedName

data class ItemListDTO(
    @SerializedName("data")
    val data: List<ItemDTO?>?,
) {
    data class ItemDTO(
        @SerializedName("header")
        val header: Header?,
        @SerializedName("contents")
        val contents: Contents?,
        @SerializedName("footer")
        val footer: Footer?,
    ) {
        data class Header(
            @SerializedName("iconURL")
            val iconURL: String?,
            @SerializedName("linkURL")
            val linkURL: String?,
            @SerializedName("title")
            val title: String?,
        )

        data class Contents(
            @SerializedName("banners")
            val banners: List<Banner?>?,
            @SerializedName("goods")
            val goods: List<Good?>?,
            @SerializedName("styles")
            val styles: List<Style?>?,
            @SerializedName("type")
            val type: String?,
        ) {
            data class Banner(
                @SerializedName("description")
                val description: String?,
                @SerializedName("keyword")
                val keyword: String?,
                @SerializedName("linkURL")
                val linkURL: String?,
                @SerializedName("thumbnailURL")
                val thumbnailURL: String?,
                @SerializedName("title")
                val title: String?,
            )

            data class Good(
                @SerializedName("brandName")
                val brandName: String?,
                @SerializedName("hasCoupon")
                val hasCoupon: Boolean?,
                @SerializedName("linkURL")
                val linkURL: String?,
                @SerializedName("price")
                val price: Int?,
                @SerializedName("saleRate")
                val saleRate: Int?,
                @SerializedName("thumbnailURL")
                val thumbnailURL: String?,
            )

            data class Style(
                @SerializedName("linkURL")
                val linkURL: String?,
                @SerializedName("thumbnailURL")
                val thumbnailURL: String?,
            )
        }

        data class Footer(
            @SerializedName("iconURL")
            val iconURL: String?,
            @SerializedName("title")
            val title: String?,
            @SerializedName("type")
            val type: String?,
        )
    }
}