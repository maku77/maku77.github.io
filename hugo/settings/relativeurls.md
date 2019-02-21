---
title: "リンクをページからの相対パスで出力するようにする (relativeurls)"
date: "2019-02-21"
---

サイト内のページへのリンクを `/sec1/sec2/` のように記述しておくと、デフォルトではそのまま `/sec1/sec2/` へのリンクとして出力されます（つまり、baseURL からの相対パスになります）。

Hugo の設定ファイル `config.toml`（あるいは `config.yaml`）で **`relativeurls = true`** という設定を追加しておくと、各リンクが現在のページからの相対パスで出力されるようになります。

#### config.toml の例

~~~ toml
baseURL = "https://example.com/"
relativeurls = true
languageCode = "ja-jp"
title = "サンプル Web サイト"
~~~

例えば、`/content/posts/sample.md` というコンテンツファイル内で `/about/` というリンクを記載しておくと、出力されるリンクのパスは `../about/` になります。

