package de.tictactoe

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.tictactoe.controller.controllerComponent.ControllerInterface
import de.tictactoe.model.gameboardComponent.GameboardInterface
import de.tictactoe.model.gameboardComponent.gameboardAdvancedImpl.Gameboard
import net.codingwell.scalaguice.ScalaModule

class TicTacToeModule extends AbstractModule with ScalaModule{

  val defaultSet:Boolean = false

  override def configure():Unit = {
    bindConstant().annotatedWith(Names.named("DefaultSet")).to(defaultSet)

    bind[GameboardInterface].to[Gameboard]
    bind[ControllerInterface].to[controller.controllerComponent.controllerBaseImpl.Controller]

    bind[GameboardInterface].annotatedWithName("normal").toInstance(new Gameboard(defaultSet))

    //bind[FileIOInterface].to[fileIoXmlImpl.FileIO]
  }

}
