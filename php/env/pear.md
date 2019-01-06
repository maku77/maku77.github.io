---
title: "パッケージ管理環境 PEAR をインストールする"
date: "2012-09-22"
---

**PEAR (PHP Extension and Application Repository)** は、PHP のパッケージ（ライブラリ）管理を行うための環境です。


PEAR のインストール
----

`wget` や `curl` コマンドで `go-pear.phar` スクリプトをダウンロードしてインストールすることができます。

~~~
$ curl -O http://pear.php.net/go-pear.phar
$ php go-pear.php
~~~

最初はおそらく下記のような警告メッセージが表示されます。

~~~
WARNING!  The include_path defined in the currently used php.ini does not
contain the PEAR PHP directory you just specified:
</Users/maku/pear/share/pear>
~~~

指定されたパスを `php.ini` 内の `include_path` に追加することで、PEAR でインストールしたライブラリへのインクルードパスが通るようになります。

#### php.ini

~~~
...
include_path = ".:/Users/maku/pear/share/pear"
...
~~~


XAMPP など pear を付属した統合環境がインストールされている場合
----

XAMPP の統合環境がインストールされていると、そちらで一緒にインストールされた pear コマンドにパスが通っている可能性があります。
新たに手動でインストールした pear コマンドの方を優先的に使用するには、たとえば下記のように設定します。

まず、XAMPP の `pear` コマンドのファイル名を変更し（例えば `__pear` にしてしまう）、新しくインストールした `pear` にパスを通します。

#### ~/.bashrc

~~~ bash
export PATH=$PATH:~/pear/bin
~~~

次に、PEAR で管理されているライブラリにインクルードパスを通します。
独立した `/usr/bin/php` コマンドと、XAMPP の `php` コマンドの両方がインストールされている場合は、それぞれの `php.ini` の `include_path` を設定しておく必要があります。

#### /etc/php.ini（独立 PHP 環境用）

~~~
include_path=".:/Users/maku/pear/share/pear"
~~~

#### /Applications/XAMPP/etc/php.ini（XAMPP 環境用）

~~~
include_path=".:/Applications/XAMPP/xamppfiles/lib/php:/Applications/XAMPP/xamppfiles/lib/php/pear"
↓
include_path=".:/Users/maku/pear/share/pear"
~~~

