---
title: Node.js で assert を使用する
date: "2014-07-13"
---

Node.js には、標準で assert モジュールが用意されています。

- 参考: [Assert - Node.js Manual & Documentation](https://nodejs.org/api/assert.html)

#### 使用例

```javascript
var assert = require('assert');

var count = 0;
assert(count > 0, 'count should be positive');
```

`console` オブジェクトには `assert()` メソッドが用意されており、こちらは何もモジュールをインポートしなくても使用できます。

```javascript
console.assert(count > 0, 'count should be positive');
```

