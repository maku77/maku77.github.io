---
title: "Hugo でホームページの Page オブジェクトを取得する"
url: "p/ffr2bku/"
date: "2017-12-22"
tags: ["Hugo"]
description: "Hugo テンプレートの中で、ホームページを表す Page オブジェクトを取得する方法です。"
aliases: /hugo/template/homepage.html
---

Hugo テンプレートの中で、ホームページを表す Page オブジェクトを取得するには次のようにします。

```go-html-template
{{ $home := .Site.Home }}
{{ printf "%#v" $home }}
```

[公式ドキュメントの .Site 変数のページ](https://gohugo.io/variables/site/)には書いてないのですが、上記のように簡単にホームページの `Page` オブジェクトを取得できるみたいです。
2 行目では、取得した `Page` オブジェクトの内容を出力して確認しています。

`GetPage` 関数を使用して、Kind パラメータに `"home"` を指定するというのもありです。

```go-html-template
{{ $home := .Site.GetPage "home" }}
{{ printf "%v" (eq $home .Site.Home) }}  {{/* true になるはず */}}
```

このような取得方法を知るまでは、次のような感じでものすごく面倒な書き方してました。。。

```go-html-template
{{ $home := index (where .Site.Pages "URL" "==" "/") 0 }}
```

まず、`where` 関数を使い、全ページ (`.Site.Pages`) 中で URL が `/` であるものの `Page` オブジェクトの配列を取得しています。
実際にはホームページは 1 つだけなのですが、`where` は `Page` オブジェクトの配列を返すので、`index` 関数を使って 1 番目の要素だけを取り出しています。
なんて非効率的な（＾＾；

