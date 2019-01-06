---
title: "HTML5 Canvas の描画領域全体をクリアする"
date: "2015-04-09"
---

Canvas の一部、あるいは全体の描画内容をクリアするには、下記のメソッドを使用します。

~~~ js
CanvasRenderingContext2D.clearRect(x, y, width, height)
~~~

- API ドキュメント<br>
  [CanvasRenderingContext2D.clearRect() - Web APIs｜MDN](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/clearRect)

#### 使用例

~~~ js
ctx.clearRect(0, 0, canvas.width, canvas.height);
~~~

