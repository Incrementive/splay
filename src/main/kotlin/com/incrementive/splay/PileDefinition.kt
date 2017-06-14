package com.incrementive.splay

typealias GamePileDefinition = PileDefinition<GameVisibility>
typealias PlayerPileDefinition = PileDefinition<PlayerVisibility>

data class PileDefinition<out V : PileVisibility>(val name: String, val visibleTo: V, val splay: Splay = Splay.none)