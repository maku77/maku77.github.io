---
title: JavaScript で定数を定義する
created: 2012-10-27
---

ECMAScript の規格には、定数を定義するための構文はありません。
JavaScript の独自実装として、定数を定義するための `const` キーワードが用意されていることがあります。

SpiderMonkey (FireFox) や v8 (Chrome) では `const` が使用できます。

```javascript
js> const HOGE = 100;
js> console.log(HOGE);
100
```

Rhino (jrunscript) では `const` は使用できません。

```javascript
$ jrunscript
js> const HOGE = 100;
js> print(HOGE);
undefined
```

いずれにしても、`const` は独自拡張なので、使わないほうがよいでしょう。

