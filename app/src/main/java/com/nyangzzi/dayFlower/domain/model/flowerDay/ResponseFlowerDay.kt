package com.nyangzzi.dayFlower.domain.model.flowerDay

import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "document")
data class ResponseFlowerDay(
    @Element(name = "root")
    val root: RootFlowerDay? = null
)

@Xml(name = "root")
data class RootFlowerDay(
    @PropertyElement(name = "repcategory")
    val apiName: String? = null,
    @Element(name = "result")
    val result: FlowerDetail? = null,
    @PropertyElement(name = "resultCode")
    val resultCode: Int? = null,
    @PropertyElement(name = "resultMsg")
    val resultMsg: String? = null
)