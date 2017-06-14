package com.incrementive.splay

import com.incrementive.splay.Splay.none
import com.incrementive.splay.Splay.right
import java.util.*

class Pile(val name: String, private val splay: Splay = none, private val visibleTo: Set<Player>, deck: Set<Card> = emptySet()) {
    val cardCount
        get() = cards.size

    private val cards = deck.toMutableList()

    fun draw() = cards.removeAt(cards.lastIndex)

    fun take(index: Int) = cards.removeAt(index)

    fun place(card: Card) = cards.add(card)

    fun render(player: Player) =
            "$name: Card count: $cardCount" +
                    if (cards.isEmpty())
                        ""
                    else
                        ", " + when (player) {
                            in visibleTo -> when (splay) {
                                none -> "Top Card: " + cards.last()
                                right -> "Cards: " + cards.joinToString(separator = " ")
                            }
                            else -> "cards not visible"
                        }

    override fun equals(other: Any?) = other is Pile &&
            name == other.name &&
            splay == other.splay &&
            visibleTo == other.visibleTo &&
            cards == other.cards

    override fun hashCode() = Objects.hash(name, splay, visibleTo, cards)

    override fun toString(): String {
        return "Pile(name='$name', splay=$splay, visibleTo=$visibleTo, cards=$cards)"
    }


}