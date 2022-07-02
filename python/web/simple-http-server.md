---
title: "Python で簡易的な HTTP サーバを立てる (http.server, SimpleHTTPServer)"
url: "p/rr3cmu5/"
permalink: "p/rr3cmu5/"
date: "2012-12-09"
lastmod: "2022-06-26"
tags: ["Python"]
redirect_from:
  - /python/web/simple-http-server
---

Python がインストールされた環境であれば、コマンドラインから下記のように実行することで簡易 HTTP サーバを立てることができます。

#### （Python3 の場合）ポート 8000 で HTTP サーバーを起動

```console
$ python3 -m http.server 8000
Serving HTTP on :: port 8000 (http://[::]:8000/) ...
```

#### （Python2 の場合）ポート 8000 で HTTP サーバーを起動

```console
$ python -m SimpleHTTPServer 8000
Serving HTTP on 0.0.0.0 port 8000 ...
```

`http://localhost:8000/` にアクセスすれば、カレントディレクトリ内のファイルの一覧が表示されます。
開発環境での HTTP 開通テストや、LAN 内の一時的なファイル共有サーバとして使用することができます。

