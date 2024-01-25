---
title: "Node.js スクリプトにシェバング (#!` を付けてコマンドとして起動できるようにする"
url: "p/ptactsj/"
date: "2013-11-19"
tags: ["nodejs"]
aliases: /nodejs/shebang.html
---

一般的なスクリプト言語と同様に、Node.js で動作させるアプリもシェバング (__`#!`__) で `node` コマンドを指定しておくことができます。

{{< code lang="js" title="app.js" >}}
#!/usr/bin/env node
console.log('Hello');
{{< /code >}}

あとは、`.js` ファイルに実行権限を付けてやれば、コマンドとして扱うことができるようになります。

{{< code lang="console" title="実行方法" >}}
$ chmod +x app.js
$ ./app.js
Hello
{{< /code >}}

