package jp._5000164.libgdx_othello.models

import org.scalatest.FreeSpec

class BoardDataSpec extends FreeSpec {
  "BoardData" - {
    "Int の2次元配列として定義されているデータにアクセスできる" in {
      val boardData: BoardData = BoardData(Array(Array()))
      assert("jp._5000164.libgdx_othello.models.Status[][]" == boardData.data.getClass.getTypeName)
    }
  }
}
