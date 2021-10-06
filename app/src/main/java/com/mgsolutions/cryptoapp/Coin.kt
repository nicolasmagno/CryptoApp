package com.mgsolutions.cryptoapp

import java.io.Serializable

class Coin : Serializable{

    var id:String = ""
        get() {return field}
        set(value) {field = value}

    var name:String = ""
        get() {return field}
        set(value) {field = value}

    var price:Double = 0.0
        get() {return field}
        set(value) {field = value}

    var code:String = ""
        get() {return field}
        set(value) {field = value}

    var img:String = ""
        get() {return field}
        set(value) {field = value}

}