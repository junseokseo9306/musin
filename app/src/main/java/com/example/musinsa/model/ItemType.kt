package com.example.musinsa.model

import com.example.musinsa.dto.ItemListDTO.ItemDTO

data class Item(
    val header: ItemType.Header,
    val contents: ItemType.Contents,
    val footer: ItemType.Footer,
    val type: String,
) {
    sealed class ItemType {

        data class Header(
            val title: String,
            val iconURL: String,
            val linkURL: String,
        ) : ItemType()

        data class Contents(
            val banners: List<Banner>,
            val goods: List<Goods>,
            val styles: List<Style>,
        ) {
            data class Banner(
                val linkURL: String,
                val thumbnailURL: String,
                val title: String,
                val description: String,
                val keyword: String,
            ) : ItemType()

            data class Goods(
                val layoutType: String,
                val linkURL: String,
                val thumbnailURL: String,
                val brandName: String,
                val price: Int,
                val saleRate: Int,
                val hasCoupon: Boolean,
            ) : ItemType()

            data class Style(
                val linkURL: String,
                val thumbnailURL: String,
            ) : ItemType()
        }

        data class Footer(
            val type: String,
            val title: String,
            val iconURL: String,
            var contentType: String = INITIAL_VALUE,
        ) : ItemType()

        companion object {
            const val TYPE_BANNER = "BANNER"
            const val TYPE_GOODS_SCROLL = "SCROLL"
            const val TYPE_GOODS_GRID = "GRID"
            const val TYPE_STYLE = "STYLE"
            const val REFRESH = "REFRESH"
            const val INITIAL_VALUE = ""
            val INITIAL_HEADER = Header(
                title = INITIAL_VALUE,
                iconURL = INITIAL_VALUE,
                linkURL = INITIAL_VALUE
            )
            val INITIAL_FOOTER = Footer(
                type = INITIAL_VALUE,
                title = INITIAL_VALUE,
                iconURL = INITIAL_VALUE
            )

            fun headerOf(item: ItemDTO.Header?) = Header(
                title = item?.title.orEmpty(),
                iconURL = item?.iconURL.orEmpty(),
                linkURL = item?.linkURL.orEmpty(),
            )

            fun bannerOf(item: ItemDTO.Contents.Banner?) = Contents.Banner(
                linkURL = requireNotNull(item?.linkURL),
                thumbnailURL = requireNotNull(item?.thumbnailURL),
                title = item?.title.orEmpty(),
                description = item?.description.orEmpty(),
                keyword = item?.keyword.orEmpty()
            )

            fun goodsOf(type: String, item: ItemDTO.Contents.Good?) = Contents.Goods(
                layoutType = type,
                linkURL = requireNotNull(item?.linkURL),
                thumbnailURL = requireNotNull(item?.thumbnailURL),
                brandName = requireNotNull(item?.brandName),
                price = requireNotNull(item?.price),
                saleRate = requireNotNull(item?.saleRate),
                hasCoupon = requireNotNull(item?.hasCoupon)
            )

            fun styleOf(item: ItemDTO.Contents.Style?) = Contents.Style(
                linkURL = requireNotNull(item?.linkURL),
                thumbnailURL = requireNotNull(item?.thumbnailURL)
            )

            fun footerOf(item: ItemDTO.Footer?) = Footer(
                type = item?.type.orEmpty(),
                title = item?.title.orEmpty(),
                iconURL = item?.iconURL.orEmpty()
            )
        }
    }
}