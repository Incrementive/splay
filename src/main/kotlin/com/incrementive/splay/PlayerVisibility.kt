package com.incrementive.splay

enum class PlayerVisibility : PileVisibility {
    all {
        override fun invoke(allPlayers: Set<Player>, owner: Player) = allPlayers
    },
    owner {
        override fun invoke(allPlayers: Set<Player>, owner: Player) = setOf(owner)
    },
    none {
        override fun invoke(allPlayers: Set<Player>, owner: Player) = emptySet<Player>()
    };

    abstract operator fun invoke(allPlayers: Set<Player>, owner: Player): Set<Player>
}