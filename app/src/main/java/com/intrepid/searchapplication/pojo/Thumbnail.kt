package com.intrepid.searchapplication.pojo

import io.realm.RealmObject

open class Thumbnail : RealmObject() {
    var source: String? = null

    var width = 0

    var height = 0

}