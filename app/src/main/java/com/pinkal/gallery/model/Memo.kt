package com.pinkal.gallery.model

import java.lang.annotation.Retention

import io.realm.RealmObject
import io.realm.annotations.Required

open class Memo : RealmObject {

    @Required
    var text: String? = null

    override fun toString(): String {
        return "Memo{" +
                "text='" + text + '\''.toString() +
                '}'.toString()
    }

    constructor() {
        this.text = "아무값도 없습니다."
    }

    constructor(text: String) {
        this.text = text
    }
}
