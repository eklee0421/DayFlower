package com.nyang.dayFlower.domain.model.flowerList

data class RequestFlowerList (
    val searchType : Int? = null,
    val searchWord : String? = null,
    val pageNo: Int? = null
)