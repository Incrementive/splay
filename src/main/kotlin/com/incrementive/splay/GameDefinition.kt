package com.incrementive.splay

data class GameDefinition(
        val nameOfGame: String,
        val deck: Set<Card>,
        val deckReceivingPileDefinition: GamePileDefinition,
        val otherPileDefinitions: Set<GamePileDefinition>,
        val perPlayerPileDefinitions: Set<PlayerPileDefinition>) {

    init {
        require(deck.isNotEmpty())
        require(otherPileDefinitions.isNotEmpty() || perPlayerPileDefinitions.isNotEmpty())
    }

    fun buildPiles(allPlayers: Set<Player>): Map<String, Pile>  {
        val pileNameToPilePairs = buildOtherPiles(allPlayers, otherPileDefinitions) +
                buildPerPlayerPiles(allPlayers, perPlayerPileDefinitions) +
                buildDeckReceivingPile()
        val duplicatedNames = pileNameToPilePairs
                .groupingBy { (name, _) -> name }
                .eachCount()
                .filterValues { it > 1 }
                .keys
        if (duplicatedNames.isNotEmpty()) {
            throw IllegalArgumentException("Duplicated pile names: $duplicatedNames")
        }
        return pileNameToPilePairs.toMap()
    }

    private fun buildDeckReceivingPile() =
            with(deckReceivingPileDefinition) {
                name to Pile(name, splay, emptySet(), deck)
            }

    companion object {
        fun buildOtherPiles(allPlayers: Set<Player>, otherPileDefinitions: Set<GamePileDefinition>) =
                otherPileDefinitions.map {
                    it.name to Pile(
                            name = it.name,
                            splay = it.splay,
                            visibleTo = it.visibleTo(allPlayers)
                    )
                }

        fun buildPerPlayerPiles(allPlayers: Set<Player>, perPlayerPileDefinitions: Set<PlayerPileDefinition>) =
                allPlayers.flatMap { player ->
                    perPlayerPileDefinitions.map { pileDefinition ->
                        val pileName = "${player.name} ${pileDefinition.name}"
                        val visibleTo = pileDefinition.visibleTo(allPlayers = allPlayers, owner = player)
                        pileName to Pile(pileName, pileDefinition.splay, visibleTo)
                    }
                }
    }
}