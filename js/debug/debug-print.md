---
title: デバッグ出力用の print 関数の統一について
created: 2012-10-22
---

JavaScript にコア言語機能として文字列をデバッグ出力する `print()` のような関数は存在しないため、それぞれの環境で出力の方法を切り替える必要があります。

Rhino (jrunscript) の場合
----

```
js> print('Hello\n');
```
Rhino (jrunscript) の環境では、最初から `print()` 関数が使用できます。


Web ブラウザの場合
----

```javascript
alert('Hello');
document.write('Hello');
```

Node.js、Chrome の JavaScript Console、FireFox の FireBug の場合
----

```javascript
console.log('Hello');
```

