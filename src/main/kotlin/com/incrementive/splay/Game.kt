package com.incrementive.splay

class Game(val config : Config) {
    private val piles: Map<PileDefinition, Pile>

    init {
        val map = mutableMapOf(config.deckReceivingPileDefinition to Pile(config.deck))
        config.otherPileDefinitions.associateByTo(map, {it}, {Pile()})
        piles = map.toMap()
    }

    fun run() = "Welcome to ${config.nameOfGame}, there are ${config.deck.size} cards in the deck."

    fun pileFor(pileDefinition: PileDefinition): Pile {
        return piles[pileDefinition] ?: throw RuntimeException()
    }

    fun moveTopCard(from: PileDefinition, to: PileDefinition) {
        pileFor(to).place(pileFor(from).draw())
    }
}