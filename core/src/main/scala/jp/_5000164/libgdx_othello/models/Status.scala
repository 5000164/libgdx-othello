package jp._5000164.libgdx_othello.models

/**
 * 盤面の状態を表す
 */
sealed abstract class Status
case object White extends Status
case object Black extends Status
case object Empty extends Status

/**
 * 手番を表す
 */
sealed abstract class MoveStatus
case object WhiteMove extends MoveStatus
case object BlackMove extends MoveStatus
