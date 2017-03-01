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

class OthelloGame extends Game {
  var batch: SpriteBatch = _
  var font: BitmapFont = _

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
  val tbs = new TextButtonStyle()
  tbs.font = game.font
  val tb = new TextButton("Text on the Button", tbs)
  tb.setPosition(300f, 300f)
  tb.getLabel.setFontScale(2)
  tb.addListener(new ClickListener {
    override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
      println("clicked")
    }
  })
  stage.addActor(tb)

  override def render(delta: Float) {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    camera.update()
    game.batch.setProjectionMatrix(camera.combined)

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line)

    for (i <- 1 to 10; j <- 1 to 10) {
      val x = i * 22
      val y = j * 22
      shapeRenderer.rect(x.toFloat, y.toFloat, 20f, 20f)
    }

    shapeRenderer.end()

    game.batch.begin()
    game.font.draw(game.batch, "Play", 100, 100)
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
