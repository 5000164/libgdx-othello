package jp._5000164.libgdx_othello.models

import org.scalatest.FreeSpec

class BoardDataSpec extends FreeSpec {
  "BoardData" - {
    "Status の 2 次元配列として定義されているデータにアクセスできる" in {
      val boardData: BoardData = BoardData(Map(1 -> Map(1 -> Empty)))
      assert("scala.collection.immutable.Map$Map1" == boardData.data.getClass.getTypeName)
      assert("scala.collection.immutable.Map$Map1" == boardData.data(1).getClass.getTypeName)
      assert("jp._5000164.libgdx_othello.models.Empty$" == boardData.data(1)(1).getClass.getTypeName)
    }

    "盤面の情報を元に新しい盤面を作る" in {
      val boardData: BoardData = BoardData(Map(1 -> Map(1 -> Empty)))
      val newBoardData = BoardData(boardData.data.updated(1, Map(1 -> Black)))
      assert(newBoardData.data(1)(1) == Black)
    }
  }
}
