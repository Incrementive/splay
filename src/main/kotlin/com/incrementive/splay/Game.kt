package com.incrementive.splay

class Game(private val config: Config) {
    private val piles = with(config) {
        otherPileDefinitions.associateBy({ it }, { Pile(it) }) + (deckReceivingPileDefinition to Pile(deckReceivingPileDefinition, deck))
    }

    private val pilesByName = piles.mapKeys { (pileDefinition, _) -> pileDefinition.name }

    fun run() = "Welcome to ${config.nameOfGame}, there are ${config.deck.size} cards in the deck."

    fun pileFor(pileDefinition: PileDefinition) = piles[pileDefinition]!!

    fun moveTopCard(from: PileDefinition, to: PileDefinition) = pileFor(to).place(pileFor(from).draw())

    fun render(player: Player) =
            piles.values.joinToString(
                    prefix = "View for ${player.name}" + System.lineSeparator(),
                    transform = { it.render(player) },
                    separator = System.lineSeparator())

    fun command(fromPileName: String, index: Int, toPileName: String) {
        val fromPile = pilesByName[fromPileName]!!
        val toPile = pilesByName[toPileName]!!
        toPile.place(fromPile.take(index))
    }
}