package com.nyang.dayFlower.domain.model.flowerDetail

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class FlowerDetail(
    @PropertyElement
    val dataNo: Int? = null,
    @PropertyElement
    val fContent: String? = null,
    @PropertyElement
    val fEngNm: String? = null,
    @PropertyElement
    val fGrow: String? = null,
    @PropertyElement
    val fMonth: Int? = null,
    @PropertyElement
    val fDay: Int? = null,
    @PropertyElement
    val fSctNm: String? = null,
    @PropertyElement
    val fType: String? = null,
    @PropertyElement
    val fUse: String? = null,
    @PropertyElement
    val fileName1: String? = null,
    @PropertyElement
    val fileName2: String? = null,
    @PropertyElement
    val fileName3: String? = null,
    @PropertyElement
    val flowLang: String? = null,
    @PropertyElement
    val flowNm: String? = null,
    @PropertyElement
    val imgUrl1: String? = null,
    @PropertyElement
    val imgUrl2: String? = null,
    @PropertyElement
    val imgUrl3: String? = null,
    @PropertyElement
    val publishOrg: String? = null
)