package com.incrementive.splay

fun main(vararg args : String) {
    val player1 = Player("Player One")
    val player2 = Player("Player Two")
    val allPlayers = setOf(player1, player2)
    val deck = ('♠'..'♣').flatMap { suit ->
        listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
                .map { value -> Card(suit.toString(), value) }
    }
    val config = Config(
            nameOfGame = "Go Fish with two players",
            deck = deck.toSet(),
            deckReceivingPileDefinition = PileDefinition("draw", emptySet()),
            otherPileDefinitions = setOf(
                    PileDefinition(
                            name = "P1 hand",
                            visibleTo = setOf(player1),
                            splay = Splay.right
                    ),
                    PileDefinition(
                            name = "P1 books",
                            visibleTo = allPlayers,
                            splay = Splay.right
                    ),
                    PileDefinition(
                            name = "P2 hand",
                            visibleTo = setOf(player2),
                            splay = Splay.right
                    ),
                    PileDefinition(
                            name = "P2 books",
                            visibleTo = allPlayers,
                            splay = Splay.right
                    )
            )
    )
    val game = Game(config)
    while (true) {
        println(allPlayers.map(game::render).joinToString(separator = System.lineSeparator() + System.lineSeparator()))
        print("Command> ")
        val command = readLine()!!
        if (command == "exit") break
        val (fromPile, index, toPile) = command.split(",").map(String::trim)
        game.command(fromPile, index.toInt(), toPile)
    }
}