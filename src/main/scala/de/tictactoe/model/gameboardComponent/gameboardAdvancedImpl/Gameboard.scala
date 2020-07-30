package de.tictactoe.model.gameboardComponent.gameboardAdvancedImpl

import com.google.inject.Inject
import com.google.inject.name.Named
import de.tictactoe.model.gameboardComponent.GameboardInterface
import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.{Gameboard => BaseGameboard}


class Gameboard @Inject() (@Named("DefaultSet")isSet:Boolean) extends BaseGameboard(isSet){

  override def createNewGameboard: GameboardInterface = new BaseGameboard(isSet)

}


