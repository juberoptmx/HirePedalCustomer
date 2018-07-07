package com.titan.fasttrack.myapps

import android.graphics.drawable.Drawable

class CartItem {
    var title: String? = null
    var image: Drawable? = null
    var packageName: String? = null
    var isSelected: Boolean? = null



    constructor(name: String, image: Drawable, packageName:String, selection:Boolean) {
        this.title = name
        this.image = image
        this.packageName = packageName
        this.isSelected = selection
    }
}