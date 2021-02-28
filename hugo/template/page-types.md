---
title: "各種ページにおいて .Kind や .IsPage、.IsSection、.IsNode の値がどうなるかの一覧"
date: "2018-01-11"
description: "ページの種類によって条件分岐するテンプレートコードを記述するには、レンダリング時のコンテキストにおいて、.Kind の値や .IsPage などの値がどう変化するかを知っておく必要があります。"
---

| ページ種類 | .RelPermalink の例 | .Section | .Type | .Kind | .IsHome | .IsNode | .IsPage | .IsSection |
| ---------- | --------- | -------- | ----- | ----- | :-----: | :-----: | :-----: | :--------: |
| ホームページ | `/` | nil | `page` | `home` | **true** | **true** | false | false |
| セクションページ（第１階層） | `/sec1/` | `sec1` | `sec1` | `section` | false | **true** | false | **true** |
| セクションページ（第２階層） | `/sec1/sec2/` | `sec1` | `sec1` | `section` | false | **true** | false | **true** |
| タクソノミーターム | `/tags/` | `tags` | `tags` | `taxonomy` ※ | false | **true** | false | false |
| タクソノミーリスト | `/tags/xxx/` | `tags` | `tags` | `term` ※ | false | **true** | false | false |
| 404 ページ | nil | nil | `page` | `404` | false | **true** | false | false |
| 記事ページ（第１階層）| `/mypage/` | nil | `page` | `page` | false | false | **true** | false |
| 記事ページ（第２階層）| `/sec1/mypage/` | `sec1` | `sec1` | `page` | false | false | **true** | false |
| 記事ページ（第３階層）| `/sec1/sec2/mypage/` | `sec1` | `sec1` | `page` | false | false | **true** | false |

※[Hugo v0.73](https://github.com/gohugoio/hugo/issues/6911) において `taxonomyTerm → taxonomy`、`taxonomy → term` という `.Kind` 値の変更が入っているので、バージョンアップ時はご注意ください。

上記の値は、次のようなテンプレートコードを、ベーステンプレート (`layouts/_default/baseof.html`) に記述して調べています（__2020-04-27: `.IsSection` を追記、`.URL` (deprecated) を `.RelPermalink` に変更__）。

~~~
<pre>
.RelPermalink = {{ "{{" }} .RelPermalink }}
.Section = {{ "{{" }} .Section }}
.Type = {{ "{{" }} .Type }}
.Kind = {{ "{{" }} .Kind }}
.IsHome = {{ "{{" }} .IsHome }}
.IsNode = {{ "{{" }} .IsNode }}
.IsPage = {{ "{{" }} .IsPage }}
.IsSection = {{ "{{" }} .IsSection }}
</pre>
~~~

これらの情報を使って分岐処理するときの方針としては、まずは、大まかに `.IsHome`、`.IsNode`、`.IsPage`、`.IsSection` の値で分岐できないかを考え、もう少し細かい分岐処理が必要であれば、`.Kind` の値を利用する、という感じにするとよいでしょう。

下記の例では、title 要素のテキストを構築するときに、`.IsHome` の値を使って分岐処理しています。
ホームページの場合は「サイト名」だけを表示し、その他のページでは「ページタイトル｜サイト名」のように連結して表示するようにしています。

~~~
<title>{{ "{{" }} if not .IsHome }}{{ "{{" }} .Title }}｜{{ "{{" }} end }}{{ "{{" }} .Site.Title }}</title>
~~~

あるいは、`cond` 関数を利用して次のように書くこともできますね。

~~~
<title>{{ "{{" }} cond .IsHome .Site.Title (print .Title "｜" .Site.Title)}}</title>
~~~

