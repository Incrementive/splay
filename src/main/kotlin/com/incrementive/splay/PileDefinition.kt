package com.incrementive.splay

data class PileDefinition(val name: String, val visibleTo: Set<Player>, val splay: Splay = Splay.none)