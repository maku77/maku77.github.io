---
title: "Git コマンドで使用するプロキシを設定する (http.proxy)"
date: "2020-03-04"
---

Git のプロキシを設定する
----

企業などのプロキシ環境内から Git コマンドを使用する場合は、次のようにプロキシサーバーのアドレス（とポート番号）を設定できます。

```
$ git config --global http.proxy http://proxy.example.com:8080
```

ちゃんと設定されているか確認しておきます。

```
$ git config --global --list
...
http.proxy=http://proxy.examle.com:8080
```

```
$ cat ~/.gitconfig
...
[http]
	proxy = http://proxy.examle.com:8080
```


Git のプロキシ設定を削除する
----

プロキシ設定を削除するには、`git config --unset` コマンドを使用します。

```
$ git config --global --unset http.proxy
```


Git のプロキシ設定を一時的に無効にする
----

リモートワークなどで、一時的にプロキシ設定を無視して `git` コマンドを使用したい場合は、`-c` オプションで `http.proxy` 設定を次のように空っぽに指定してやります。

```
$ git -c http.proxy= pull
```

`http.proxy=` の後ろには空であることを示すスペースがあることに注意してください（もっと明示的に `http.proxy=""` としてもOK）。
また、`-c` オプションは、`git` コマンドのグローバルなオプションなので、`git` の直後くらいに入力しないといけないことに注意してください。

