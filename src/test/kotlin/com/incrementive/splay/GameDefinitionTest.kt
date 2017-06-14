package com.incrementive.splay

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GameDefinitionTest {
    @Test
    fun onlyDeckReceivingPileReceivesDeck() {
        val suits = setOf("Hearts", "Spades", "Diamonds", "Clubs")
        val values = setOf("2", "Jack", "Queen", "King", "Ace")
        val deck = suits.flatMap { suit -> values.map { value -> Card(suit, value) } }.toSet()
        val config = GameDefinition(
                nameOfGame = "name of game",
                deck = deck,
                deckReceivingPileDefinition = PileDefinition("pile", GameVisibility.none),
                otherPileDefinitions = setOf(PileDefinition("other pile", GameVisibility.none)),
                perPlayerPileDefinitions = emptySet())
        val piles = config.buildPiles(setOf(Player("P1")))
        val pile = piles["pile"]!!
        assertThat(pile.cardCount)
                .isEqualTo(20)
        val otherPile = piles["other pile"]!!
        assertThat(otherPile.cardCount)
                .isEqualTo(0)
    }

    @Test
    fun givenOnePlayer_CreatesSinglePileForThatPlayer() {
        val owner = Player("P1")
        val piles = GameDefinition.buildPerPlayerPiles(setOf(owner), setOf(PileDefinition("hand", PlayerVisibility.owner)))

        assertThat(piles.size)
                .isEqualTo(1)

        assertThat(piles)
                .containsKey("P1 hand")

        assertThat(piles["P1 hand"]!!.name)
                .isEqualTo("P1 hand")
    }

    @Test
    fun buildingPileWithNoGameVisibilityBuildsPileNotVisibleToPlayers() {
        val allPlayers = emptySet<Player>()
        val piles = GameDefinition.buildOtherPiles(allPlayers, setOf(PileDefinition("draw", GameVisibility.none)))
        val drawPile = piles["draw"]!!
        assertThat(drawPile)
                .isEqualTo(Pile(
                        name = "draw",
                        visibleTo = emptySet(),
                        splay = Splay.none,
                        deck = emptySet()
                ))
    }

    @Test
    fun buildingPileWithAllGameVisibilityBuildsPileVisibleToAllPlayers() {
        val allPlayers = setOf(Player("player one"), Player("player two"))
        val piles = GameDefinition.buildOtherPiles(allPlayers, setOf(PileDefinition("draw", GameVisibility.all)))
        val drawPile = piles["draw"]!!
        assertThat(drawPile)
                .isEqualTo(Pile(
                        name = "draw",
                        visibleTo = allPlayers,
                        splay = Splay.none,
                        deck = emptySet()
                ))
    }
}