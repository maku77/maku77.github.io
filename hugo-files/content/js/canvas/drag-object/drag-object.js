document.addEventListener('DOMContentLoaded', function() {
  'use strict';

  function Screen(canvas) {
    this.canvas = canvas;
    this.ctx = canvas.getContext('2d'); // CanvasRenderingContext2D
    this.sprites = [];
  }

  Screen.prototype.addSprite = function (sprite) {
    this.sprites.push(sprite);
  };

  Screen.prototype.redraw = function() {
    this.clearScreen();
    for (var i = 0; i < this.sprites.length; ++i) {
      this.sprites[i].draw(this.ctx);
    }
  };

  Screen.prototype.clearScreen = function () {
    this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
  }

  function Sprite(x, y) {
    this.x = x;
    this.y = y;
    this.width = 10;
    this.height = 10;
  }

  /**
   * Draw this sprite.
   * @param {CanvasRenderingContext2D} ctx - the rendering context
   * @returns {void}
   */
  Sprite.prototype.draw = function (ctx) {
    ctx.fillStyle = 'rgb(0,0,255)';
    ctx.fillRect(this.x, this.y, this.width, this.height);
  }

  function CanvasDragger(elem) {
    elem.addEventListener('mousedown', this._handleMouseDown.bind(this), false);
    elem.addEventListener('mouseup', this._handleMouseUp.bind(this), false);
    elem.addEventListener('mousemove', this._handleMouseMove.bind(this), false);
    this.elem = elem;
    this.isDragging = false;
  }

  CanvasDragger.prototype.setMoveHandler = function (callback) {
    this.moveHandler = callback;
  };

  CanvasDragger.prototype._handleMouseDown = function (e) {
    this.prevX = e.offsetX;
    this.prevY = e.offsetY;
    this.isDragging = true;
  };

  CanvasDragger.prototype._handleMouseUp = function (e) {
    this.isDragging = false;
  };

  CanvasDragger.prototype._handleMouseMove = function (e) {
    if (!this.isDragging) {
      return;
    }
    var diffX = e.offsetX - this.prevX;
    var diffY = e.offsetY - this.prevY;
    this.prevX = e.offsetX;
    this.prevY = e.offsetY;
    if (this.moveHandler) {
      this.moveHandler(diffX, diffY);
    }
  };

  function main() {
    var canvas = document.getElementById('my-canvas');
    var screen = new Screen(canvas);
    var sp1 = new Sprite(30, 10);
    var sp2 = new Sprite(50, 15);
    var sp3 = new Sprite(70, 20);
    screen.addSprite(sp1);
    screen.addSprite(sp2);
    screen.addSprite(sp3);
    screen.redraw();

    new CanvasDragger(canvas).setMoveHandler(function (dx, dy) {
      sp1.x += dx;
      sp1.y += dy;
      sp2.x += dx;
      sp2.y += dy;
      sp3.x += dx;
      sp3.y += dy;
      screen.redraw();
    });
  }

  main();
});

