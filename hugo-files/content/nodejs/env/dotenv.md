---
title: "Node.jsメモ: 環境変数の代わりに .env ファイルを使用する (dotenv)"
url: "p/6kruhwy/"
date: "2019-03-26"
tags: ["nodejs"]
lastmod: "2019-10-11"
aliases: /nodejs/env/dotenv.html
---

dotenv モジュールとは
----

Node.js のプログラムから環境変数を参照するには `process.env` を参照します。

- 参考: [Node.js で環境変数を参照する (process.env)](/p/e44uun8/)

ユーザー設定を環境変数で行うようにしているアプリはよくあるのですが、たかが 1 つのアプリのために環境変数を設定するのは面倒だというユーザーは少なくありません（設定がどこで行われているのかわかりにくいという理由もあります）。

**`dotenv`** モジュールを使用すると、カレントディレクトリに置かれた **`.env`** ファイルを読み込み、そこに記述されたキーと値のペアを `process.env` 経由で参照できるようになります。

- [dotenv - npm](https://www.npmjs.com/package/dotenv)

つまり、**ユーザーはアプリの設定を、従来通り環境変数で行うこともできるし、`.env` ファイルでも行うことができるようになります**。
環境変数を使ってアプリの挙動を変えるような実装をしている場合は、`.env` ファイルによる設定もサポートしておくと親切です。
クラウドサービスと連携するアプリケーションなどは、**接続キー** などの情報を `.env` ファイルで指定できるようにしているものがよくあります。


dotenv モジュールのインストール
----

`dotenv` モジュールは `npm` コマンドで下記のようにインストールすることができます。
インストールされたモジュールは `node_modules` ディレクトリに格納されます。

```console
$ npm install dotenv
```


dotenv モジュールの基本的な使い方
----

まず、サンプルの設定ファイルとして、プロジェクトのルートディレクトリに、下記のような `.env` ファイルを作成します。

{{< code title=".env" >}}
# これはコメント行
KEY1=VALUE1
KEY2=VALUE2
KEY3=VALUE3
{{< /code >}}

`.env` ファイルの内容を読み込んで、`process.env` オブジェクトのプロパティとして参照できるようにするには、アプリの最初の方で下記のように実行します。

{{< code lang="js" title="sample.js" >}}
require('dotenv').config();
{{< /code >}}

基本的には**やることはこれだけ**です。
仮に、**`.env` ファイルが見つからない場合は、単純に無視されるだけ**なので、上記のコードは安心して入れておくことができます。

後は、通常の環境変数と同様に `process.env` オブジェクト経由で値を参照するだけです。

```js
require('dotenv').config();

console.log(process.env.KEY1);  //=> VALUE1
console.log(process.env.KEY2);  //=> VALUE2
console.log(process.env.KEY3);  //=> VALUE3
```

より実践的には、下記のように、設定が正しく行われているかの確認をするのがよいでしょう（これは、`dotenv` モジュールを使用しない場合でも同様ですが）。

```js
require('dotenv').config();

if (typeof process.env.KEY1 == 'undefined') {
  console.error('Error: "KEY1" is not set.');
  console.error('Please consider adding a .env file with KEY1.');
  process.exit(1);
}

console.log(process.env.KEY1);  //=> VALUE1
```


.env ファイルの記述ルール
----

- 空行は無視されます
- `#` で始まる行はコメントだとみなされます
- 値にスペースを含んでいてもクォーテーションで囲む必要はありません（`KEY1=AAA BBB CCC` も OK）。シングルクォートやダブルクォートで囲んでもよいです
- 値の記載を省略した場合は空文字になります（例: `KEY1=` とすると、`{ KEY1: '' }` になる）
- キーや値の前後の空白は削除されます。削除したくない場合は、シングルクォートかダブルクォートで値を囲む必要があります（例: `KEY1='  AAA  '`）
- エンコーディング形式は `utf8` で保存します（`dotenv.config()` のオプション `encoding` で変更できます）



dotenv によって読み込んだ値のみを参照する
----

`dotenv.config()` によって返されるオブジェクトの **`parsed`** プロパティを参照すると、`.env` ファイルで設定されているキーと値をオブジェクトの形で参照することができます。

{{< code lang="js" title="sample.js" >}}
const dotenv = require('dotenv');
const result = dotenv.config();
console.log(result.parsed);
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node sample.js
{ KEY1: 'VALUE1', KEY2: 'VALUE2', KEY3: 'VALUE3' }
{{< /code >}}

ちなみに、`.env` ファイルが存在しない場合は result は `undefined` になり、`.env` ファイルの内容が空のときは result は `{}` （空オブジェクト）になります。


カレントディレクトリではなく、js ファイルと同じディレクトリにある .env ファイルを読み込む
----

デフォルトでは、`.env` ファイルの読み込みパスは下記のように構築されるため、`node` コマンドを実行したときのカレントディレクトリに置かれた `.env` ファイルが読み込まれます。

```js
path.resolve(process.cwd(), '.env');
```

通常はあまり問題になることはありませんが、`js` ファイルが置かれたディレクトリ以外から `node` コマンドを実行する場合（例: `node myapp/main.js` のように実行した場合）は、`.env` ファイルが読み込めずに混乱するかもしれません。

カレントディレクトリではなく、プログラム（`.js` ファイル）自体が置かれたディレクトリ内にある `.env` ファイルを読み込むようにするには、`dotenv.config()` の `path` オプションで下記のようにパス指定します。

```js
const ENV_PATH = path.join(__dirname, '.env');
require('dotenv').config({ path: ENV_PATH });
```


dotenv によって .env ファイルの内容がうまく反映されない場合
----

### デバッグモードで確認する

`dotenv.config()` のオプションで **`debug`** フラグを `true` に設定しておくと、`dotenv` の処理内容が表示されるようになります。
これにより、`.env` ファイルの記述方法のミスなどを発見できます。

```js
require('dotenv').config({ debug: true });
```

```console
$ node sample.js
[dotenv][DEBUG] did not match key and value when parsing line 1: THIS_IS_AN_INVALID_LINE
```

### 環境変数に同じ名前の変数が設定されている

環境変数と、`.env` ファイルに同じ名前のキーが設定されている場合は、**環境変数の設定の方が優先されます**。
`.env` ファイルに設定した設定が反映されておらず、別の値が設定されているような場合は、環境変数の設定を確認してみてください。


.env ファイルは Git にコミットしない
----

`.env` ファイルは、ユーザ固有の設定や、Web API 接続用のキーなどの設定を行うために使用されます。
Git などのバージョン管理システムには、`.env` ファイルをコミットしないように気を付けてください。

Git を使用する想定であれば、プロジェクトのルートに置いた `.gitignore` ファイルに下記のようなエントリを追加しておきましょう。

```
.env
```

これで `.env` ファイルが Git の管理下から外されます（`git status` などでコミット候補として表示されなくなります）。

