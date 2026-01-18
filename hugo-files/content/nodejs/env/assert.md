---
title: "Node.jsメモ: Node.js で assert を使用する"
url: "p/3x3y8h8/"
date: "2014-07-13"
tags: ["nodejs"]
aliases: /nodejs/assert.html
---

Node.js には、標準で assert モジュールが用意されています。

- 参考: [Assert - Node.js Manual & Documentation](https://nodejs.org/api/assert.html)

{{< code lang="js" title="使用例" >}}
import assert from 'node:assert';

const count = 0;
assert(count > 0, 'count should be positive');
{{< /code >}}

`console` オブジェクトには `assert()` メソッドが用意されており、こちらは何もモジュールをインポートしなくても使用できます。

```js
console.assert(count > 0, 'count should be positive');
```

