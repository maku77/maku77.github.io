---
title: "IE 8 でも HTML5 の header や footer 要素を使えるようにする"
date: "2016-07-01"
---

IE 8 では HTML5 の header 要素や footer 要素に適用したスタイルが反映されません。
この問題を解決するには、下記のコードを head セクションに追加します。

```html
<!--[if lte IE 8]>
  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
```

新しい要素に対応していないブラウザでは、それらの要素をインライン要素として表示してしまうので、下記のようにブロック要素として定義しておかないといけないのですが、上記のスクリプトはこの設定も行ってくれます。

```css
article, aside, dialog, figcaption, figure, footer, header, hgroup, main, nav, section {
  display: block;
}
```

