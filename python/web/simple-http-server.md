---
title: "Python で簡易的な HTTP サーバを立てる (SimpleHTTPServer)"
date: "2012-12-09"
---

Python がインストールされた環境であれば、コマンドラインから下記のように実行することで簡易 HTTP サーバを立てることができます。

#### ポート 8000 で HTTP サーバを起動

~~~
$ python -m SimpleHTTPServer 8000
Serving HTTP on 0.0.0.0 port 8000 ...
~~~

`http://localhost:8000/` にアクセスすれば、カレントディレクトリ内のファイルの一覧が表示されます。
LAN 内の一時的なファイル共有サーバとして使用することができます。

