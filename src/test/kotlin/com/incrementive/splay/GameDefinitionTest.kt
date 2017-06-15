package com.incrementive.splay

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GameDefinitionTest {
    @Test
    fun `only deck receiving pile receives deck`() {
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
    fun `given one player, creates single pile for that player`() {
        val owner = Player("P1")
        val piles = GameDefinition.buildPerPlayerPiles(setOf(owner), setOf(PileDefinition("hand", PlayerVisibility.owner))).toMap()

        assertThat(piles.size)
                .isEqualTo(1)

        assertThat(piles)
                .containsKey("P1 hand")

        assertThat(piles["P1 hand"]!!.name)
                .isEqualTo("P1 hand")
    }

    @Test
    fun `building pile with no game visibility builds pile not visible to players`() {
        val allPlayers = emptySet<Player>()
        val piles = GameDefinition.buildOtherPiles(allPlayers, setOf(PileDefinition("draw", GameVisibility.none))).toMap()
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
    fun `building pile with all game visibility builds pile visible to all players`() {
        val allPlayers = setOf(Player("player one"), Player("player two"))
        val piles = GameDefinition.buildOtherPiles(allPlayers, setOf(PileDefinition("draw", GameVisibility.all))).toMap()
        val drawPile = piles["draw"]!!
        assertThat(drawPile)
                .isEqualTo(Pile(
                        name = "draw",
                        visibleTo = allPlayers,
                        splay = Splay.none,
                        deck = emptySet()
                ))
    }

    @Test
    fun `creating piles with duplicate names throws exception`() {
        val gameDefinition = GameDefinition(nameOfGame = "name of game", deck = setOf(
                Card("suit", "rank")),
                deckReceivingPileDefinition = PileDefinition(name = "draw", visibleTo = GameVisibility.none),
                perPlayerPileDefinitions = emptySet(),
                otherPileDefinitions = setOf(PileDefinition(name = "draw", visibleTo = GameVisibility.none))
        )
        Assertions.assertThatThrownBy {
            gameDefinition.buildPiles(emptySet())
        }.hasMessage("Duplicated pile names: [draw]")
    }
}