package com.incrementive.splay

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GameTest {

    @Test(expected = IllegalArgumentException::class)
    fun `constructing game definition with no cards throws exception`() {
        GameDefinition(
                nameOfGame = "name of game",
                deck = emptySet(),
                deckReceivingPileDefinition = PileDefinition("pile", GameVisibility.none),
                otherPileDefinitions = setOf(PileDefinition("pile", GameVisibility.none)), perPlayerPileDefinitions = emptySet())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `constructing game definition with no piles throws exception`() {
        GameDefinition("name of game", setOf(Card("suit", "rank")), PileDefinition("pile", GameVisibility.none), emptySet(), emptySet())
    }

    @Test
    fun `game With Two Piles Starts With Deck In Specified Pile`() {
        val deck = setOf(Card("suit", "rank"))
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
    fun `move card from draw to discard`() {
        val deck = setOf(Card("suit", "rank"))
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
    fun `render game`() {
        val deck = setOf(Card("suit", "rank"))
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
                .isEqualTo("""View for Player One
Player One hand: Card count: 0
Player Two hand: Card count: 0
draw: Card count: 1, cards not visible

View for Player Two
Player One hand: Card count: 0
Player Two hand: Card count: 0
draw: Card count: 1, cards not visible""")
    }
}
