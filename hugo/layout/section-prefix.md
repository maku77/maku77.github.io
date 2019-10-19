---
title: "ページタイトルに自動でセクションプレフィックスを付ける"
date: "2019-05-17"
description: "Hugo で多くのコンテンツを作成する場合、記事の種類ごとにセクション（ディレクトリ）に分けて管理することになります。このような場合、各ページのタイトルに対して、セクションのタイトルを自動で付加できれば、ページタイトルの管理が楽になります。"
---

何をやりたいか？
----

例えば、次のようなセクション構造で記事を管理しているとします。

```
contents/
  +-- _index.md (home page)
  +-- section1/
  |    +-- _index.md (section page)
  |    +-- page1.md  (single page)
  |    +-- page2.md  (single page)
  |    +-- page3.md  (single page)
  +-- section2/
       +-- _index.md (section page)
       +-- ...
```

各ディレクトリにある `_index.md` は、セクションページのコンテンツファイルとして使用されます。
このファイルのフロントマターには、下記のようにセクションのタイトルを記述することができます。

#### contents/section1/_index.md

```
---
title: "セクション1"
date: "2019-05-17"
---

セクションページの本文…
```

このセクションタイトルは、セクションページ（このケースでは `https://example.com/section1/`）にアクセスしたときに使用されるものですが、同じセクションに所属する記事ページのタイトルのプレフィックスにも自動で付加できると便利です。

例えば、`section1` に所属する記事ページとして下記のようなコンテンツがあるとします。

#### contents/section1/page.md

```
---
title: "タイトル1"
date: "2019-05-17"
---

ページの本文…
```

このように記述されているときに、表示するタイトルを `タイトル1` ではなく、**`セクション1: タイトル1`** のようにしたいということです。
このように、セクションタイトルを付加することで、あるページを単独で表示したときに、どのような種類の記事なのかが分かりやすくなります。


セクションプレフィックスの実装
----

ここでは、シングルページテンプレートで、セクションプレフィックスの仕組みを実現する方法を示します。
下記の例では、構築したページタイトルを `$pageTitle` という変数に格納しています。

#### layouts/_defaults/single.html （抜粋）

```
{{ "{{" }} $pageTitle := cond (eq .CurrentSection.Section "") (.Title) (printf "%s: %s" .CurrentSection.Title .Title) }}

<h1>{{ "{{" }} $pageTitle }}</h1>
```

コンテンツのルートディレクトリ (`contents/`) に配置したページにアクセスしたときは、`.CurrentSection.Section` の値が空になることを利用して、ページのタイトルをそのまま表示するようにしています。

上記では、`h1` 要素で表示上のページタイトルを設定していますが、`head` 要素下の `title` 要素にも同様に設定するようにしてください。
Google などの検索エンジンは、`title` 要素に指定したページタイトルを表示します。


（応用）セクションプレフィックス用のプロパティを用意する
----

上記の例では、セクションプレフィックスとして、自分が所属するセクションのセクションページに設定された `title` プロパティの値を使用していました。
ここでは、セクションプレフィックス用に、独自のプロパティ **`sectionPrefix`** を参照するようにしてみます。

このようなケースでは、Hugo の [Front Matter Cascade](https://gohugo.io/content-management/front-matter/#front-matter-cascade) という機能を使用すると便利です。
例えば、セクションページのフロントマターが下記のようになっているとすると、

#### contents/section1/_index.md

```
---
title: "セクションタイトル1"
cascade:
  sectionPrefix: "セクション1"
---
```

そのセクションの記事内では、`.Params.sectionPrefix` 変数で `セクション1` という値を参照できるようになります。

下記はテンプレートの実装例です。

#### テンプレート抜粋

```
{{ "{{" }} if ne .Kind "section" }}
  {{ "{{" }} with .Params.sectionPrefix }}
    {{ "{{" }} $.Scratch.Set "title" (printf "%s: " .) }}
  {{ "{{" }} end }}
{{ "{{" }} end }}
{{ "{{" }} $.Scratch.Add "title" .Title }}
{{ "{{" }} $pageTitle := $.Scratch.Get "title" }}

<h1>{{ "{{" }} $pageTitle }}</h1>
```

現在のページがセクションページでなければ、`sectionPrefix` プロパティの値をタイトルの前に連結する、という処理になっています。

