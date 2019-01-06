---
title: "プロキシ経由で gem コマンドを利用する"
date: "2015-10-15"
---

`gem` コマンドは、インターネット上のサーバから Ruby のパッケージをダウンロードしますが、プロキシ環境下で接続がうまくいかない場合には下記のようなエラーが発生します。

```
$ gem install pry
ERROR:  Could not find a valid gem 'pry' (>= 0), here is why:
          Unable to download data from https://rubygems.org/
          - no such name (https://rubygems.org/latest_specs.4.8.gz)
```

`gem` コマンドのプロキシ設定は下記のように行います。

gem コマンドのパラメータで指定する方法
----

```
$ gem install <packageName> -p http://proxy.example.com
$ gem install <packageName> -p http://proxy.example.com:port
$ gem install <packageName> -p http://user:pass@proxy.example.com:port
```

環境変数で指定する方法
----

環境変数 `http_proxy` にプロキシの設定を行っておけば、`gem install` の際にプロキシを指定する必要がなくなります。

