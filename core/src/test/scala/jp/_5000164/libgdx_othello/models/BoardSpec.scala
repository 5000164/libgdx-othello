package jp._5000164.libgdx_othello.models

import org.scalatest.FreeSpec

class BoardSpec extends FreeSpec {
  "Board" - {
    "assign" - {
      "ボード情報を受け取ってボード情報を返す" in {
        val boardData: BoardData = BoardData(Array(Array(1)))
        val position = Position(1, 1)
        val status = White
        assert(Board.assign(boardData, position, status).isInstanceOf[BoardData])
      }

      "処理対象の位置情報を受け取る" in {
        val boardData = BoardData(Array(Array(1)))
        val position = Position(1, 1)
        val status = White
        Board.assign(boardData, position, status)
      }

      "置かれた石の情報を受け取る" in {
        val boardData = BoardData(Array(Array(1)))
        val position = Position(1, 1)
        val status = White
        Board.assign(boardData, position, status)
      }
    }
  }
}
