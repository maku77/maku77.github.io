---
title: "使用するデータファイルをショートコードのパラメータで切り替える"
date: "2018-12-07"
---

下記の記事では、data ディレクトリの基本的な使い方を説明しています。

* [全ページから参照できるデータを用意する（data ディレクトリ）](basic.html)

ここではもう少し踏み込んで、ショートコードを使って、パラメータで指定した名前のデータファイルを読み込めるようにしてみます。

まず、サンプルデータとして下記のようなデータファイルを用意します。
ファミコンのゲームタイトルの一覧です。

#### data/games/nes.yaml （データファイル）

~~~ yaml
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
~~~

次に、このデータファイルを使って、HTML の table として出力するショートコードを作成します。

#### layouts/shortcodes/games.html （ショートコード）

~~~ html
<table>
  <thead>
    <tr><th>発売日</th><th>タイトル</th><th>メーカー</th></tr>
  </thead>
  <tbody>
  {{ "{{" }}- range .Site.Data.games.nes }}
    <tr><td>{{ "{{" }} .date }}</td><td>{{ "{{" }} .title }}</td><td>{{ "{{" }} .maker }}</td></tr>
  {{ "{{" }}- end }}
  </tbody>
</table>
~~~

コンテンツページから、次のように使用することができます。

#### content/ref/nes-games.md

~~~
---
title: "ファミコンのゲームタイトル一覧"
---

{{ "{{" }}% games %}}
~~~

上記のショートコードは、`data/games/nes.yaml` という決まったデータファイルを読み込むようにしていますが、下記のようにすれば、パラメータで指定した Yaml ファイルを読み込めるようになります。
こうすることで、例えば、スーパーファミコンのタイトルリストを別の Yaml ファイルで作成しておき、同じショートコードを使ってテーブル出力できるようになります。

#### layouts/shortcodes/games.html （ショートコード）

~~~ html
<table>
  <thead>
    <tr><th>発売日</th><th>タイトル</th><th>メーカー</th></tr>
  </thead>
  <tbody>
  {{ "{{" }}- range (index .Site.Data.games (.Get 0)) }}
    <tr><td>{{ "{{" }} .date }}</td><td>{{ "{{" }} .title }}</td><td>{{ "{{" }} .maker }}</td></tr>
  {{ "{{" }}- end }}
  </tbody>
</table>
~~~

`.Get 0` という部分で、ショートコードに渡されたパラメータを取得しています。

この `games` ショートコードは下記のように使用します。
ショートコードのパラメータとして、`nes`、`snes` を指定することで、それぞれデータファイルとして `nes.yaml`、`snes.yaml` が使用されます。

#### content/ref/nes-games.md

~~~
---
title: "ファミコンのゲームタイトル一覧"
---

{{ "{{" }}% games nes %}}
~~~

#### content/ref/snes-games.md

~~~
---
title: "スーパーファミコンのゲームタイトル一覧"
---

{{ "{{" }}% games snes %}}
~~~

