---
title: Node.js が require() で検索するパスのまとめ
date: "2014-09-22"
---

`app/main.js` の中で `require()` を実行したときに、Node.js がどのようにファイルをサーチするかのまとめです。

### `require('./module.js');` とした場合

1. app/module.js

### `require('./module');` とした場合

1. app/module.js
2. app/module.json
3. app/module.node

### `require('module');` とした場合

1. app/node_modules/module.js
2. app/node_modules/module/index.js
3. <System>/node_modules/module.js
4. <System>/node_modules/module/index.js

