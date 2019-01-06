---
title: "関数オブジェクトから関数名を取得する"
date: "2014-03-17"
---

関数オブジェクトの `name` プロパティで、関数名を取得することができます。

#### sample.js

```javascript
function func1() {}
var func2 = function () {};
var func3 = function func3() {};

console.log(func1.name);  //=> 'func1'
console.log(func2.name);  //=> ''
console.log(func3.name);  //=> 'func3'
```

