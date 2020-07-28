package de.tictactoe.model.gameboardComponent.gameboardBaseImpl

case class Matrix[Field] (rows: Vector[Vector[Field]]) {

  def this(field: Field) = this(Vector.tabulate(3, 3) { (row, col) => field })

  def field(row: Int, col: Int): Field = rows(row)(col)

  def updateField(row: Int, col: Int, field: Field): Matrix[Field] = copy(rows.updated(row, rows(row).updated(col, field)))
}
