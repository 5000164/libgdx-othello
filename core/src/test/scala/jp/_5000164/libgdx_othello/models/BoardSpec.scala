package jp._5000164.libgdx_othello.models

import org.scalatest.FreeSpec

class BoardSpec extends FreeSpec {
  "Board" - {
    "initialize" - {
      "盤面を初期化できる" in {
        assert(Board.initialize == BoardData(Map(
          1 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty),
          2 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty),
          3 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty),
          4 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> White, 5 -> Black, 6 -> Empty, 7 -> Empty, 8 -> Empty),
          5 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Black, 5 -> White, 6 -> Empty, 7 -> Empty, 8 -> Empty),
          6 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty),
          7 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty),
          8 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty)
        )))
      }
    }

    "assign" - {
      "ボード情報を受け取って石を置こうとした結果を返す" in {
        val boardData: BoardData = BoardData(Map(1 -> Map()))
        val coordinate = Coordinate(1, 1)
        val status = Black
        assert(Board.assign(boardData, coordinate, status).isInstanceOf[AssignResult])
      }

      "処理対象の位置情報を受け取る" in {
        val boardData = BoardData(Map(1 -> Map()))
        val coordinate = Coordinate(1, 1)
        val status = Black
        Board.assign(boardData, coordinate, status)
      }

      "置かれた石の情報を受け取る" in {
        val boardData = BoardData(Map(1 -> Map()))
        val coordinate = Coordinate(1, 1)
        val status = Black
        Board.assign(boardData, coordinate, status)
      }

      "すでに石が置いてあったら置けなかったと判断する" in {
        val boardData = BoardData(Map(1 -> Map(1 -> Black)))
        val coordinate = Coordinate(1, 1)
        val status = Black
        val result = Board.assign(boardData, coordinate, status)
        assert(!result.assignable)
      }

      "盤面の情報を計算する" - {
        "指定された情報を追加する" in {
          val boardData = BoardData(Map(1 -> Map()))
          val coordinate = Coordinate(1, 1)
          val status = White
          val result = Board.assign(boardData, coordinate, status)
          assert(result.boardData.data(1)(1) == White)
        }
      }
    }

    "calculateAssignable" - {
      "渡された方向の状態がすぐに Empty だったら座標が移動しない" in {
        val boardData = BoardData(Map(
          1 -> Map(1 -> Empty, 2 -> Empty)
        ))
        val coordinate = Coordinate(1, 1)
        val direction = Direction(1, 0)
        val moveStatus = BlackMove
        val calculateAssignableData = CalculateAssignableData(boardData, coordinate, direction, moveStatus)
        val result = Board.calculateAssignable(calculateAssignableData)
        assert(result.coordinate == Coordinate(1, 1))
      }

      "渡された方向がすぐに盤面の外だったら座標が移動しない" in {
        val boardData = BoardData(Map(
          1 -> Map(1 -> Empty)
        ))
        val coordinate = Coordinate(1, 1)
        val direction = Direction(1, 0)
        val moveStatus = BlackMove
        val calculateAssignableData = CalculateAssignableData(boardData, coordinate, direction, moveStatus)
        val result = Board.calculateAssignable(calculateAssignableData)
        assert(result.coordinate == Coordinate(1, 1))
      }

      "渡された方向の状態がすぐに自分と同じ色だったら座標が移動しない" in {
        val boardData = BoardData(Map(
          1 -> Map(1 -> Empty, 2 -> Black)
        ))
        val coordinate = Coordinate(1, 1)
        val direction = Direction(1, 0)
        val moveStatus = BlackMove
        val calculateAssignableData = CalculateAssignableData(boardData, coordinate, direction, moveStatus)
        val result = Board.calculateAssignable(calculateAssignableData)
        assert(result.coordinate == Coordinate(1, 1))
      }

      "空のマスまで探索してひっくり返せなかった場合は座標が移動しない" in {
        val boardData = BoardData(Map(
          1 -> Map(1 -> Empty, 2 -> White, 3 -> Empty)
        ))
        val coordinate = Coordinate(1, 1)
        val direction = Direction(1, 0)
        val moveStatus = BlackMove
        val calculateAssignableData = CalculateAssignableData(boardData, coordinate, direction, moveStatus)
        val result = Board.calculateAssignable(calculateAssignableData)
        assert(result.coordinate == Coordinate(1, 1))
      }

      "画面端まで探索してひっくり返せなかった場合は座標が移動しない" in {
        val boardData = BoardData(Map(
          1 -> Map(1 -> Empty, 2 -> White)
        ))
        val coordinate = Coordinate(1, 1)
        val direction = Direction(1, 0)
        val moveStatus = BlackMove
        val calculateAssignableData = CalculateAssignableData(boardData, coordinate, direction, moveStatus)
        val result = Board.calculateAssignable(calculateAssignableData)
        assert(result.coordinate == Coordinate(1, 1))
      }

      "石を置けたと判断してひっくり返せる座標の最大である 1 マス右の座標を取得できる" in {
        val boardData = BoardData(Map(
          1 -> Map(1 -> Empty, 2 -> White, 3 -> Black)
        ))
        val coordinate = Coordinate(1, 1)
        val direction = Direction(1, 0)
        val moveStatus = BlackMove
        val calculateAssignableData = CalculateAssignableData(boardData, coordinate, direction, moveStatus)
        val result = Board.calculateAssignable(calculateAssignableData)
        assert(result.coordinate == Coordinate(2, 1))
      }

      "石を置けたと判断してひっくり返せる座標の最大である 2 マス右の座標を取得できる" in {
        val boardData = BoardData(Map(
          1 -> Map(1 -> Empty, 2 -> White, 3 -> White, 4 -> Black)
        ))
        val coordinate = Coordinate(1, 1)
        val direction = Direction(1, 0)
        val moveStatus = BlackMove
        val calculateAssignableData = CalculateAssignableData(boardData, coordinate, direction, moveStatus)
        val result = Board.calculateAssignable(calculateAssignableData)
        assert(result.coordinate == Coordinate(3, 1))
      }

      "石を置けたと判断してひっくり返せる座標の最大である 1 マス左上の座標を取得できる" in {
        val boardData = BoardData(Map(
          1 -> Map(1 -> White, 2 -> Empty, 3 -> Empty),
          2 -> Map(1 -> Empty, 2 -> Black, 3 -> Empty),
          3 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty)
        ))
        val coordinate = Coordinate(3, 3)
        val direction = Direction(-1, -1)
        val moveStatus = WhiteMove
        val calculateAssignableData = CalculateAssignableData(boardData, coordinate, direction, moveStatus)
        val result = Board.calculateAssignable(calculateAssignableData)
        assert(result.coordinate == Coordinate(2, 2))
      }
    }
  }
}
