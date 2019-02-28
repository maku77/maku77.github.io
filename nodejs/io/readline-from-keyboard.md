---
title: "キーボードからの入力を取得する (reaqline.question)"
date: "2019-02-28"
---

キーボード（標準入力）からのユーザ入力を取得するには、**`readline`** 標準モジュールを使用します。
特に、`readline.question()` 関数を使用することで、ユーザへのプロンプトメッセージ表示と、ユーザ入力の取得を同時に行うことができます。


非同期でキーボード入力を取得する
----

下記の例では、プロンプトでユーザ名の入力を促し、入力された名前を使って挨拶文を表示します。

#### sample.js

```js
const readline = require('readline').createInterface({
  input: process.stdin,
  output: process.stdout
});

readline.question('What is your name? ', (answer) => {
  console.log(`Hello, ${answer}!`);
  readline.close();
});
```

#### 実行例

```
$ node sample.js
What is your name? Maku
Hello, Maku!
```


同期でキーボード入力を取得する
----

`readline` モジュールは基本的に非同期に結果を受け取るように設計されているため、同期的にユーザ入力を取得したい場合は、Promise を使用した処理が必要になります。
下記の例では、ECMAScript 2017 の `async`、`await` を利用して同期的にキーボードからの入力を取得しています。

#### sample.js

```js
// ユーザからのキーボード入力を取得する Promise を生成する
function readUserInput(question) {
  const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
  });

  return new Promise((resolve, reject) => {
    readline.question(question, (answer) => {
      resolve(answer);
      readline.close();
    });
  });
}

// メイン処理
(async function main() {
  const name = await readUserInput('What is your name? ');
  console.log(`Hello, ${name}!`);
})();
```

#### 実行例

```
$ node sample.js
What is your name? Maku
Hello, Maku!
```

