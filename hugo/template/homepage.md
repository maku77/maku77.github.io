---
title: "ホームページの Page オブジェクトを取得する"
date: "2017-12-22"
description: "Hugo テンプレートの中で、ホームページを表す Page オブジェクトを取得する方法です。"
---

~~~
{{ "{{" }} $home := .Site.Home }}
{{ "{{" }} printf "%#v" $home }}
~~~

[公式ドキュメントの .Site 変数のページ](https://gohugo.io/variables/site/)には書いてないのですが、上記のように簡単にホームページの `Page` オブジェクトを取得できるみたいです。
２行目では、テストのために、取得した `Page` オブジェクトの内容を出力しています。

`GetPage` 関数を使用して、Kind パラメータに `"home"` を指定するというのもあり。

~~~
{{ "{{" }} $home := .Site.GetPage "home" }}
{{ "{{" }} printf "%v" (eq $home .Site.Home) }}  {{ "{{" }}/* true になるはず */}}
~~~

このような取得方法を知るまでは、次のような感じでものすごく面倒な書き方してました。。。

~~~
{{ "{{" }} $home := index (where .Site.Pages "URL" "==" "/") 0 }}
~~~

まず、`where` 関数を使い、全ページ (`.Site.Pages`) 中で URL が `/` であるものの `Page` オブジェクトの配列を取得しています。
実際にはホームページは１つだけなのですが、`where` は `Page` オブジェクトの配列を返すので、`index` 関数を使って１番目の要素だけを取り出しています。
なんて非効率的な。。。
