package com.example.composedemo.model

import com.algolia.instantsearch.core.highlighting.HighlightedString
import com.algolia.instantsearch.highlighting.Highlightable
import com.algolia.search.model.Attribute
import com.algolia.search.model.ObjectID
import com.algolia.search.model.indexing.Indexable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JsonObject

@Serializable
data class AlgoliaProduct(
    val boxName: String? = "",
    val superCatName: String? = "",
    val categoryName: String? = "",
    val categoryFriendlyName: String? = "",
    val sellPrice: Double = 0.0,
    val cashBuyPrice: Double = 0.0,
    val exchangePrice: Double = 0.0,
    val superCatFriendlyName: String? = "",
    val imageNames: List<String>?= null,
    val isImageTypeInternal: Int?= null,
    val boxId: String? = "",
    val ecomQuantity: Int? = 0,
    val collectionQuantity:String? = "0",
    val boxBuyAllowed:Int = 0,
    val boxSaleAllowed:Int = 0,
    val boxWebBuyAllowed:Int = 0,
    val boxWebSaleAllowed:Int = 0,
    val rating: Float = 0.0f,
    val cashPriceCalculated:Double = 0.0,
    @SerialName("imageUrls")
    val imageUrls: ImageUrls?= null,
    @Transient
    var mode:Int  = 0,
    @Transient
    var deliveryMethodId:Int = 0,
    val exchangePriceCalculated:Double = 0.0,
    var discontinued:Int = 0,
    override val objectID: ObjectID,
    override val _highlightResult: JsonObject? = null
): Indexable, Highlightable{
    val highlightedName: HighlightedString?
        get() = getHighlight(Attribute("boxName"))
}