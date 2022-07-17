package com.example.musinsa.dto


import com.example.musinsa.model.Item
import com.example.musinsa.model.Item.ItemType
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

fun ItemListDTO.toItemList(): List<Item> {
    val itemList = mutableListOf<Item>()

    this.data?.forEach { itemDTO ->
        val header =
            itemDTO?.let { ItemType.headerOf(itemDTO.header) } ?: ItemType.INITIAL_HEADER
        val footer =
            itemDTO?.let { ItemType.footerOf(itemDTO.footer) } ?: ItemType.INITIAL_FOOTER
        val banners = mutableListOf<ItemType.Contents.Banner>()
        val goods = mutableListOf<ItemType.Contents.Goods>()
        val styles = mutableListOf<ItemType.Contents.Style>()
        var type = ""
        when (itemDTO?.contents?.type) {
            ItemType.TYPE_BANNER -> {
                type = ItemType.TYPE_BANNER
                itemDTO.contents.banners?.forEach { banner ->
                    if (banner != null) {
                        banners.add(ItemType.bannerOf(banner))
                    }
                }
            }
            ItemType.TYPE_GOODS_GRID -> {
                type = ItemType.TYPE_GOODS_GRID
                itemDTO.contents.goods?.forEach { gridGoods ->
                    if (gridGoods != null) {
                        goods.add(ItemType.goodsOf(
                            ItemType.TYPE_GOODS_GRID,
                            gridGoods)
                        )
                    }
                }
            }
            ItemType.TYPE_GOODS_SCROLL -> {
                type = ItemType.TYPE_GOODS_SCROLL
                itemDTO.contents.goods?.forEach { scrollGoods ->
                    if (scrollGoods != null) {
                        goods.add(ItemType.goodsOf(
                            ItemType.TYPE_GOODS_SCROLL,
                            scrollGoods)
                        )
                    }
                }
            }
            ItemType.TYPE_STYLE -> {
                type = ItemType.TYPE_STYLE
                itemDTO.contents.styles?.forEach { style ->
                    if (style != null) {
                        styles.add(ItemType.styleOf(style))
                    }
                }
            }
        }

        footer.contentType = type
        val item = Item(
            header = header,
            contents = ItemType.Contents(
                banners = banners,
                goods = goods,
                styles = styles,
            ),
            footer = footer,
            type = type
        )
        itemList.add(item)
    }
    return itemList
}