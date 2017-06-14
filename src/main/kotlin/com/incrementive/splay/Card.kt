package com.incrementive.splay

data class Card(val suit: String, val rank: String) {
    override fun toString() = "$suit$rank"
}