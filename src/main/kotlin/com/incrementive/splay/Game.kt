package com.incrementive.splay

class Game(gameDefinition: GameDefinition, private val allPlayers: Set<Player>) {
    private val piles = gameDefinition.buildPiles(allPlayers)
    fun pileFor(pileName: String) = piles[pileName]!!

    fun render() =
            allPlayers.joinToString(
                    separator = System.lineSeparator() + System.lineSeparator(),
                    transform = this::render)

    fun render(player: Player) =
            piles.values.joinToString(
                    prefix = "View for ${player.name}" + System.lineSeparator(),
                    transform = { it.render(player) },
                    separator = System.lineSeparator())

    fun command(fromPileName: String, index: Int, toPileName: String) {
        val fromPile = pileFor(fromPileName)
        val toPile = pileFor(toPileName)
        toPile.place(fromPile.take(index))
    }
}