---
title: "レイアウト用のテンプレートの種類を理解する"
url: "p/zg4n7q9/"
date: "2017-11-28"
tags: ["Hugo"]
description: "Hugo では様々なタイプのテンプレートファイルを用意することができ、コンテンツファイルのパスに応じて、どのテンプレートファイルを使ってレンダリングされるかが決定されます。"
aliases: /hugo/layout/template-types.html
---

Hugo では様々なタイプのテンプレートファイルを用意することができ、コンテンツファイルのパスに応じて、どのテンプレートファイルを使ってレンダリングされるかが決定されます。

Hugo のテンプレートファイル
----

Hugo で Web サイトを作成する場合、コンテンツファイルとして Markdown ファイルを作成していきます。
この Markdown ファイルが HTML の形にレンダリングされるとき、__テンプレートファイル__ が使用されます。
Hugo のテンプレートの仕組みを理解することは、Hugo を使いこなすキモとなります。
ここでは、どのような種類のテンプレートファイルが、どのようなコンテンツに対して適用されてレンダリングされるのかを把握しましょう。

ここでは、下記のようなコンテンツ階層があるものとして説明していきます。

#### コンテンツのディレクトリ階層

```
content/
  +-- _index.md （ホームページ）
  +-- page1.md  （通常のページ）
  +-- page2.md  （通常のページ）
  +-- section1/
  |    +-- _index.md  （セクションのインデックスページ ＝ セクションページ）
  |    +-- pagel-1.md （通常のページ）
  |    +-- page1-2.md （通常のページ）
  +-- section2/
       +-- _index.md  （セクションのインデックスページ ＝ セクションページ）
       +-- page2-1.md （通常のページ）
       +-- page2-2.md （通常のページ）
```


ホームページテンプレート (Homepage Template)
----

- [Homepage Template](https://gohugo.io/templates/homepage/)

最上位の __`_index.md`__ をレンダリングするときは、__ホームページテンプレート__ が使用されます。

```
content/
  +-- _index.md ★ホームページテンプレートを使用してレンダリング
  +-- page1.md
  +-- page2.md
  +-- section1/
  |    +-- _index.md
  |    +-- pagel-1.md
  |    +-- page1-2.md
  +-- section2/
       +-- _index.md
       +-- page2-1.md
       +-- page2-2.md
```

ホームページテンプレートといっても、特定のテンプレートファイルが適用されるのではなく、下記のようなファイルのうち最初に見つかったテンプレートファイルが使用されることになります。

1. `/layouts/index.html`
2. `/layouts/_default/list.html`
3. `/themes/＜テーマ名＞/layouts/index.html`
4. `/themes/＜テーマ名＞/layouts/_default/list.html`

{{% note %}}
このように複数の候補の中からテンプレートファイルを探す仕組みは、別の種類のコンテンツファイルの場合も同様に採用されています。
この仕組みによって、異なる種類のコンテンツに、共通のテンプレートファイルを適用するといったことができるようになっています。
{{% /note %}}


セクションテンプレート (Section Templates)
----

- [Section Page Templates](https://gohugo.io/templates/section-templates/)

セクションごとのインデックスページ (`_index.md`) をレンダリングするときは、__セクションテンプレート__ が使用されます。

```
content/
  +-- _index.md
  +-- page1.md
  +-- page2.md
  +-- section1/
  |    +-- _index.md ★セクションテンプレートを使用してレンダリング
  |    +-- pagel-1.md
  |    +-- page1-2.md
  +-- section2/
       +-- _index.md ★セクションテンプレートを使用してレンダリング
       +-- page2-1.md
       +-- page2-2.md
```

下記のファイルの内、最初に見つかったテンプレートファイルがセクションテンプレートとして使用されます。

1. `/layouts/section/＜セクション名＞.html`
2. `/layouts/＜セクション名＞/list.html`
3. `/layouts/_default/section.html`
4. `/layouts/_default/list.html`
5. `/themes/＜テーマ名＞/layouts/section/＜セクション名＞.html`
6. `/themes/＜テーマ名＞/layouts/＜セクション名＞/list.html`
7. `/themes/＜テーマ名＞/layouts/_default/section.html`
8. `/themes/＜テーマ名＞/layouts/_default/list.html`

パスに `＜セクション名＞` を含んでいるものは、そのセクション固有のテンプレートを提供したいときに使用します。
多くの場合は、すべてのセクションページで共通のセクションテンプレートを使用しますので、3 番目の `/layouts/_default/section.html` を用意すると考えておけばよいでしょう。


リストテンプレート (List Templates)
----

- [List Templates](https://gohugo.io/templates/lists/)

__リストテンプレート__ は、前述のテンプレートファイルとは若干概念が異なるものです。
鋭い方は、ホームページテンプレート (Homepage Template) とセクションテンプレート (Section Templates) として使用されるテンプレートファイルの候補に、共通の `/layouts/_default/list.html` が含まれていることに気が付いたかもしれません。

Web サイトのホームページや、セクションごとのインデックスページ（セクションページ）には、そこに含まれるコンテンツのリストを表示するという共通の目的があります。
Hugo では、このようなリスト表示を目的としたコンテンツに適用可能できる、共通のテンプレートファイルとして `/layouts/_default/list.html` を参照するようになっています。
つまり、ホームページとセクションページで共通のレイアウトを使用するのであれば、`/layouts/_default/list.html` というファイルを 1 つだけ作成しておけばよいことになります。
この仕組みをリストテンプレートと呼んでいます。

```
content/
  +-- _index.md ★リストテンプレートの適用対象
  +-- page1.md
  +-- page2.md
  +-- section1/
  |    +-- _index.md ★リストテンプレートの適用対象
  |    +-- pagel-1.md
  |    +-- page1-2.md
  +-- section2/
       +-- _index.md ★リストテンプレートの適用対象
       +-- page2-1.md
       +-- page2-2.md
```

{{% note title="テンプレートの優先度" %}}
リストテンプレートの適用優先度は低く設定されているため、`/layouts/_default/list.html` ファイルは主にフォールバック機構により適用されることになります。
例えば、ホームページのレンダリングには、まずは `/layouts/index.html` テンプレートを適用しようとしますが、そのファイルが見つからなかった場合に `/layouts/_default/list.html` テンプレートを適用する、という動作をします。
{{% /note %}}


シングルページテンプレート (Single Page Templates)
----

- [Single Page Templates](https://gohugo.io/templates/single-page-templates/)

__シングルページテンプレート__ は、前述のリスト系のコンテンツに当てはまらないページ、つまり、個別記事のコンテンツに対して適用されるテンプレートです。

```
content/
  +-- _index.md
  +-- page1.md ★シングルページテンプレートを使用してレンダリング
  +-- page2.md ★シングルページテンプレートを使用してレンダリング
  +-- section1/
  |    +-- _index.md
  |    +-- pagel-1.md ★シングルページテンプレートを使用してレンダリング
  |    +-- page1-2.md ★シングルページテンプレートを使用してレンダリング
  +-- section2/
       +-- _index.md
       +-- page2-1.md ★シングルページテンプレートを使用してレンダリング
       +-- page2-2.md ★シングルページテンプレートを使用してレンダリング
```

下記のテンプレートファイルのうち、最初に見つかったファイルがシングルページテンプレートとして使用されます。

1. `/layouts/＜タイプ名＞/＜レイアウト名＞.html`
2. `/layouts/＜セクション名＞/＜レイアウト名＞.html`
3. `/layouts/＜タイプ名＞/single.html`
4. `/layouts/＜セクション名＞/single.html`
5. `/layouts/_default/single.html`
6. `/themes/＜テーマ名＞/layouts/＜タイプ名＞/＜レイアウト名＞.html`
7. `/themes/＜テーマ名＞/layouts/＜セクション名＞/＜レイアウト名＞.html`
8. `/themes/＜テーマ名＞/layouts/＜タイプ名＞/single.html`
9. `/themes/＜テーマ名＞/layouts/＜セクション名＞/single.html`
10. `/themes/＜テーマ名＞/layouts/_default/single.html`

セクションテンプレートと同様、セクション固有のテンプレートファイルを用意することもできますが、まずは `/layouts/_default/single.html` を作成し、すべてのページを同じレイアウトでレンダリングするところから始めるのがよいでしょう。

