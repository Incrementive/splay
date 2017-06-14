package com.incrementive.splay

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PileTest {
    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun drawFromEmptyPile() {
        Pile(name = "pile", visibleTo = emptySet())
                .draw()
    }

    @Test
    fun drawFromNonEmptyPileReturnsCard() {
        val placedCard = Card("suit", "value")
        val pile = Pile(
                name = "pile",
                visibleTo = emptySet(),
                deck = setOf(placedCard))
        val drawnCard = pile.draw()
        assertThat(pile.cardCount)
                .isZero()
        assertThat(drawnCard)
                .isEqualTo(placedCard)
    }

    @Test
    fun placingCardOnEmptyPileResultsInSizeOfOne() {
        val placedCard = Card("suit", "value")
        val pile = Pile(
                name = "pile",
                visibleTo =  emptySet())
        pile.place(placedCard)
        assertThat(pile.cardCount)
                .isEqualTo(1)
    }

    @Test
    fun placingTwoCardsAllowsThemToBeDrawnInLastInFirstOutOrder() {
        val firstCardPlaced = Card("suit", "first")
        val secondCardPlaced = Card("suit", "second")
        val pile = Pile(
                name = "pile",
                visibleTo = emptySet())
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
                name = "pile",
                visibleTo = players,
                deck = setOf(card))
        assertThat(players)
                .allSatisfy {
                    assertThat(pile.render(it))
                            .isEqualTo("pile: Card count: 1, Top Card: ♠A")
                }
    }

    @Test
    fun renderFaceDownPile() {
        val player = Player("player")
        val pile = Pile(
                name = "pile",
                visibleTo = emptySet(),
                deck = setOf(Card("♠", "A")))
        val actual = pile.render(player)
        assertThat(actual).isEqualTo("pile: Card count: 1, cards not visible")
    }

    @Test
    fun renderPlayerOwnPile() {
        val player = Player("Player One")
        val pile = Pile(
                name = "pile",
                visibleTo = setOf(player),
                deck = setOf(Card("♠", "A")))
        val actual = pile.render(player)
        assertThat(actual).isEqualTo("pile: Card count: 1, Top Card: ♠A")
    }

    @Test
    fun renderPlayerOwnHand() { // hand = splayed pile that only one player can see
        val player = Player("Player One")
        val pile = Pile(
                name = "pile",
                splay = Splay.right,
                visibleTo = setOf(player))
        pile.place(Card("♠", "A"))
        pile.place(Card("♠", "2"))
        val actual = pile.render(player)
        assertThat(actual).isEqualTo("pile: Card count: 2, Cards: ♠A ♠2")
    }

    @Test
    fun renderOtherPlayerOwnHand() {
        val card = Card("♠", "A")
        val player = Player("Player One")
        val otherPlayer = Player("Player Two")
        val pile = Pile(
                name = "pile",
                visibleTo = setOf(otherPlayer),
                deck = setOf(card))
        val actual = pile.render(player)
        assertThat(actual).isEqualTo("pile: Card count: 1, cards not visible")
    }

    @Test
    fun `render visible empty splayed none pile only displays card count of zero`() {
        val player = Player("Player One")
        val pile = Pile(
                name = "pile",
                visibleTo = setOf(player),
                splay = Splay.none,
                deck = emptySet())
        val actual = pile.render(player)
        assertThat(actual).isEqualTo("pile: Card count: 0")
    }

    @Test
    fun `render visible empty splayed right pile only displays card count of zero`() {
        val player = Player("Player One")
        val pile = Pile(
                name = "pile",
                visibleTo = setOf(player),
                splay = Splay.right,
                deck = emptySet())
        val actual = pile.render(player)
        assertThat(actual).isEqualTo("pile: Card count: 0")
    }

    @Test
    fun `render not-visible empty pile only displays card count of zero`() {
        val player = Player("Player One")
        val pile = Pile(
                name = "pile",
                visibleTo = emptySet(),
                splay = Splay.right,
                deck = emptySet())
        val actual = pile.render(player)
        assertThat(actual).isEqualTo("pile: Card count: 0")
    }
}
