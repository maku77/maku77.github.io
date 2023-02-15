---
title: "Hugo でリンクをページからの相対パスで出力するようにする (relativeurls)"
url: "p/32n9scv/"
date: "2019-02-21"
tags: ["Hugo"]
aliases: /hugo/settings/relativeurls.html
---

サイト内のページへのリンクを `/sec1/sec2/` のように記述しておくと、デフォルトではそのまま `/sec1/sec2/` へのリンクとして出力されます（つまり、`baseURL` からの相対パスになります）。

Hugo の設定ファイル `config.toml`（あるいは `config.yaml`）で __`relativeurls = true`__ という設定を追加しておくと、各リンクが現在のページからの相対パスで出力されるようになります。

{{< code lang="toml" title="config.toml の例" >}}
baseURL = "https://example.com/"
relativeurls = true
languageCode = "ja-jp"
title = "サンプル Web サイト"
{{< /code >}}

例えば、`/content/posts/sample.md` というコンテンツファイル内に `/about/` というリンクを記載しておくと、出力されるリンクのパスは `../about/` になります。

