---
title: "（コラム）フレキシブルボックスの旧式の定義方法"
date: "2017-08-03"
---

参考: [フレキシブルボックスによるレスポンシブレイアウト](flexible-box.html)

フレキシブルボックスの定義に、昔は `display: box` を使用していましたが、最新の W3C 仕様では `display: flex` となっています。

- Chrome ~20: display:-webkit-box
- Chrome ~28: **display:-webkit-flex**
- Chrome 28~: **display:flex**
- Firefox ~21: **display:-moz-box**
- Firefox 22~: **display:flex**
- IE9: (not supported)
- IE10: display:-ms-flexbox
- IE11: **display:flex**
- Safari ~6.0: **display:-webkit-box**
- Safari 6.1~: **display:-webkit-flex**
- Android ~4.3: **display:-webkit-box**
- Android 4.4~: **display:flex**
- iOS(Safari) ~6.1: **display:-webkit-box**
- iOS(Safari) 7.0~: **display:-webkit-flex**

フレキシブルボックス用の `display` 指定は、このように定義しておけばよさそうです。

~~~
display: flex;
display: -webkit-flex;
display: -webkit-box;
display: -moz-box;
~~~

さらに、伸縮時の割合の指定は、`box-flex` の代わりに `flex-grow` を使用します。

~~~
flex-grow: 1;
-webkit-flex-grow: 1;
-webkit-box-flex: 1;
-moz-box-flex: 1;
~~~

- 参考: [Can I use ... Support tables for HTML5, CSS3, etc](http://caniuse.com/#search=flex)
- 参考: [可変ボックス ("Flexbox") レイアウトの更新 (Windows)](https://msdn.microsoft.com/library/dn265027.aspx)

