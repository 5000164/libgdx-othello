package jp._5000164.libgdx_othello.models

import org.scalatest.FreeSpec

class BoardSpec extends FreeSpec {
  "Board" - {
    "initialize" - {
      "盤面を初期化できる" in {
        assert(BoardData(List(
          List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
          List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
          List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
          List(Empty, Empty, Empty, White, Black, Empty, Empty, Empty),
          List(Empty, Empty, Empty, Black, White, Empty, Empty, Empty),
          List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
          List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
          List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty)
        )) == Board.initialize)
      }
    }

    "assign" - {
      "ボード情報を受け取ってボード情報を返す" in {
        val boardData: BoardData = BoardData(List(List()))
        val position = Position(1, 1)
        val status = White
        assert(Board.assign(boardData, position, status).isInstanceOf[BoardData])
      }

      "処理対象の位置情報を受け取る" in {
        val boardData = BoardData(List(List()))
        val position = Position(1, 1)
        val status = White
        Board.assign(boardData, position, status)
      }

      "置かれた石の情報を受け取る" in {
        val boardData = BoardData(List(List()))
        val position = Position(1, 1)
        val status = White
        Board.assign(boardData, position, status)
      }

      "盤面の情報を計算する" - {
        "指定された情報を追加する" in {
          val boardData = BoardData(List(List()))
          val position = Position(1, 1)
          val status = White
          val result = Board.assign(boardData, position, status)
          assert(White == result.data.head.head)
        }
      }
    }
  }
}
