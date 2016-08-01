---
title: 画面スクロールしても表示位置の変わらない要素を表示する
created: 2013-04-04
---

CSS で `position: fixed;` を指定すると、画面をスクロールしても指定した座標で要素を表示することができます。
以下の例では、`div` 要素を画面右上に固定して表示しています。

#### css

```css
#menu {
  position: fixed;
  top: 0px;
  right: 0px;
  width: 150px;
  background: lightgray;
  width: 150px;
}
```

#### html

```html
<div id="menu">
  <ul>
    <li>Item 1
    <li>Item 2
    <li>Item 3
  </ul>
</div>
```

ちなみに、`position: absolute;` を指定すると、ページ全体の右上に固定して要素を表示できます。

