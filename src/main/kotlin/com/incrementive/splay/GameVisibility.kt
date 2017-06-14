package com.incrementive.splay

enum class GameVisibility : PileVisibility {
    all {
        override fun invoke(allPlayers: Set<Player>) = allPlayers
    },
    none {
        override fun invoke(allPlayers: Set<Player>) = emptySet<Player>()
    };

    abstract operator fun invoke(allPlayers: Set<Player>): Set<Player>
}