package de.tictactoe.model

import de.tictactoe.model.playerComponent.Player
import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when { "new" should {
    val player = Player("PlayerName")

    "has a name"  in {
      player.name should be("PlayerName")
    }
    "has a nice String representation" in {
      player.toString should be("PlayerName")
    }
  }}
}

