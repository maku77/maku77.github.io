---
title: "Hugo の記事ページで TypeScript ファイルや NPM パッケージをインクルードして使用する"
url: "p/3adgjnq/"
date: "2022-06-02"
tags: ["Hugo"]
---

js.Build の基本
----

Hugo Pipes のひとつである [js.Build](https://gohugo.io/hugo-pipes/js/) を使用すると、TypeScript ファイル (`.ts`) のビルドを行うことができます。

下記のショートコードは、記事ページにバンドルした `.ts` ファイルを `.js` ファイルにトランスパイルし、`script` 要素として出力する例です。

{{< code lang="go-html-template" title="layouts/shortcodes/script.html" >}}
{{- $src := .Get "src" -}}
{{- $res := .Page.Resources.GetMatch $src -}}
{{- $built := $res | js.Build -}}
<script src="{{ $built.RelPermalink }}" defer></script>
{{< /code >}}

このショートコードを使う記事ページ (`.md`) と TypeScript ファイル (`.ts`) は、次のような感じで `content` ディレクトリ以下の任意のディレクトリに配置します。

- content/
  - sample-page/
    - index.md
    - hello.ts

ページバンドルの形にするため、記事ページは `index.md` というファイル名で作成し、同じディレクトリに `.ts` ファイルを配置することに注意してください。

{{< code lang="yaml" title="content/sample-page/index.md" >}}
---
title: "サンプルページ"
---

{{</* script src="hello.ts" */>}}
{{< /code >}}

{{< code lang="ts" title="content/sample-page/hello.ts" >}}
alert('Hello!')
{{< /code >}}


assets ディレクトリに配置した ts ファイルを参照する
----

前述の例では、ページバンドルした `.ts` ファイルを参照しましたが、プロジェクト全体で使う `.ts` ファイルは、共有リソースとして __`assets`__ ディレクトリ以下に入れておくと便利です（`assets` ディレクトリには、ビルド時に何らかの変換を行うファイルを配置します）。

{{< code lang="ts" title="assets/js/hello2.ts" >}}
alert('Hello2')
{{< /code >}}

`assets` ディレクトリ以下のリソースを参照するために、ショートコードを少しだけ変更する必要があります（`.Page.Resources.GetMatch` を `resources.Get` に置き換えます）。
ここでは、別のショートコード (`script-shared.html`) として作成することにします。

{{< code lang="go-html-template" title="layouts/shortcodes/script-shared.html" >}}
{{- $src := .Get "src" -}}
{{- $res := resources.Get $src -}}
{{- $built := $res | js.Build -}}
<script src="{{ $built.RelPermalink }}" defer></script>
{{< /code >}}

ショートコードの使い方は、前述の例と同様です。
`.ts` ファイルのパスは、`assets` ディレクトリからの相対パスで指定します。

{{< code lang="yaml" title="content/test.md" >}}
---
title: "テストページ"
---

{{</* script-shared src="js/hello2.ts" */>}}
{{< /code >}}

ちなみに、`assets` ディレクトリに配置した `.ts` ファイルは、`js.Build` への入力ファイルとして使われるだけで、最終的なビルド結果（`public` ディレクトリ）にコピーされることはありません。
上記の例の場合は、トランスパイル後の `public/js/hello2.js` だけが出力されます。


NPM パッケージを使用する
----

`js.Build` は、Hugo のプロジェクトルートに配置した __`node_modules`__ ディレクトリを参照してくれます（ver 0.78.1 以降）。
つまり、`npm install` でインストールした NPM パッケージを使用できるということです。

ここでは、[day.js](https://day.js.org/) という日付を扱うパッケージを使ってみます。
まず、Hugo プロジェクトのルートで次のように NPM パッケージをインストールします。

```console
$ npm init -y         # package.json の作成
$ npm install dayjs   # dayjs パッケージのインストール
```

あとは、TypeScript コードから普通に `import` して使えます。

{{< code lang="ts" title="assets/js/test.ts" >}}
import dayjs from 'dayjs'

const s = dayjs('2019-01-25').format('DD/MM/YYYY')
alert(s)
{{< /code >}}

もちろん、React などのパッケージも使えるので、Hugo のページ内に React コンポーネントを埋め込むといったことも可能です。
Hugo は自由度が高いですね。

