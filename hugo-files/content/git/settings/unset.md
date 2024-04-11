---
title: "git config で設定を削除する (config --unset)"
url: "p/4pad93a/"
date: "2010-07-17"
lastmod: "2024-04-11"
tags: ["Git"]
aliases: /git/settings/unset.html
---

`git config` で設定した値を削除したい場合は、以下のように __`--unset`__ オプションで設定項目を指定します。

{{< code lang="console" title="例: user.email のローカル設定を削除する" >}}
$ git config --local --unset user.email
{{< /code >}}

設定のスコープを表すオプション (`--local` / `--global` / `--system`) も同時に指定するようにしてください。
スコープを省略するとデフォルトで local スコープの設定項目の削除になります。

- 参考: [Git 設定のスコープ (`local`/`global`/`system`) を理解する](/p/af7q7n3/)

