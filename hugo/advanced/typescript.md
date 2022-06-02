---
title: "Hugo の記事ページで TypeScript ファイルをインクルードして使用する"
url: "p/3adgjnq/"
permalink: "p/3adgjnq/"
date: "2022-06-02"
tags: ["Hugo"]
---

js.Build の基本
----

Hugo Pipes のひとつである [js.Build](https://gohugo.io/hugo-pipes/js/) を使用すると、TypeScript ファイル (`.ts`) のビルドを行うことができます。

下記のショートコードは、記事ページにバンドルした `.ts` ファイルを `.js` ファイルにトランスパイルし、`script` 要素として出力する例です。

#### layouts/shortcodes/script.html

```go
{{ "{{" }}- $src := .Get "src" -}}
{{ "{{" }}- $res := .Page.Resources.GetMatch $src -}}
{{ "{{" }}- $built := $res | js.Build -}}
<script src="{{ "{{" }} $built.RelPermalink }}" defer></script>
```

このショートコードを使う記事ページ (`.md`) と TypeScript ファイル (`.ts`) は、次のような感じで `content` ディレクトリ以下の任意のディレクトリに配置します。

- content/
  - sample-page/
    - index.md
    - hello.ts

ページバンドルの形にするため、記事ページは `index.md` というファイル名で作成し、同じディレクトリに `.ts` ファイルを配置することに注意してください。

#### content/sample-page/index.md

```go
---
title: "サンプルページ"
---

{{ "{{" }}< script src="hello.ts" >}}
```

#### content/sample-page/hello.ts

```ts
alert('Hello!')
```


assets ディレクトリに配置した ts ファイルを参照する
----

前述の例では、ページバンドルした `.ts` ファイルを参照しましたが、プロジェクト全体で使う `.ts` ファイルは、共有リソースとして `assets` ディレクトリ以下に入れておくと便利です。

#### assets/js/hello2.ts

```ts
alert('Hello2')
```

`assets` ディレクトリ以下のリソースを参照するために、ショートコードを少しだけ変更する必要があります（`.Page.Resources.GetMatch` を `resources.Get` に置き換えます）。
ここでは、別のショートコード (`script-shared.html`) として作成することにします。

#### layouts/shortcodes/script-shared.html

```go
{{ "{{" }}- $src := .Get "src" -}}
{{ "{{" }}- $res := resources.Get $src -}}
{{ "{{" }}- $built := $res | js.Build -}}
<script src="{{ "{{" }} $built.RelPermalink }}" defer></script>
```

ショートコードの使い方は、前述の例と同様です。
`.ts` ファイルのパスは、`assets` ディレクトリからの相対パスで指定します。

#### content/test.md

```go
---
title: "テストページ"
---

{{ "{{" }}< script-shared src="js/hello2.ts" >}}
```

ちなみに、`assets` ディレクトリに配置した `.ts` ファイルは、`js.Build` への入力ファイルとして使われるだけで、最終的なビルド結果（`public` ディレクトリ）にコピーされることはありません。
上記の例の場合は、トランスパイル後の `public/js/hello2.js` だけが出力されます。


NPM パッケージを使用する
----

`js.Build` は、Hugo のプロジェクトルートに配置した __`node_modules`__ ディレクトリを参照してくれます（ver 0.78.1 以降）。
つまり、`npm install` でインストールした NPM パッケージを使用できるということです。

ここでは、[day.js](https://day.js.org/) という日付を扱うパッケージを使ってみます。
Hugo プロジェクトのルートで次のように実行します。

```console
$ npm init -y         # package.json の作成
$ npm install dayjs   # dayjs パッケージのインストール
```

あとは、TypeScript コードから普通に `import` して使えます。

#### assets/js/test.ts

```ts
import dayjs from 'dayjs'

const s = dayjs('2019-01-25').format('DD/MM/YYYY')
alert(s)
```

もちろん、React などのパッケージも使えるので、Hugo のページ内に React コンポーネントを埋め込むといったことも可能です。
Hugo 何でもできちゃうなぁ。

