package com.incrementive.splay

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GameTest {

    @Test(expected = IllegalArgumentException::class)
    fun constructingConfigWithNoCardsThrowsException() {
        Config(
                nameOfGame = "name of game",
                deck = emptySet(),
                deckReceivingPileDefinition = PileDefinition("pile", Direction.faceUp),
                otherPileDefinitions = setOf(PileDefinition("pile", Direction.faceUp)))
    }

    @Test(expected = IllegalArgumentException::class)
    fun constructingConfigWithNoPilesThrowsException() {
        Config("name of game", setOf(Card("suit", "value")), PileDefinition("pile", Direction.faceUp), emptySet())
    }

    @Test
    fun engineDisplays20CardsForCombined5ValuesOf4Suits() {
        val suits = setOf("Hearts", "Spades", "Diamonds", "Clubs")
        val values = setOf("2", "Jack", "Queen", "King", "Ace")
        val deck = suits.flatMap { suit -> values.map { value -> Card(suit, value) } }.toSet()
        val config = Config(
                nameOfGame = "name of game",
                deck = deck,
                deckReceivingPileDefinition = PileDefinition("pile", Direction.faceUp),
                otherPileDefinitions = setOf(PileDefinition("pile", Direction.faceUp)))
        val output = Game(config).run()
        assertThat(output)
                .isEqualTo("Welcome to name of game, there are 20 cards in the deck.")
    }

    @Test
    fun gameWithTwoPilesStartsWithDeckInSpecifiedPile() {
        val deck = setOf(Card("suit", "value"))
        val drawPileDefinition = PileDefinition("draw", Direction.faceDown)
        val discardPileDefinition = PileDefinition("discard", Direction.faceUp)
        val config = Config(
                nameOfGame = "name of game",
                deck = deck,
                deckReceivingPileDefinition = drawPileDefinition,
                otherPileDefinitions = setOf(discardPileDefinition))
        val game = Game(config)

        assertThat(game.pileFor(drawPileDefinition).size)
                .isEqualTo(1)
        assertThat(game.pileFor(discardPileDefinition).size)
                .isEqualTo(0)
    }

    @Test
    fun moveCardFromDrawToDiscard() {
        val deck = setOf(Card("suit", "value"))
        val drawPileDefinition = PileDefinition("draw", Direction.faceDown)
        val discardPileDefinition = PileDefinition("discard", Direction.faceUp)
        val config = Config(
                nameOfGame = "name of game",
                deck = deck,
                deckReceivingPileDefinition = drawPileDefinition,
                otherPileDefinitions = setOf(discardPileDefinition))
        val game = Game(config)
        game.moveTopCard(
                from = drawPileDefinition,
                to = discardPileDefinition)
        assertThat(game.pileFor(drawPileDefinition).size)
                .isEqualTo(0)
        assertThat(game.pileFor(discardPileDefinition).size)
                .isEqualTo(1)
    }
}
