---
title: "Hugo サーバーで記事の変更内容が反映されない場合"
date: "2018-05-29"
---

Hugo サーバーを立ち上げて記事の更新をしていると、Web ブラウザをリロードしても変更内容が反映されないことがあります（特に layouts や partials など、共有して使う部分など）。

ページのビルドに成功しているのに変更内容が反映されない場合は、Hugo サーバーのキャッシュを疑ってみるとよいかもしれません。
`hugo server` コマンドを実行するときに、`--ignoreCache` オプションをつけると、キャッシュを使わずに描画されます。

~~~
$ hugo server --ignoreCache
~~~
