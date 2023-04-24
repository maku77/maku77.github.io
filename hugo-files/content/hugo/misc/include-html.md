---
title: "Hugo で Markdown ファイルに記述した HTML コードが削除されてしまう場合"
url: "p/mju5eox/"
date: "2019-12-26"
tags: ["Hugo"]
aliases: /hugo/misc/include-html.html
---

Goldmark 化による弊害
----

Hugo v0.60.0 から Markdown パーサーとして [Goldmark](https://github.com/yuin/goldmark/) が採用され、Markdown の解釈が微妙に変わっています。

- [Now CommonMark Compliant! - Hugo](https://gohugo.io/news/0.60.0-relnotes/)

例えば、今までは `.md` ファイル内に記述した HTML コードはそのまま出力されていたのに、デフォルトで取り除かれるようになっています。
これまでに作成した記事で HTML タグを使用している場合はちょっと困ってしまいます。


Markdown ファイル内に HTML コードを記述できるようにする
----

`.md` ファイルに記述した HTML コードを、これまで通り出力できるようにするには、Hugo の設定ファイルで次のように指定します。

{{< code lang="toml" title="hugo.toml（TOML フォーマットの場合）" >}}
[markup.goldmark.renderer]
  unsafe = true
{{< /code >}}

{{< code lang="yaml" title="hugo.yaml（YAML フォーマットの場合）" >}}
markup:
  goldmark:
    renderer:
      unsafe: true
{{< /code >}}


もちろん、使用する Markdown パーサー自体を旧来の BlackFriday に設定するという方法もあります。

```toml
[markup]
  defaultMarkdownHandler = "blackfriday"
```

ただ、Goldmark の方が高速に動作するようなので、前向きに Goldmark に乗り換えることを考えた方がよいでしょう。

