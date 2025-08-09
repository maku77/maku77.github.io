---
title: "Vim で外部の ex スクリプトを実行する"
url: "p/rocrnht/"
date: "2007-02-14"
tags: ["vim"]
aliases: ["/vim/advanced/run-ex-script.html"]
---

Vim エディタでは、複数の ex コマンドを記述したファイル（ex スクリプト）を読み込んで、現在編集中のファイルに対して実行することができます。
下記の ex スクリプト (`replace.ex`) は、現在編集中のテキストのファイルの `AAA` を `BBB` に置換し、保存して終了します。

{{< code title="ex スクリプトの例 (replace.ex)" >}}
%s/AAA/BBB/g
wq
{{< /code >}}

この ex スクリプトを実行するには、**`:source`** コマンドを使って以下のようにします（省略形は `:so`** です）。

```vim
:so replace.ex
```

Linux には同様のことを行う単独のコマンド `ex` が標準で付属しています。
Vim を起動しなくても、次のようにして ex スクリプトを任意のファイルに対して実行できます。

```console
$ ex input.txt < replace.ex
```

