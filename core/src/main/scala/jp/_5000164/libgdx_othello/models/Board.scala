package jp._5000164.libgdx_othello.models

/**
 * オセロの盤面についての処理を提供する
 */
object Board {
  /**
   * 初期化された盤面を生成する
   *
   * @return 初期化された盤面
   */
  def initialize = BoardData(Map(
    1 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty),
    2 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty),
    3 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty),
    4 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> White, 5 -> Black, 6 -> Empty, 7 -> Empty, 8 -> Empty),
    5 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Black, 5 -> White, 6 -> Empty, 7 -> Empty, 8 -> Empty),
    6 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty),
    7 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty),
    8 -> Map(1 -> Empty, 2 -> Empty, 3 -> Empty, 4 -> Empty, 5 -> Empty, 6 -> Empty, 7 -> Empty, 8 -> Empty)
  ))

  /**
   * 盤面に石を置く
   *
   * @param boardData  盤面情報
   * @param coordinate 石を置く位置
   * @param status     置く石の状態
   * @return 渡された盤面に石を置いた後の盤面
   */
  def assign(boardData: BoardData, coordinate: Coordinate, status: Status): AssignResult = {
    // すでに石が置いてあったらそこには置けないと判断する
    if (boardData.data.getOrElse(coordinate.y, Map()).getOrElse(coordinate.x, Black) != Empty) {
      return AssignResult(boardData, assignable = false)
    }

    // ひっくり返せる座標の一覧を計算する
    val upsetCoordinateList = calculateUpsetCoordinateList(boardData, coordinate, if (status == Black) BlackMove else WhiteMove)

    // ひっくり返せる座標がなかったらそこには置けないと判断する
    if (upsetCoordinateList.isEmpty) {
      return AssignResult(boardData, assignable = false)
    }

    AssignResult(BoardData(Map(1 -> Map(1 -> White))), assignable = true)
  }

  /**
   * ひっくり返せる座標の一覧を計算する
   *
   * @param boardData  盤面の状態
   * @param coordinate 石を置こうとした座標
   * @param moveStatus 石を置こうとした手番
   * @return ひっくり返せる座標の一覧
   */
  def calculateUpsetCoordinateList(boardData: BoardData, coordinate: Coordinate, moveStatus: MoveStatus): List[Coordinate] = {
    // 8 方向に対してひっくり返せるかを計算する
    (for (x <- -1 to 1; y <- -1 to 1 if !(x == 0 && y == 0)) yield calculateAssignable(CalculateAssignableData(
      boardData,
      coordinate,
      Direction(x, y),
      moveStatus,
      Nil,
      existsOpponentStone = false
    ))).collect { case d if d.upsetCoordinateList.nonEmpty => d.upsetCoordinateList }.flatten.toList
  }

  /**
   * 盤面の状態と打たれた石の情報から石を置けるかどうかを判断する
   *
   * @param calculateAssignableData 盤面と打たれた石の情報
   * @return 渡された情報に結果を付与した情報
   */
  def calculateAssignable(calculateAssignableData: CalculateAssignableData): CalculateAssignableData = {
    // 判定対象を移動する
    val judgeX = calculateAssignableData.coordinate.x + calculateAssignableData.direction.x
    val judgeY = calculateAssignableData.coordinate.y + calculateAssignableData.direction.y

    // 対象が空、もしくは盤面の外だったら処理を終了する
    // 空、もしくは盤面の外で処理を終了した場合は石は置くことはできない
    if (calculateAssignableData.boardData.data.getOrElse(judgeY, Map()).getOrElse(judgeX, Empty) == Empty) {
      return CalculateAssignableData(
        calculateAssignableData.boardData,
        calculateAssignableData.coordinate,
        calculateAssignableData.direction,
        calculateAssignableData.moveStatus,
        Nil,
        calculateAssignableData.existsOpponentStone
      )
    }

    // 対象が自分と同じ色だったら処理を終了する
    // 相手の石が存在し、自分と同じ色で処理が終了した場合は石を置くことができるのでひっくり返せる座標の情報を付与する
    val myStatus = if (calculateAssignableData.moveStatus == BlackMove) Black else White
    if (calculateAssignableData.boardData.data.getOrElse(judgeY, Map()).getOrElse(judgeX, Empty) == myStatus) {
      return CalculateAssignableData(
        calculateAssignableData.boardData,
        calculateAssignableData.coordinate,
        calculateAssignableData.direction,
        calculateAssignableData.moveStatus,
        upsetCoordinateList = if (calculateAssignableData.existsOpponentStone) Coordinate(calculateAssignableData.coordinate.x, calculateAssignableData.coordinate.y) :: calculateAssignableData.upsetCoordinateList else Nil,
        calculateAssignableData.existsOpponentStone
      )
    }

    // 端に到達するまで判定を行う
    // 一度でも座標を移動するということは相手の石は存在している
    calculateAssignable(CalculateAssignableData(
      calculateAssignableData.boardData,
      Coordinate(judgeX, judgeY),
      calculateAssignableData.direction,
      calculateAssignableData.moveStatus,
      calculateAssignableData.upsetCoordinateList,
      existsOpponentStone = true
    ))
  }
}

/**
 * 盤面の座標を表す
 *
 * @param x 盤面の列を表す
 * @param y 盤面の行を表す
 */
case class Coordinate(x: Int, y: Int) {
}

/**
 * 盤面の方向を表す
 *
 * @param x 列に対する方向を表す
 * @param y 行に対する方向を表す
 */
case class Direction(x: Int, y: Int) {
}

/**
 * 石を置こうとした結果を表す
 *
 * @param boardData  盤面の情報
 * @param assignable 石を置くことができるかどうか
 */
case class AssignResult(boardData: BoardData, assignable: Boolean) {
}

/**
 * 盤面に石を置けるかどうか判断するのに必要なデータを表す
 *
 * @param boardData           盤面の状態
 * @param coordinate          石を置こうとしている座標
 * @param direction           石をひっくり返せるか判定する方向
 * @param moveStatus          現在の手番
 * @param upsetCoordinateList ひっくり返せる座標の一覧 ひっくり返せない場合は None になる
 * @param existsOpponentStone 相手の石が存在するかどうか
 */
case class CalculateAssignableData(
                                    boardData: BoardData,
                                    coordinate: Coordinate,
                                    direction: Direction,
                                    moveStatus: MoveStatus,
                                    upsetCoordinateList: List[Coordinate],
                                    existsOpponentStone: Boolean
                                  ) {
}
