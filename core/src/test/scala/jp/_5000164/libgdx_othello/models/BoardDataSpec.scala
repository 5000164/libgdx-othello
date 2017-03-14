package jp._5000164.libgdx_othello.models

import org.scalatest.FreeSpec

class BoardDataSpec extends FreeSpec {
  "BoardData" - {
    "Int の2次元配列として定義されているデータにアクセスできる" in {
      val boardData: BoardData = BoardData(Array(Array(1)))
      assert("int[][]" == boardData.data.getClass.getTypeName)
    }
  }
}
