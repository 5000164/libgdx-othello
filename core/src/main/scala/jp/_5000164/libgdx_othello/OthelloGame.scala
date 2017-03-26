package jp._5000164.libgdx_othello

import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.{Game, Gdx, Input, Screen}
import jp._5000164.libgdx_othello.models._

class OthelloGame extends Game {
  var batch: SpriteBatch = _
  var font: BitmapFont = _
  var boardData: BoardData = Board.initialize
  var moveStatus: MoveStatus = BlackMove

  override def create() {
    batch = new SpriteBatch()
    font = new BitmapFont()
    setScreen(new TitleScreen(this))
  }

  override def render() {
    super.render()
  }

  override def dispose() {
    batch.dispose()
    font.dispose()
  }
}

class TitleScreen(game: OthelloGame) extends Screen {
  val camera = new OrthographicCamera()
  camera.setToOrtho(false, 800, 480)

  override def render(delta: Float) {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    camera.update()
    game.batch.setProjectionMatrix(camera.combined)

    game.batch.begin()
    game.font.draw(game.batch, "Othello Game", 100, 100)
    game.batch.end()

    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
      game.setScreen(new PlayScreen(game))
    }
  }

  override def resize(width: Int, height: Int) {}

  override def show() {}

  override def hide() {}

  override def pause() {}

  override def resume() {}

  override def dispose() {}
}

class PlayScreen(game: OthelloGame) extends Screen {
  val camera = new OrthographicCamera()
  camera.setToOrtho(false, 800, 480)
  lazy val shapeRenderer = new ShapeRenderer()

  val stage = new Stage(new ScreenViewport())
  Gdx.input.setInputProcessor(stage)

  for (i <- 1 to 8; j <- 1 to 8) {
    val tbs = new TextButtonStyle()
    tbs.font = game.font
    val tb = new TextButton("", tbs)
    tb.setSize(50f, 50f)
    tb.setPosition(i * 50f - 25f, 450 - j * 50f - 25f)
    tb.addListener(new ClickListener {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        val assignResult = Board.assign(game.boardData, Coordinate(i, j), if (game.moveStatus == BlackMove) Black else White)

        // 石を置けなかった場合はなにもせずに再度操作されるのを待つ
        if (!assignResult.assignable) {
          return
        }

        // 盤面の情報を更新する
        game.boardData = assignResult.boardData

        // 相手が置ける座標が残っていれば手番を交代してゲームを続行する
        val opponentAssignableCount = (for (boardX <- 1 to 8; boardY <- 1 to 8) yield Board.assign(game.boardData, Coordinate(boardX, boardY), if (game.moveStatus == BlackMove) White else Black).assignable).count(_ == true)
        if (opponentAssignableCount != 0) {
          game.moveStatus = game.moveStatus match {
            case BlackMove => WhiteMove
            case WhiteMove => BlackMove
          }
          return
        }

        // 相手が置ける座標が残ってなく自分の置ける座標が残っていれば相手はパスとして自分の手番のままゲームを続行する
        val oneselfAssignableCount = (for (boardX <- 1 to 8; boardY <- 1 to 8) yield Board.assign(game.boardData, Coordinate(boardX, boardY), if (game.moveStatus == BlackMove) Black else White).assignable).count(_ == true)
        if (oneselfAssignableCount != 0) {
          return
        }

        // どちらも置ける座標が残っていなければゲーム終了として白と黒の数を数えて勝敗を決める
        // TODO: 勝敗を決定する
        // TODO: 結果画面へ遷移する
      }
    })
    stage.addActor(tb)
  }

  override def render(delta: Float) {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    camera.update()
    game.batch.setProjectionMatrix(camera.combined)

    // 盤面のマス目を描画する
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
    for (i <- 1 to 8; j <- 1 to 8) {
      val x = i * 50f
      val y = j * 50f
      shapeRenderer.rect(x - 25f, y - 25f, 50f, 50f)
    }
    shapeRenderer.end()

    // 石を描画する
    for (i <- 1 to 8; j <- 1 to 8) {
      val x = i * 50f
      val y = 450 - j * 50f
      val boardX = i
      val boardY = j
      val shapeType = game.boardData.data(boardY)(boardX) match {
        case Black => Some(ShapeRenderer.ShapeType.Line)
        case White => Some(ShapeRenderer.ShapeType.Filled)
        case Empty => None
      }
      if (shapeType.isDefined) {
        shapeRenderer.begin(shapeType.get)
        shapeRenderer.circle(x, y, 20f)
        shapeRenderer.end()
      }
    }

    // 現在の手番を表示する
    val moveStatusString = game.moveStatus.toString
    game.batch.begin()
    game.font.draw(game.batch, moveStatusString, 20, 20)
    game.batch.end()

    stage.act(delta)
    stage.draw()
  }

  override def resize(width: Int, height: Int) {}

  override def show() {}

  override def hide() {}

  override def pause() {}

  override def resume() {}

  override def dispose() {}
}
