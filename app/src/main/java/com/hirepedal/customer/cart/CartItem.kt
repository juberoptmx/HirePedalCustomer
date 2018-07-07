package com.hirepedal.customer.cart

import android.graphics.drawable.Drawable
import java.util.function.DoubleUnaryOperator

class CartItem {

    var partnerName: String? = null
    var partnerId: Int? = null
    var partnerInfo: String? = null
    var pImage: Drawable? = null

    var cycleName: String? = null
    var cycleId: Int? = null
    var cycleInfo: String? = null
    var cImage: Drawable? = null

    var pLat:Double? = null
    var pLong:Double? = null


    constructor(partnerName: String,
                partnerId: Int,
                partnerInfo: String,
                pImage: Drawable,
                cycleName: String,
                cycleId: Int,
                cycleInfo: String,
                cImage: Drawable,
                pLat:Double,
                pLong:Double) {
        this.partnerName = partnerName
        this.partnerId = partnerId
        this.partnerInfo = partnerInfo
        this.pImage = pImage
        this.cycleName = cycleName
        this.cycleId = cycleId
        this.cycleInfo = cycleInfo
        this.cImage = cImage
        this.pLat = pLat
        this.pLong = pLong

    }
}