package com.incrementive.splay

import java.util.*

class Pile(deck: Set<Card> = emptySet()) {
    val size
      get() = cards.size
    private val cards: Deque<Card> = ArrayDeque(deck)

    fun draw() = cards.pop()

    fun place(card: Card) {
        cards.push(card)
    }
}