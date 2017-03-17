package jp._5000164.libgdx_othello.models

/**
 * オセロの盤面について処理を提供する
 */
object Board {
  /**
   * 初期化された盤面を生成する
   *
   * @return 初期化された盤面
   */
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

  /**
   * 盤面に石を置く
   *
   * @param boardData 盤面情報
   * @param position  石を置く位置
   * @param status    置く石の状態
   * @return 渡された盤面に石を置いた後の盤面
   */
  def assign(boardData: BoardData, position: Position, status: Status): BoardData = {
    BoardData(List(List(White)))
  }
}
