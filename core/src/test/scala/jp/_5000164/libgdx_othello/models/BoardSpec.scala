package jp._5000164.libgdx_othello.models

import org.scalatest.FreeSpec

class BoardSpec extends FreeSpec {
  "Board" - {
    "assign" - {
      "BoardData を受け取って BoardData を返す" in {
        val boardData: BoardData = BoardData(Array(Array(1)))
        assert(Board.assign(boardData).isInstanceOf[BoardData])
      }
    }
  }
}
