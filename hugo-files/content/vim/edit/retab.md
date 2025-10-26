---
title: "Vim/Neovim ですでに入力されているタブをスペースに変換する (:retab)"
url: "p/w4qm7ok/"
date: "2009-12-07"
tags: ["vim"]
aliases: ["/vim/edit/retab.html"]
---

[`expandtab` オプション](/p/8okf7d3/)が設定されていると、TAB キーを押したときに、タブ文字の代わりに `tabstop` で設定した数のスペースが挿入されます。
すでに入力されているタブ文字を、スペースに変換するには、`expandtab` モードを有効にしたうえで、__`:retab`__ コマンドを実行します。

{{< code lang="vim" title="例: タブ文字を 4 文字分のスペースに置換する" hl_lines="3" >}}
:set expandtab
:set tabstop=4
:retab
{{< /code >}}

参考
----

- [タブ文字の設定 (`tabstop`, `expandtab`, `softtabstop`)](/p/8okf7d3/)

