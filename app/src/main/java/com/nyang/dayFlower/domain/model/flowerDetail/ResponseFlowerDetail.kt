package com.nyang.dayFlower.domain.model.flowerDetail

import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "document")
data class ResponseFlowerDetail(
    @Element(name="root")
    val root: RootFlowerDetail? = null
)

@Xml(name="root")
data class RootFlowerDetail(
    @PropertyElement(name="repcategory")
    val apiName: String? = null,
    @Element(name ="result")
    val result: FlowerDetail? = null,
    @PropertyElement(name="resultCode")
    val resultCode: Int? = null,
    @PropertyElement(name="resultMsg")
    val resultMsg: String? = null
)