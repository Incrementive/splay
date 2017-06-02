package com.incrementive.splay

data class Config(val nameOfGame: String, val deck: Set<Card>, val deckReceivingPileDefinition: PileDefinition, val otherPileDefinitions: Set<PileDefinition>) {
    init {
        require(deck.isNotEmpty())
        require(otherPileDefinitions.isNotEmpty())
    }
}