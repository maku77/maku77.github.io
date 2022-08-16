---
title: "新しいページや別のサイトへ自動でリダイレクトする (aliases, redirectTo)"
url: "p/oj3izfu/"
permalink: "p/oj3izfu/"
date: "2022-08-17"
tags: ["Hugo"]
---

既存の Web ページの URL を変更した場合は、古い URL にアクセスしたときに自動的に新しい URL にリダイレクトされるようにしておくと親切です。
ここでは、2 種類のリダイレクト方法を説明します。

- `aliases` を使う方法 ... 同じ Hugo サイト内でリダイレクトする
- `redirectTo` を使う方法（独自） ... 別のサイトへリダイレクトする


aliases を使う方法
----

同一の Hugo サイト内でページの URL を変更した場合は、新しいページのフロントマターに __aliases__ プロパティを追加し、以前の URL を列挙しておきます。
古いページの Markdown ファイルは削除します。

<b>content/new-page/_index.md</b>

```yaml
---
title: "新しい URL のページ"
date: "2022-08-17"
aliases:
  - /old-page
  - /old-page2
  - /old-page3
---

本文...
```

上記のように記述しておくと、古いページの URL (`https://examle.com/old-page`) にアクセスしたときに、新しいページの URL (`https://example.com/new-page`) にリダイレクトしてくれます。
この仕組みは単純で、`aliases` に列挙した URL に対応するように、Hugo が次のような内容のファイルを出力しています。

<b>public/old-page/index.html</b>

```html
<!DOCTYPE html>
<html lang="ja-jp">
  <head>
    <title>https://example.com/new-page/</title>
    <link rel="canonical" href="https://example.com/new-page/">
    <meta name="robots" content="noindex">
    <meta charset="utf-8">
    <meta http-equiv="refresh" content="0; url=https://example.com/new-page/">
  </head>
</html>
```

`meta` 要素を使って、Web ブラウザに新しい URL にジャンプするように指示しています。
さらに、`link` 要素の Canonical URL として新しい URL を設定することで、Google などの検索エンジンに、正しい URL はこちらですよと知らせています。


redirectTo を使う方法（独自）
----

応用として、別の Web サイトにリダイレクトしたい場合を考えてみます。
前述の `aliases` を使う方法は、あくまで同じ Hugo サイト内でのリダイレクトを想定しています。
別ドメインへのリダイレクトを行う方法は Hugo 標準では用意されていないようなので、フロントマターに独自のプロパティ __`redirectTo`__ を指定できるようにしてみます。

まず、リダイレクト用の出力を行えるように、独自の __`redirect`__ レイアウトテンプレートを作っておきます。
内容は、Hugo の [aliases.html](https://github.com/gohugoio/hugo/blob/master/tpl/tplimpl/embedded/templates/alias.html) を真似しています（`.Permalink` の部分を `.Params.redirectTo` に置き換えています）。

<b>layouts/redirect.html</b>

```html
<!DOCTYPE html>
<html{{ "{{" }} with site.LanguageCode | default site.Language.Lang }} lang="{{ "{{" }} . }}"{{ "{{" }} end }}>
  <head>
    <title>{{ "{{" }} .Params.redirectTo }}</title>
    <link rel="canonical" href="{{ "{{" }} .Params.redirectTo }}">
    <meta name="robots" content="noindex">
    <meta charset="utf-8">
    <meta http-equiv="refresh" content="0; url={{ "{{" }} .Params.redirectTo }}">
  </head>
</html>
```

あとは、リダイレクトさせたいページのフロントマターで、このレイアウトを使うように指定すれば OK です。
リダイレクト先の URL は、__`redirectTo`__ プロパティで設定します。
さらに、[_build プロパティ](/p/4ziyhxe/) で、ページのコレクションに列挙されないようにしておくのがよさそうです。

<b>content/old-page/_index.md</b>

```yaml
---
title: "別のサイトへリダイレクトさせたいページ"
layout: redirect
redirectTo: "https://google.com"
_build: { list: false }
---
```

これで、`https://example.com/old-page/` にアクセスしたときに、`https://google.com` にリダイレクトされるようになります。

