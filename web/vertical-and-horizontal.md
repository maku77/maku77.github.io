---
title: メニューの縦並びと横並びを切り替える
created: 2016-08-01
---

下記は、リスト項目の並べ方を縦／横で切り替える例です。
メディアクエリを使用して、画面幅の広い場合は横向きに並べています。

表示例
----

![vertical-and-horizontal-1.png](./vertical-and-horizontal-1.png){: .center }

![vertical-and-horizontal-2.png](./vertical-and-horizontal-2.png){: .center }

ソースコード
----

#### html

```html
<div class="siteNavi">
  <ul>
    <li>Home
    <li>Blog
    <li>About
  </ul>
</div>
```

#### css

```css
/* normalize */
* {
  margin: 0;
  padding: 0;
}

.siteNavi li {
  display: list-item;  /* 縦に並べる */
  list-style-type: none;
  text-transform: uppercase;
  padding: 0.5em;
  background: #ccc;
}

@media (min-width: 640px) {
  .siteNavi li {
    display: inline-block;  /* 横に並べる */
  }
}
```

