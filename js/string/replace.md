---
title: 文字列を置換する
created: 2012-01-20
---

最初に見つかった文字列を置換する
----

下記の例では、`ABC` という文字列を `XXX` に置換しています。

```javascript
var s = 'ABC DEF ABC';
s = s.replace('ABC', 'XXX');  // => "XXX DEF ABC"
```

最初に見つかった文字列だけを置換していることに注意してください。


正規表現パターンに一致する文字列を全て置換する
----

パターンに一致した部分すべてを一括置換したい場合は、正規表現の `g` オプションを付けて `replace()` を実行します。

```javascript
var s = "AAA 100 BBB 200";
s = s.replace(/\d+/g, '###');  // => "AAA ### BBB ###"
```
