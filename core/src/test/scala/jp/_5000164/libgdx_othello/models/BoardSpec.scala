package jp._5000164.libgdx_othello.models

import org.scalatest.FreeSpec

class BoardSpec extends FreeSpec {
  "Board" - {
    "assign" - {
      "BoardData を受け取って BoardData を返す" in {
        val boardData: BoardData = BoardData(Array(Array(1)))
        val position = Position(1, 1)
        assert(Board.assign(boardData, position).isInstanceOf[BoardData])
      }

      "処理対象の位置情報を受け取る" in {
        val boardData = BoardData(Array(Array(1)))
        val position = Position(1, 1)
        Board.assign(boardData, position)
      }
    }
  }
}
