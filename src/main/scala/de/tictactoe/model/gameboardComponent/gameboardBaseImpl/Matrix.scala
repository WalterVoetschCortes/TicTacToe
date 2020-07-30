package de.tictactoe.model.gameboardComponent.gameboardBaseImpl

/**
 * Matrix of fields to build the game board matrix of TicTacToe with Dimension 3x3.
 */
case class Matrix[Field] (rows: Vector[Vector[Field]]) {

  def this(field: Field) = this(Vector.tabulate(3, 3) { (row, col) => field })

  /**
   * method to get field in (row,col) of matrix
   * @param row
   * @param col
   * @return
   */
  def field(row: Int, col: Int): Field = rows(row)(col)

  /**
   * updates field in (row, col)
   * @param row
   * @param col
   * @param field
   * @return
   */
  def updateField(row: Int, col: Int, field: Field): Matrix[Field] = copy(rows.updated(row, rows(row).updated(col, field)))
}
