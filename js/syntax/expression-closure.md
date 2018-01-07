---
title: 式クロージャ (Expression Closure)
date: "2012-11-09"
---

JavaScript の独自拡張として **Expression Closure** という文法があります。
通常、関数リテラルは以下のように記述しますが、

```javascript
var add = function(a, b) { return a + b; }
```

以下のように、関数型プログラミング風に記述することができます。
中括弧 `{}` と `return` が省略されていることに注意してください。

```javascript
var add = function(a, b) return a + b;
```

