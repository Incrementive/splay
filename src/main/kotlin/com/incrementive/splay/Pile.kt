package com.incrementive.splay

class Pile(private val pileDefinition: PileDefinition, deck: Set<Card> = emptySet()) {
    val size
        get() = cards.size
    private val cards = deck.toMutableList()

    fun draw() = cards.removeAt(cards.lastIndex)

    fun take(index: Int) = cards.removeAt(index)

    fun place(card: Card) = cards.add(card)

    fun render(player: Player) =
            "${pileDefinition.name}: " +
            when (player) {
                in pileDefinition.visibleTo -> {
                    when (pileDefinition.splay) {
                        Splay.none -> "Size: $size, Top Card: ${cards.last()}"
                        Splay.right -> "Size: $size, Cards: " + cards.joinToString(separator = " ")
                    }
                }
                else -> "Size: $size, cards not visible"
            }
}