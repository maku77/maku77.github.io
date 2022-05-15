---
title: "全ページから参照できるデータを用意する（data ディレクトリ）"
date: "2018-04-08"
description: "data ディレクトリに YAML、JSON、TOML、CSV などの形式でデータファイルを作成しておくと、すべてのページから自由に参照できるようになります。"
---

データファイルを作成する
----

`data` ディレクトリに置くデータファイルは、下記のいずれかのフォーマットで作成します。

- [YAML フォーマット](http://yaml.org/spec/)
- [JSON フォーマット](https://www.ecma-international.org/publications/files/ECMA-ST/ECMA-404.pdf)
- [TOML フォーマット](https://github.com/toml-lang/toml)
- [CSV フォーマット](https://tools.ietf.org/html/rfc4180)

ここでは英単語の辞書データを YAML 形式のファイル `data/words.yaml` として作成してみましょう（拡張子は .yaml でも .yml でも構いません）。
`data` ディレクトリは、サイトのルートディレクトリに作成します（`content` ディレクトリや `layouts` ディレクトリと同じ階層に `data` ディレクトリを置きます）。

#### data/words.yaml

~~~ yaml
-
  en: apple
  jp: りんご
-
  en: banana
  jp: バナナ
-
  en: grape
  jp: ぶどう
~~~

<div class="note">
Hugo 0.37 より前のバージョンには YAML 処理の不具合があり、上記のようにルートレベルに配列データを記述することができませんでした。<code>hugo version</code> でバージョンを確認し、必要があれば <a href="../basic/install.html">最新の Hugo に更新</a> しましょう。
</div>

あるいは、JSON ファイルとして作成するのであればこんな感じ。

#### data/words.json

~~~ json
[
  {
    "en" : "apple",
    "jp" : "りんご"
  },
  {
    "en" : "banana",
    "jp" : "バナナ"
  },
  {
    "en" : "grape",
    "jp" : "ぶどう"
  }
]
~~~

できれば、よりシンプルに記述できる YAML 形式を使いたいですね。


データファイルを参照する
----

`data` ディレクトリ以下に作成したデータファイルを参照するには、テンプレートファイルの中で `.Site.Data.ファイル名` のように参照します。
ここでは、words セクションのインデックスページ (`content/words/_index.md`) にのみ適用されるセクションテンプレートを作成することにしましょう。

#### layouts/words/list.html

~~~ html
（省略）

<ul>
{{ "{{" }}- range .Site.Data.words }}
  <li>{{ "{{" }} .en }} は {{ "{{" }} .jp }} です。
{{ "{{" }}- end }}
</ul>

（省略）
~~~

次に、words セクションのコンテンツファイルを作成します。
セクションの生成が目的ですから、本文は空っぽで構いません。

#### content/words/_index.md

~~~
---
title: "単語集"
---

（本文は空っぽでOK）
~~~

これで、http://＜サイト＞/words/ にアクセスすると、次のような HTML コンテンツが表示されるはずです。

~~~ html
<ul>
  <li>apple は りんご です。
  <li>banana は バナナ です。
  <li>grape は ぶどう です。
</ul>
~~~


複数のデータファイルをループ処理する
----

`data` ディレクトリ以下のディレクトリ内に、複数のデータファイルを同じフォーマットで作成しておくと、テンプレート内でループ処理することができます。

次の例では、`data/books` ディレクトリ内に、書籍情報を表す YAML ファイルを配置しています。
1 つの YAML ファイルに、1 つの書籍の情報を記述します。
ディレクトリ内のファイルをすべてループ処理するので、YAML ファイルの名前は何でも構いません。

#### data/books/title1.yaml

~~~ yaml
title: ライザップ式 2週間ダイエットレシピ
author: RIZAP 株式会社
isbn13: 978-4537215489
~~~

#### data/books/title2.yaml

~~~ yaml
title: 大人女子の体にライザップ
author: RIZAP 株式会社
isbn13: 978-4537215618
~~~

本当はこれくらいの情報量であれば、1 ファイルに記述してしまった方がよいのですが、ここでは説明のために分けていると考えてください。

これらのデータファイルを参照するテンプレートは、例えば以下のように記述します。
ここでは、1 データごとの出力内容を、パーシャルテンプレートに分けてみましょう。

#### layouts/_default/list.html

~~~
（省略）

{{ "{{" }}- range .Site.Data.books }}
  {{ "{{" }} partial "book" . }}
{{ "{{" }}- end }}

（省略）
~~~

#### layouts/partials/book.html

~~~ html
<div class="book">
  <div class="book_title">{{ "{{" }} .title }}</div>
  <div>著者: {{ "{{" }} .author }}</div>
  <div>ISBN-13: {{ "{{" }} .isbn13 }}</div>
</div>
~~~

レンダリング結果は次のような感じになります。

~~~ html
（省略）

<div class="book">
  <div class="book_title">ライザップ式 2週間ダイエットレシピ</div>
  <div>著者: RIZAP 株式会社</div>
  <div>ISBN-13: 978-4537215489</div>
</div>

<div class="book">
  <div class="book_title">大人女子の体にライザップ</div>
  <div>著者: RIZAP 株式会社</div>
  <div>ISBN-13: 978-4537215618</div>
</div>

（省略）
~~~

