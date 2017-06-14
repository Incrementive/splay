package com.incrementive.splay

fun main(vararg args: String) {
    val player1 = Player("Player One")
    val player2 = Player("Player Two")
    val allPlayers = setOf(player1, player2)
    val deck = ('♠'..'♣').flatMap { suit ->
        listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
                .map { value -> Card(suit.toString(), value) }
    }
    val gameDefinition = GameDefinition(
            nameOfGame = "Go Fish",
            deck = deck.toSet(),
            deckReceivingPileDefinition = PileDefinition(
                    name = "draw",
                    visibleTo = GameVisibility.none
            ),
            perPlayerPileDefinitions = setOf(
                    PileDefinition(
                            name = "hand",
                            visibleTo = PlayerVisibility.owner,
                            splay = Splay.right
                    ),
                    PileDefinition(
                            name = "books",
                            visibleTo = PlayerVisibility.all,
                            splay = Splay.right
                    )),
            otherPileDefinitions = setOf(
                    PileDefinition(
                            name = "reveal pile",
                            visibleTo = GameVisibility.all
                    )
            )
    )
    val game = Game(gameDefinition, allPlayers)
    println("Welcome to ${gameDefinition.nameOfGame}")
    println()
    while (true) {
        println(game.render())
        println()
        print("Command> ")
        val command = readLine()!!
        if (command == "exit") break
        val (fromPile, index, toPile) = command.split(",").map(String::trim)
        game.command(fromPile, index.toInt(), toPile)
    }
}