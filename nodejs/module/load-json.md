---
title: "外部の JSON ファイルを読み込む (require)"
date: "2018-12-10"
---

Node.js の `require` は、外部の JSON ファイルを読み込むのにも使用することができます。
下記は、設定情報を `settings.json` というファイルに記述しておき、その情報を読み込む例です。

#### settings.json

~~~ json
{
  "srcDir": "src/main/js",
  "testDir": "src/test/js"
}
~~~

#### main.js

~~~ javascript
var settings = require('./settings.json');  // 拡張子は省略可能
console.log(settings.srcDir);   //=> 'src/main/js'
console.log(settings.testDir);  //=> 'src/test/js'
~~~

上記では `require` に渡すファイル名として `./settings.json` と拡張子まで指定していますが、`./settings` のように拡張子を省略することもできます。
ただし、同じディレクトリに `./settings.js` があると、そちらが優先的に読み込まれてしまうので注意してください（拡張子だけ異なるファイルは作らない方がよいです）。

