---
title: "Hugo サーバーで記事の変更内容が反映されない場合"
url: "p/taxh3m7/"
date: "2018-05-29"
tags: ["Hugo"]
aliases: /hugo/command/ignore-cache.html
---

Hugo サーバーを立ち上げて記事の更新をしていると、Web ブラウザをリロードしても変更内容が反映されないことがあります（特に `layouts` や `partials` など、共有して使う部分など）。

ページのビルドに成功しているのに変更内容が反映されない場合は、Hugo サーバーのキャッシュを疑ってみるとよいかもしれません。
`hugo server` コマンドを実行するときに、__`--ignoreCache`__ オプションをつけると、キャッシュを使わずにレンダリングされます。

```console
$ hugo server --ignoreCache
```

