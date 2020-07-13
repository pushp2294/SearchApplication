package com.intrepid.searchapplication.pojo

import io.realm.RealmList
import io.realm.RealmObject

open class Terms : RealmObject() {
    var description: RealmList<String>? = null
}