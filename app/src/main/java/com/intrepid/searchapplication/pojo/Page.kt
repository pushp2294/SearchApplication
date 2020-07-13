package com.intrepid.searchapplication.pojo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Page : RealmObject() {

    @PrimaryKey
    var pageid = 0

    var ns = 0

    var title: String? = null

    var index = 0

    var thumbnail: Thumbnail? = null

    var terms: Terms? = null

    var fullurl: String? = null

}