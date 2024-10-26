---
title: "Hugo のローカルサーバーで動作させているとき（開発時）のみ内容を出力する private ショートコードを作成する"
url: "p/jbr6kzd/"
date: "2018-04-02"
lastmod: "2024-10-26"
tags: ["Hugo"]
description: "自分の作業用備忘録としてはメモとして残したいけれど、インターネット上には公開したくない内容に関しては、Hugo をローカルサーバーとして動作させているときだけ出力するようなショートコードを作成しておくと便利です。"
changes:
  - 2024-10-26: .Site.IsServer を hugo.IsServer に変更
aliases: /hugo/shortcode/private.html
---

自分の作業用備忘録としてはメモとして残したいけれど、インターネット上には公開したくない内容に関しては、Hugo をローカルサーバーとして動作させているときだけ出力するようなショートコードを作成しておくと便利です。

private ショートコードを作成する
----

そのショートコードが、Hugo のローカルサーバ上 (hugo server) で使用されたかを判別するには、[`hugo.IsServer`](https://gohugo.io/functions/hugo/isserver/) を参照します。

{{< code lang="go-html-template" title="layouts/shortcodes/private.html" >}}
{{ if hugo.IsServer }}
  ローカルサーバで動作しています。
{{ end }}
{{< /code >}}

あとは、__`.Inner`__ 変数で、渡されたテキストを参照することができるので、たとえば次のように実装すればよいでしょう。

{{< code lang="go-html-template" title="layouts/shortcodes/private.html" >}}
{{ if hugo.IsServer }}
  <div class="private">{{ .Inner }}</div>
{{ end }}
{{< /code >}}

ここでは、出力する `div` 要素に __`private`__ というクラスを割り当てています。
下記は `private` クラスに適用するスタイルシートの例です（赤系の色で強調表示するようにしています）。

```css
.private {
  background: #fee;
  color: #f11;
  padding: 0.5em;
  border: solid 1px #f11;
}
.private::before {
  content: "Private メモ";
  display: block;
  font-weight: bolder;
}
```

使用方法
----

作成した private ショートコードは、記事ファイルの中で次のように利用します。

{{< code lang="yaml" title="content/sample.md" >}}
---
title: "サンプルタイトル"
---

{{</* private */>}}
  これは非公開なメモだよ。
{{</* /private */>}}
{{< /code >}}

次のように、内部のテキストで Markdown 構文を使用することもできます（その場合は、ショートコード名を `%` 記号で囲むことに注意してください）。

```
{{%/* private */%}}
これは非公開なメモだよ。

- Markdown の構文も使えるよ
- Markdown の構文も使えるよ
- Markdown の構文も使えるよ
{{%/* /private */%}}
```


（おまけ）IsServer が使えなかった頃はどうやっていたか？
----

`hugo.IsServer` は Hugo v.0.120.0 で追加されました。
また、それ以前は、`Site` 変数の `.Site.IsServer` を使っていました。
`.Site.IsServer` 変数は [Hugo v.0.38 で追加](https://github.com/gohugoio/hugo/pull/4541)されました。
v.0.37 以前のバージョンでは、ローカルサーバーで Web ページをホスティングしているかどうかを、`.Site.BaseURL` の値が `http://localhost` で始まっているかどうかで判断していました。

{{< code lang="go-html-template" title="layouts/shortcodes/private.html" >}}
{{ if hasPrefix .Site.BaseURL "http://localhost" }}
  ローカルサーバーで動作しています。
{{ end }}
{{< /code >}}

