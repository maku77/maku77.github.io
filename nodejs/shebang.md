---
title: node アプリをシェバングで起動するようにする
date: "2013-11-19"
---

一般的なスクリプト言語と同様に、Node.js で動作させるアプリもシェバングで node コマンドを指定しておくことができます。

#### app.js

```javascript
#!/usr/bin/env node
console.log('Hello');
```

あとは、js ファイルに実行権限を付けてやれば、コマンドとして扱うことができるようになります。

#### 実行方法
```
$ chmod +x app.js
$ ./app.js
Hello
```

