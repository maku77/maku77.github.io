---
title: "Git コマンドの長いオプション名を省略して入力する小技"
url: "p/7cf83cy/"
date: "2010-08-15"
lastmod: "2024-04-11"
tags: ["Git"]
aliases: /git/basic/abbreviate-option.html
---

Git のコマンドで __`--`__ で始まるオプションを指定する場合、そのオプション名を省略して指定することができます。
例えば、`--dry-run` オプションは通常次のように指定しますが、

```console
$ git push --dry-run
```

次のように `--dr` と省略して実行することができます。

```console
$ git push --dr
```

`--d` だけだと、`--delete` なのか `--dry-run` なのか判別できないので、一意に決まる長さまでは入力する必要があります。

- 参考: [Git コマンドのエイリアスを作成する (`alias.xxx`)](/p/a59xkpd/)

