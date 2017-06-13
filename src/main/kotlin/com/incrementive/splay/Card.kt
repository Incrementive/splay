package com.incrementive.splay

data class Card(val suit: String, val value: String) {
    override fun toString() = "$suit$value"
}