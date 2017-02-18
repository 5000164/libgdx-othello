package jp._5000164.libgdx_othello

import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.{Game, Gdx, Screen}

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
  }

  override def resize(width: Int, height: Int) {}

  override def show() {}

  override def hide() {}

  override def pause() {}

  override def resume() {}

  override def dispose() {}
}
