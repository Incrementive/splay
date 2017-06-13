package com.incrementive.splay

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PileTest {
    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun drawFromEmptyPile() {
        Pile(
                PileDefinition(
                        name = "pile",
                        visibleTo = emptySet()
                ),
                emptySet()
        ).draw()
    }

    @Test
    fun drawFromNonEmptyPileReturnsCard() {
        val placedCard = Card("suit", "value")
        val pile = Pile(
                PileDefinition(
                        name = "pile",
                        visibleTo = emptySet()
                ),
                setOf(placedCard))
        val drawnCard = pile.draw()
        assertThat(pile.size)
                .isZero()
        assertThat(drawnCard)
                .isEqualTo(placedCard)
    }

    @Test
    fun placingCardOnEmptyPileResultsInSizeOfOne() {
        val placedCard = Card("suit", "value")
        val pile = Pile(
                PileDefinition(
                        name = "pile",
                        visibleTo = emptySet()
                ),
                emptySet())
        pile.place(placedCard)
        assertThat(pile.size)
                .isEqualTo(1)
    }

    @Test
    fun placingTwoCardsAllowsThemToBeDrawnInLastInFirstOutOrder() {
        val firstCardPlaced = Card("suit", "first")
        val secondCardPlaced = Card("suit", "second")
        val pile = Pile(
                PileDefinition(
                        name = "pile",
                        visibleTo = emptySet()
                ),
                emptySet())
        pile.place(firstCardPlaced)
        pile.place(secondCardPlaced)
        assertThat(pile.draw())
                .isEqualTo(secondCardPlaced)
        assertThat(pile.draw())
                .isEqualTo(firstCardPlaced)
    }

    @Test
    fun renderFaceUpPile() {
        val card = Card("♠", "A")
        val players = setOf(Player("Player One"), Player("Player Two"))
        val pile = Pile(
                PileDefinition(
                        name = "pile",
                        visibleTo = players
                ),
                setOf(card))
        assertThat(players)
                .allSatisfy {
                    assertThat(pile.render(it))
                            .isEqualTo("pile: Size: 1, Top Card: ♠A")
                }
    }

    @Test
    fun renderFaceDownPile() {
        val player = Player("player")
        val pile = Pile(
                PileDefinition(
                        name = "pile",
                        visibleTo = emptySet()
                ),
                setOf(Card("♠", "A")))
        val actual = pile.render(player)
        assertThat(actual).isEqualTo("pile: Size: 1, cards not visible")
    }

    @Test
    fun renderPlayerOwnPile() {
        val player = Player("Player One")
        val pile = Pile(
                PileDefinition(
                        name = "pile",
                        visibleTo = setOf(player)
                ),
                setOf(Card("♠", "A")))
        val actual = pile.render(player)
        assertThat(actual).isEqualTo("pile: Size: 1, Top Card: ♠A")
    }

    @Test
    fun renderPlayerOwnHand() { // hand = splayed pile that only one player can see
        val player = Player("Player One")
        val pile = Pile(PileDefinition(
                name = "pile",
                visibleTo = setOf(player),
                splay = Splay.right
        ))
        pile.place(Card("♠", "A"))
        pile.place(Card("♠", "2"))
        val actual = pile.render(player)
        assertThat(actual).isEqualTo("pile: Size: 2, Cards: ♠A ♠2")
    }

    @Test
    fun renderOtherPlayerOwnHand() {
        val card = Card("♠", "A")
        val player = Player("Player One")
        val pile = Pile(PileDefinition("pile", emptySet()), setOf(card))
        val actual = pile.render(player)
        assertThat(actual).isEqualTo("pile: Size: 1, cards not visible")
    }
}
