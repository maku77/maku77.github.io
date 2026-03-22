---
title: "PHPメモ: パッケージ管理環境 PEAR をインストールする"
url: "/p/fd8cmiq/"
date: "2012-09-22"
tags: ["php"]
aliases: [/php/env/pear.html]
---

**PEAR (PHP Extension and Application Repository)** は、PHP のパッケージ（ライブラリ）管理を行うための環境です。


PEAR のインストール
----

`wget` や `curl` コマンドで `go-pear.phar` スクリプトをダウンロードしてインストールすることができます。

```console
$ curl -O https://pear.php.net/go-pear.phar
$ php go-pear.phar
```

最初はおそらく下記のような警告メッセージが表示されます。

```
WARNING!  The include_path defined in the currently used php.ini does not
contain the PEAR PHP directory you just specified:
</Users/maku/pear/share/pear>
```

指定されたパスを `php.ini` 内の `include_path` に追加することで、PEAR でインストールしたライブラリへのインクルードパスが通るようになります。

{{< code lang="ini" title="php.ini" >}}
...
include_path = ".:/Users/maku/pear/share/pear"
...
{{< /code >}}


XAMPP など pear を付属した統合環境がインストールされている場合
----

XAMPP の統合環境がインストールされていると、そちらで一緒にインストールされた pear コマンドにパスが通っている可能性があります。
新たに手動でインストールした pear コマンドの方を優先的に使用するには、たとえば下記のように設定します。

まず、XAMPP の `pear` コマンドのファイル名を変更し（例えば `__pear` にしてしまう）、新しくインストールした `pear` にパスを通します。

{{< code lang="bash" title="~/.bashrc" >}}
export PATH=$PATH:~/pear/bin
{{< /code >}}

次に、PEAR で管理されているライブラリにインクルードパスを通します。
独立した `/usr/bin/php` コマンドと、XAMPP の `php` コマンドの両方がインストールされている場合は、それぞれの `php.ini` の `include_path` を設定しておく必要があります。

{{< code lang="ini" title="/etc/php.ini（独立 PHP 環境用）" >}}
include_path=".:/Users/maku/pear/share/pear"
{{< /code >}}

{{< code lang="ini" title="/Applications/XAMPP/etc/php.ini（XAMPP 環境用）" >}}
include_path=".:/Applications/XAMPP/xamppfiles/lib/php:/Applications/XAMPP/xamppfiles/lib/php/pear"
↓
include_path=".:/Users/maku/pear/share/pear"
{{< /code >}}

