---
title: "Linuxメモ: Linux でリダイレクトによってファイルが上書きされてしまうのを防ぐ (`set -o noclobber`)"
url: "p/jw5xt77/"
date: "2005-05-26"
tags: ["linux"]
aliases: /linux/basic/avoid-overridden-by-redirect.html
---

例えば Linux で下記のように標準出力をリダイレクトしようとしたときに、すでに出力先のファイルが存在する場合は、そのファイルの内容が上書きされてしまいます。

{{< code lang="console" title="例: hoge.txt が上書きされてしまう" >}}
$ echo Hello > hoge.txt
{{< /code >}}

こういったリダイレクトによるファイルの上書きを抑制するには、シェルオプションの **`noclobber`** を有効化します（bash と zsh で共通です）。
有効化後に `hoge.txt` が上書きされるような処理を実行すると、以下のようにエラーになります。

{{< code lang="console" title="例: hoge.txt が上書きされてしまう" >}}
$ set -o noclobber 
$ echo Hello > hoge.txt
zsh: file exists: hoge.txt
{{< /code >}}

シェルオプションは、`set -o` で有効化、`set +o` で無効化できます。
bash では `~/.bashrc`、zsh では `~/.zshrc` に設定を追加しておくとよいでしょう。

{{< code lang="bash" title="~/.zshrc" >}}
set -o noclobber  # noclobberを有効化（既存ファイルの上書きを防止）
set +o noclobber  # noclobberを無効化（既存ファイルの上書きを許可）（デフォルト）
{{< /code >}}

ちなみに、現在のすべてのシェルオプションの設定を確認するには以下のようにします。

```console
$ set -o  # on, off 表示
$ set +o  # 設定コマンド表示
```

