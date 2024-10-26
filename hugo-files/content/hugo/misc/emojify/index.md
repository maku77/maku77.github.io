---
title: "Hugo のテンプレートの中で絵文字を使用する (emojify)"
url: "p/88e7tiz/"
date: "2020-04-13"
lastmod: "2024-10-26"
tags: ["Hugo"]
changes:
  - 2024-10-26: .Site.IsServer を hugo.IsServer に変更
aliases: /hugo/template/emojify.html
---

emojify 関数の概要
----

Hugo のテンプレートファイルの中で [emojify 関数](https://gohugo.io/functions/emojify/) を使用すると、GitHub や Slack などで使える有名どころの絵文字 (emoticons) を出力することができます。
使えるアイコンのリストは下記サイトで確認できます。

- [🎁 Emoji cheat sheet for GitHub, Basecamp, Slack & more](https://www.webfx.com/tools/emoji-cheat-sheet/)

例えば、`:smile:` アイコンを表示したければ、任意のテンプレートファイルの中で次のように記述します。

```go-html-template
{{ emojify ":smile:" }}
```

emojify 関数の使用例
----

下記の例では、Hugo をサーバーモードで起動しているときに、ローカルファイルのパスをアイコン付きで表示します。

{{< code lang="go-html-template" title="layouts/_default/single.html（抜粋）" >}}
{{ if hugo.IsServer }}
  {{ emojify ":memo:" }}{{ .File.Filename }}
{{ end }}
{{< /code >}}

{{< image border="true" w="360" src="img-001.png" title="表示結果" >}}

