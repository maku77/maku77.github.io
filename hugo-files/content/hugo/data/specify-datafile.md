---
title: "Hugo で参照するデータファイルをショートコードのパラメータで切り替える"
url: "p/jbi9ojq/"
date: "2018-12-07"
tags: ["Hugo"]
aliases: /hugo/data/specify-datafile.html
---

下記の記事では、data ディレクトリの基本的な使い方を説明しています。

* [全ページから参照できるデータを用意する（`data` ディレクトリ）](/p/5ru4kte/)

ここではもう少し踏み込んで、ショートコードを使って、パラメータで指定した名前のデータファイルを読み込めるようにしてみます。

まず、サンプルデータとして下記のようなデータファイルを用意します。
ファミコンのゲームタイトルの一覧です。

{{< code lang="yaml" title="data/games/nes.yaml （データファイル）" >}}
-
  title: ドンキーコング
  date: 1983-07-15
  maker: 任天堂
-
  title: ドンキーコングJR.
  date: 1983-07-15
  maker: 任天堂
-
  title: ポパイ
  date: 1983-07-15
  maker: 任天堂
{{< /code >}}

次に、このデータファイルを使って、HTML の table として出力するショートコードを作成します。

{{< code lang="go-html-template" title="layouts/shortcodes/games.html （ショートコード）" >}}
<table>
  <thead>
    <tr><th>発売日</th><th>タイトル</th><th>メーカー</th></tr>
  </thead>
  <tbody>
  {{- range .Site.Data.games.nes }}
    <tr><td>{{ .date }}</td><td>{{ .title }}</td><td>{{ .maker }}</td></tr>
  {{- end }}
  </tbody>
</table>
{{< /code >}}

コンテンツページから、次のように使用することができます。

{{< code lang="yaml" title="content/ref/nes-games.md" >}}
---
title: "ファミコンのゲームタイトル一覧"
---

{{%/* games */%}}
{{< /code >}}

上記のショートコードは、`data/games/nes.yaml` という決まったデータファイルを読み込むようにしていますが、下記のようにすれば、パラメータで指定した YAML ファイルを読み込めるようになります。
こうすることで、例えば、スーパーファミコンのタイトルリストを別の YAML ファイルで作成しておき、同じショートコードを使ってテーブル出力できるようになります。

{{< code lang="go-html-template" title="layouts/shortcodes/games.html （ショートコード）" >}}
<table>
  <thead>
    <tr><th>発売日</th><th>タイトル</th><th>メーカー</th></tr>
  </thead>
  <tbody>
  {{- range (index .Site.Data.games (.Get 0)) }}
    <tr><td>{{ .date }}</td><td>{{ .title }}</td><td>{{ .maker }}</td></tr>
  {{- end }}
  </tbody>
</table>
{{< /code >}}

__`.Get 0`__ という部分で、ショートコードに渡されたパラメータ（例えば `nes`）を取得しています。

この `games` ショートコードは下記のように使用します。
ショートコードのパラメータとして、`nes`、`snes` を指定することで、それぞれデータファイルとして `nes.yaml`、`snes.yaml` が使用されます。

{{< code lang="yaml" title="content/ref/nes-games.md" >}}
---
title: "ファミコンのゲームタイトル一覧"
---

{{%/* games nes */%}}
{{< /code >}}

{{< code lang="yaml" title="content/ref/snes-games.md" >}}
---
title: "スーパーファミコンのゲームタイトル一覧"
---

{{%/* games snes */%}}
{{< /code >}}

