package com.incrementive.splay

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PileTest {
    @Test(expected = NoSuchElementException::class)
    fun drawFromEmptyPile() {
        Pile(emptySet()).draw()
    }

    @Test
    fun drawFromNonEmptyPileReturnsCard() {
        val placedCard = Card("suit", "value")
        val pile = Pile(setOf(placedCard))
        val drawnCard = pile.draw()
        assertThat(pile.size)
                .isZero()
        assertThat(drawnCard)
                .isEqualTo(placedCard)
    }

    @Test
    fun placingCardOnEmptyPileResultsInSizeOfOne() {
        val placedCard = Card("suit", "value")
        val pile = Pile(emptySet())
        pile.place(placedCard)
        assertThat(pile.size)
                .isEqualTo(1)
    }

    @Test
    fun placingTwoCardsAllowsThemToBeDrawnInLastInFirstOutOrder() {
        val firstCardPlaced = Card("suit", "first")
        val secondCardPlaced = Card("suit", "second")
        val pile = Pile(emptySet())
        pile.place(firstCardPlaced)
        pile.place(secondCardPlaced)
        assertThat(pile.draw())
                .isEqualTo(secondCardPlaced)
        assertThat(pile.draw())
                .isEqualTo(firstCardPlaced)
    }
}