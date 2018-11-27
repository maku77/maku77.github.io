---
title: "npm start でアプリを起動できるようにする (npm start)"
date: "2013-12-28"
---

`package.json` の `scripts` プロパティを設定しておくと、`npm start` コマンドで設定したコマンドを実行できるようになります。

#### package.json

```json
{
  "scripts": {
    "start": "node app",
    "test": "node test"
  }
}
```

#### app.js

```javascript
console.log('Hello');
```

#### 実行例（パラメータを省略すると scripts.start に設定したコマンドが実行されます）

```
$ npm start
> @ start /Users/maku/tmp
> node app

Hello
```

