package jp._5000164.libgdx_othello.models

/**
 * 盤面情報
 *
 * @param data 盤面を生成する元となる二次元の List
 */
case class BoardData(data: Map[Int, Map[Int, Status]]) {
}
