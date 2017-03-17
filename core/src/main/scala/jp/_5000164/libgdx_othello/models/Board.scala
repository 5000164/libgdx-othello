package jp._5000164.libgdx_othello.models

object Board {
  def initialize = BoardData(List(
    List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
    List(Empty, Empty, Empty, White, Black, Empty, Empty, Empty),
    List(Empty, Empty, Empty, Black, White, Empty, Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty)
  ))

  def assign(boardData: BoardData, position: Position, status: Status): BoardData = {
    BoardData(List(List(White)))
  }
}
