---
title: "Node.jsメモ: キーボードからの入力を取得する (readline.question)"
url: "p/9pjvmzj/"
date: "2019-02-28"
tags: ["nodejs"]
aliases: /nodejs/io/readline-from-keyboard.html
---

キーボード（標準入力）からのユーザ入力を取得するには、**`readline`** 標準モジュールを使用します。
特に、`readline.question()` 関数を使用することで、ユーザへのプロンプトメッセージ表示と、ユーザ入力の取得を同時に行うことができます。


非同期でキーボード入力を取得する
----

下記の例では、プロンプトでユーザ名の入力を促し、入力された名前を使って挨拶文を表示します。

{{< code lang="js" title="sample.js" >}}
import readline from 'node:readline';

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

rl.question('What is your name? ', (answer) => {
  console.log(`Hello, ${answer}!`);
  rl.close();
});
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ node sample.js
What is your name? Maku
Hello, Maku!
{{< /code >}}


同期でキーボード入力を取得する
----

`readline` モジュールは基本的に非同期に結果を受け取るように設計されているため、同期的にユーザ入力を取得したい場合は、Promise を使用した処理が必要になります。
下記の例では、ECMAScript 2017 の `async`、`await` を利用して同期的にキーボードからの入力を取得しています。

{{< code lang="js" title="sample.js" >}}
import readline from 'node:readline';

// ユーザからのキーボード入力を取得する Promise を生成する
function readUserInput(question) {
  const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
  });

  return new Promise((resolve, reject) => {
    rl.question(question, (answer) => {
      resolve(answer);
      rl.close();
    });
  });
}

// メイン処理
(async function main() {
  const name = await readUserInput('What is your name? ');
  console.log(`Hello, ${name}!`);
})();
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ node sample.js
What is your name? Maku
Hello, Maku!
{{< /code >}}

