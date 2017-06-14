package com.incrementive.splay

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GameTest {

    @Test(expected = IllegalArgumentException::class)
    fun constructinggameDefinitionWithNoCardsThrowsException() {
        GameDefinition(
                nameOfGame = "name of game",
                deck = emptySet(),
                deckReceivingPileDefinition = PileDefinition("pile", GameVisibility.none),
                otherPileDefinitions = setOf(PileDefinition("pile", GameVisibility.none)), perPlayerPileDefinitions = emptySet())
    }

    @Test(expected = IllegalArgumentException::class)
    fun constructinggameDefinitionWithNoPilesThrowsException() {
        GameDefinition("name of game", setOf(Card("suit", "value")), PileDefinition("pile", GameVisibility.none), emptySet(), emptySet())
    }

    @Test
    fun gameWithTwoPilesStartsWithDeckInSpecifiedPile() {
        val deck = setOf(Card("suit", "value"))
        val drawPileDefinition = PileDefinition("draw", GameVisibility.none)
        val discardPileDefinition = PileDefinition("discard", GameVisibility.none)
        val gameDefinition = GameDefinition(
                nameOfGame = "name of game",
                deck = deck,
                deckReceivingPileDefinition = drawPileDefinition,
                otherPileDefinitions = setOf(discardPileDefinition), perPlayerPileDefinitions = emptySet())
        val game = Game(gameDefinition, setOf(Player("P1")))

        assertThat(game.pileFor(drawPileDefinition.name).cardCount)
                .isEqualTo(1)
        assertThat(game.pileFor(discardPileDefinition.name).cardCount)
                .isEqualTo(0)
    }

    @Test
    fun moveCardFromDrawToDiscard() {
        val deck = setOf(Card("suit", "value"))
        val drawPileDefinition = PileDefinition("draw", GameVisibility.none)
        val discardPileDefinition = PileDefinition("discard", GameVisibility.none)
        val gameDefinition = GameDefinition(
                nameOfGame = "name of game",
                deck = deck,
                deckReceivingPileDefinition = drawPileDefinition,
                otherPileDefinitions = setOf(discardPileDefinition), perPlayerPileDefinitions = emptySet())
        val game = Game(gameDefinition, setOf(Player("P1")))
        game.command(
                fromPileName = "draw",
                index = 0,
                toPileName = "discard")
        assertThat(game.pileFor("draw").cardCount)
                .isEqualTo(0)
        assertThat(game.pileFor("discard").cardCount)
                .isEqualTo(1)
    }

    @Test
    fun renderGame() {
        val deck = setOf(Card("suit", "value"))
        val drawPileDefinition = PileDefinition("draw", GameVisibility.none)
        val gameDefinition = GameDefinition(
                nameOfGame = "name of game",
                deck = deck,
                deckReceivingPileDefinition = drawPileDefinition,
                otherPileDefinitions = emptySet(),
                perPlayerPileDefinitions = setOf(PileDefinition(
                        name = "hand",
                        splay = Splay.right,
                        visibleTo = PlayerVisibility.owner)))
        val game = Game(gameDefinition, setOf(Player("Player One"), Player("Player Two")))
        val rendered = game.render();
        assertThat(rendered)
                // Ignoring whitespace as a workaround for KT-13048
                .isEqualToIgnoringWhitespace("""View for Player One
Player One hand: Card count: 0
Player Two hand: Card count: 0
draw: Card count: 1, cards not visible

View for Player Two
Player One hand: Card count: 0
Player Two hand: Card count: 0
draw: Card count: 1, cards not visible""")
    }
}
