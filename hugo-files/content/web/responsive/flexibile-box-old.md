---
title: "（旧）CSS によるフレキシブルボックスの旧式の定義方法"
url: "p/uu32hpo/"
date: "2017-08-03"
tags: ["CSS"]
aliases: /web/flexible-box-old.html
---

- 参考: [フレキシブルボックスによるレスポンシブレイアウト](/p/tq3zi5j/)

フレキシブルボックスの定義に、昔は `display: box` を使用していましたが、最新の W3C 仕様では `display: flex` となっています。

- Chrome ~20: `display:-webkit-box`
- Chrome ~28: __`display:-webkit-flex`__
- Chrome 28~: __`display:flex`__
- Firefox ~21: __`display:-moz-box`__
- Firefox 22~: __`display:flex`__
- IE9: (not supported)
- IE10: `display:-ms-flexbox`
- IE11: __`display:flex`__
- Safari ~6.0: __`display:-webkit-box`__
- Safari 6.1~: __`display:-webkit-flex`__
- Android ~4.3: __`display:-webkit-box`__
- Android 4.4~: __`display:flex`__
- iOS(Safari) ~6.1: __`display:-webkit-box`__
- iOS(Safari) 7.0~: __`display:-webkit-flex`__

フレキシブルボックス用の `display` 指定は、このように定義しておけばよさそうです。

```
display: flex;
display: -webkit-flex;
display: -webkit-box;
display: -moz-box;
```

さらに、伸縮時の割合の指定は、`box-flex` の代わりに `flex-grow` を使用します。

```
flex-grow: 1;
-webkit-flex-grow: 1;
-webkit-box-flex: 1;
-moz-box-flex: 1;
```

- 参考: [Can I use ... Support tables for HTML5, CSS3, etc](http://caniuse.com/#search=flex)
- 参考: [可変ボックス ("Flexbox") レイアウトの更新 (Windows)](https://msdn.microsoft.com/library/dn265027.aspx)

