---
title: "各種ページにおいて .Kind や .IsPage、.IsNode の値がどうなるかの一覧"
date: "2018-01-11"
description: "ページの種類によって条件分岐するテンプレートコードを記述するには、レンダリング時のコンテキストにおいて、.Kind の値や .IsPage などの値がどう変化するかを知っておく必要があります。"
---

| ページ種類 | .URL の例 | .Section | .Type | .Kind | .IsHome | .IsNode | .IsPage |
| ---------- | --------- | -------- | ----- | ----- | :-----: | :-----: | :-----: |
| ホームページ | `/` | nil | `page` | `home` | **true** | **true** | false |
| セクションページ（第１階層） | `/sec1/` | `sec1` | `sec1` | `section` | false | **true** | false |
| セクションページ（第２階層） | `/sec1/sec2/` | `sec1` | `sec1` | `section` | false | **true** | false |
| タクソノミーターム | `/tags/` | `tags` | `tags` | `taxonomyTerm` | false | **true** | false |
| タクソノミーリスト | `/tags/xxx/` | `tags` | `tags` | `taxonomy` | false | **true** | false |
| 404 ページ | nil | nil | `page` | `404` | false | **true** | false |
| 記事ページ（第１階層）| `/mypage/` | nil | `page` | `page` | false | false | **true** |
| 記事ページ（第２階層）| `/sec1/mypage/` | `sec1` | `sec1` | `page` | false | false | **true** |
| 記事ページ（第３階層）| `/sec1/sec2/mypage/` | `sec1` | `sec1` | `page` | false | false | **true** |

上記の値は、次のようなテンプレートコードを、ベーステンプレート (`layouts/_default/baseof.html`) に記述して調べています。

~~~
<pre>
.URL = {{ "{{" }} .URL }}
.Section = {{ "{{" }} .Section }}
.Type = {{ "{{" }} .Type }}
.Kind = {{ "{{" }} .Kind }}
.IsHome = {{ "{{" }} .IsHome }}
.IsNode = {{ "{{" }} .IsNode }}
.IsPage = {{ "{{" }} .IsPage }}
</pre>
~~~

これらの情報を使って分岐処理するときの方針としては、まずは、大まかに `.IsHome`、`.IsNode`、`.IsPage` の値で分岐できないかを考え、もう少し細かい分岐処理が必要であれば、`.Kind` の値を利用する、という感じにするとよいでしょう。

下記の例では、title 要素のテキストを構築するときに、`.IsHome` の値を使って分岐処理しています。
ホームページの場合は「サイト名」だけを表示し、その他のページでは「ページタイトル｜サイト名」のように連結して表示するようにしています。

~~~
<title>{{ "{{" }} if not .IsHome }}{{ "{{" }} .Title }}｜{{ "{{" }} end }}{{ "{{" }} .Site.Title }}</title>
~~~

あるいは、`cond` 関数を利用して次のように書くこともできますね。

~~~
<title>{{ "{{" }} cond .IsHome .Site.Title (print .Title "｜" .Site.Title)}}</title>
~~~

