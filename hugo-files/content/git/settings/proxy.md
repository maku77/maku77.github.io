---
title: "Git コマンドで使用するプロキシを設定する (http.proxy)"
url: "p/uyzcgd8/"
date: "2020-03-04"
lastmod: "2024-04-11"
tags: ["Git"]
aliases: /git/settings/proxy.html
---

Git のプロキシを設定する
----

会社のオフィスなどのプロキシ環境内から `git` コマンドを使用する場合は、次のようにプロキシサーバーのアドレス（とポート番号）を設定できます。

{{< code lang="console" title="git コマンド用のプロキシ設定" >}}
$ git config --global http.proxy http://proxy.example.com:8080
{{< /code >}}

ちゃんと設定されているかは、`git config --list` コマンド、あるいは設定ファイルを直接見て確認できます。

{{< code lang="console" title="プロキシ設定の確認 (1)" >}}
$ git config --global --list
...
http.proxy=http://proxy.examle.com:8080
{{< /code >}}

{{< code lang="console" title="プロキシ設定の確認 (2)" >}}
$ cat ~/.gitconfig
...
[http]
	proxy = http://proxy.examle.com:8080
{{< /code >}}


Git のプロキシ設定を削除する
----

プロキシ設定を削除するには、`git config --unset` コマンドを使用します。

```console
$ git config --global --unset http.proxy
```

- 参考: [git config で設定を削除する (`config --unset`)](/p/4pad93a/)


Git のプロキシ設定を一時的に無効にする
----

リモートワークなどで、一時的にプロキシ設定を無視して `git` コマンドを使用したい場合は、__`-c`__ オプションで `http.proxy` 設定を次のように空っぽに指定してやります。

```console
$ git -c http.proxy= pull
```

`http.proxy=` の後ろには空であることを示すスペースがあることに注意してください（もっと明示的に `http.proxy=""` としてもOK）。
また、`-c` オプションは、`git` コマンドのグローバルなオプションなので、`git` の直後くらいに入力しないといけないことに注意してください。

