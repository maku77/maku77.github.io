---
title: "Node.jsメモ: npm run で任意のコマンドを実行する (npm run/start)"
url: "p/9kiba2c/"
date: "2013-12-28"
lastmod: "2019-10-04"
tags: ["nodejs"]
aliases: /nodejs/npm/npm-run.html
---

npm run-script とは
----

`package.json` の **`scripts`** プロパティでスクリプトを定義しておくと、**`npm run <スクリプト名>`** でそのスクリプトを実行できるようになります。
`run` は `run-script` の省略形であり、下記は同じ意味を持ちます。

```console
$ npm run hello
$ npm run-script hello
```

- 参考: [npm-run | npm Docs](https://docs.npmjs.com/cli/commands/npm-run)


npm run で Hello World
----

下記は、簡単な `echo` コマンドを実行するスクリプトの定義例です。
スクリプト名は `hello` にしています。

{{< code lang="json" title="package.json" >}}
{
  "scripts": {
    "hello": "echo Hello!"
  }
}
{{< /code >}}

`npm run` に続けてスクリプト名を指定すると、そのスクリプトを実行できます。

{{< code title="実行例" >}}
C:\> npm run hello

> node@1.0.0 hello C:
> echo Hello!

Hello!
{{< /code >}}

途中でコマンド実行の過程が表示されていますが、出力をスクリプトの実行結果だけに絞りたいときは、**`--silent`** オプションを付けて実行します。

```
C:\> npm run hello --silent
Hello!
```


予約スクリプト名 (start / stop / restart)
----

`packages` プロパティで定義した各スクリプトは、通常 `npm run <スクリプト名>` のように実行しますが、**`start`**、**`stop`**、**`restart`** といった予約されたスクリプト名を使うと、**`npm start`**、**`npm stop`**、**`npm restart`** といった省略形のコマンドで実行できるようになります。

{{< code lang="json" title="package.json" >}}
{
  "scripts": {
    "start": "echo START",
    "stop": "echo STOP"
  }
}
{{< /code >}}

{{< code title="実行例" >}}
$ npm start --silent
START

$ npm stop --silent
STOP

$ npm restart --silent
STOP
START
{{< /code >}}

`npm restart` を実行すると、`stop` スクリプト → `start` スクリプトの順番に呼び出されていることが分かります。
このあたりの、スクリプトの呼び出しルールに関しては、下記のマニュアルに詳しく記載されています。
いろいろなトリガ（イベント）で、いろいろな名前のスクリプトが呼び出されるようになっているので、活用できるものがないか一度目を通しておくとよいでしょう。

- [npm-scripts｜npm Documentation](https://docs.npmjs.com/misc/scripts)

例えば、次のように `restart` スクリプトを定義しておくと、`npm restart` を実行した場合に、`stop` と `start` スクリプトの代わりに `restart` スクリプトが呼び出されるようになります。
このような振る舞いも、上記のドキュメントを読むと分かります。

{{< code lang="json" title="package.json" >}}
{
  "scripts": {
    "start": "echo START",
    "stop": "echo STOP",
    "restart": "echo RESTART"
  }
}
{{< /code >}}

{{< code title="実行例" >}}
$ npm restart --silent
RESTART
{{< /code >}}

多くの Node.js アプリでは、この仕組みを利用して、`npm start` でメインスクリプトを起動するように定義しています。

{{< code lang="json" title="package.json" >}}
{
  "scripts": {
    "start": "node index.js"
  }
}
{{< /code >}}

これくらいシンプルであれば、直接 `node` コマンドを実行すればよいのではと思うかもしれませんが、この定義があることで、ユーザーはエントリポイントとなる JS ファイルのパスを意識せずに、単純に下記のように入力するだけで Node.js アプリを起動できるようになります。

{{< code lang="console" title="実行例" >}}
$ npm start
{{< /code >}}


npm run で使用できるコマンドの一覧を表示する
----

`npm run` コマンドをパラメータなしで実行すると、実行可能なコマンドの一覧（`scripts` で定義されたプロパティの一覧）を確認することができます。

{{< code lang="json" title="package.json" >}}
{
  "scripts": {
    "start": "node index.js",
    "stop": "echo Stop server",
    "hello1": "echo Hello1",
    "hello2": "echo Hello2"
  }
}
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ npm run
Lifecycle scripts included in node:
  start
    node index.js
  stop
    echo Stop server

available via `npm run-script`:
  hello1
    echo Hello1
  hello2
    echo Hello2
{{< /code >}}

前半に、`npm start` のように省略コマンドで起動できるスクリプト、後半に、`npm run hello1` のように実行するスクリプトの一覧が表示されます。

