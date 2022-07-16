package com.example.musinsa.model

import com.example.musinsa.dto.ItemListDTO
import kotlin.reflect.typeOf

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
        ) : ItemType() {
            companion object {
                fun of(item: ItemListDTO.ItemDTO.Header?) = Header(
                    title = item?.title.orEmpty(),
                    iconURL = item?.iconURL.orEmpty(),
                    linkURL = item?.linkURL.orEmpty(),
                )

                private const val INITIAL_VALUE = ""
                val INITIAL_HEADER = Header(
                    title = INITIAL_VALUE,
                    iconURL = INITIAL_VALUE,
                    linkURL = INITIAL_VALUE
                )
            }
        }

        data class Contents(
            val banners: List<Banner>,
            val goods: List<Goods>,
            val styles: List<Style>,
        ) {
            data class Banner(
                val linkUrl: String,
                val thumbnailUrl: String,
                val title: String?,
                val description: String?,
                val keyword: String?,
            ) : ItemType() {
                companion object {
                    fun of(item: ItemListDTO.ItemDTO.Contents.Banner?) = Banner(
                        linkUrl = requireNotNull(item?.linkURL),
                        thumbnailUrl = requireNotNull(item?.thumbnailURL),
                        title = item?.title.orEmpty(),
                        description = item?.description.orEmpty(),
                        keyword = item?.keyword.orEmpty()
                    )
                }
            }

            data class Goods(
                val layoutType: String,
                val linkURL: String,
                val thumbnailURL: String,
                val brandName: String,
                val price: Int,
                val saleRate: Int,
                val hasCoupon: Boolean,
            ) : ItemType() {
                companion object {
                    fun of(type: String, item: ItemListDTO.ItemDTO.Contents.Good?) = Goods(
                        layoutType = type,
                        linkURL = requireNotNull(item?.linkURL),
                        thumbnailURL = requireNotNull(item?.thumbnailURL),
                        brandName = requireNotNull(item?.brandName),
                        price = requireNotNull(item?.price),
                        saleRate = requireNotNull(item?.saleRate),
                        hasCoupon = requireNotNull(item?.hasCoupon)
                    )
                }
            }

            data class Style(
                val linkURL: String,
                val thumbnailURL: String,
            ) : ItemType() {
                companion object {
                    fun of(item: ItemListDTO.ItemDTO.Contents.Style?) = Style(
                        linkURL = requireNotNull(item?.linkURL),
                        thumbnailURL = requireNotNull(item?.thumbnailURL)
                    )
                }
            }

            companion object {
                const val TYPE_BANNER = "BANNER"
                const val TYPE_GOODS_SCROLL = "SCROLL"
                const val TYPE_GOODS_GRID = "GRID"
                const val TYPE_STYLE = "STYLE"
            }
        }

        data class Footer(
            val type: String,
            val title: String,
            val iconURL: String,
            var contentType: String = INITIAL_VALUE,
        ) : ItemType() {
            companion object {
                fun of(item: ItemListDTO.ItemDTO.Footer?) = Footer(
                    type = item?.type.orEmpty(),
                    title = item?.title.orEmpty(),
                    iconURL = item?.iconURL.orEmpty()
                )

                private const val INITIAL_VALUE = ""
                val INITIAL_HEADER = Footer(
                    type = INITIAL_VALUE,
                    title = INITIAL_VALUE,
                    iconURL = INITIAL_VALUE
                )
            }
        }
    }
}