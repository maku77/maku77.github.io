---
title: "Vim/Neovim でインデント（シフトコマンド）を設定する (shiftwidth, shiftround)"
url: "p/b5o6ksu/"
date: "2007-02-20"
lastmod: "2024-06-10"
tags: ["vim"]
changes:
  - 2024-06-10: Neovim の設定方法を追加
aliases: /vim/settings/indent.html
---

シフトコマンドで挿入／削除するスペースの量を設定する
----

__`shiftwidth`__ オプションに設定した値は、`>>` コマンドなどで行頭に挿入するスペースの数を示します。

```vim
:set shiftwidth=4  " デフォルトは 8
```

タブストップ (`tabstop`) と混同しがちなので注意してください。
タブストップは、タブ文字を入力した場合に表示位置をどこへずらすかの基準を指定します。
シフトコマンド (`>>`) に効いてくる値はあくまで `shiftwidth` の方です。


シフトしたときに shiftwidth の値の倍数になるようにスペースを挿入する
----

シフトコマンド（`>>` など）を実行すると、行の先頭に `shiftwidth` で設定した数だけスペースが挿入されます。
このとき、すでに行頭に何文字かのスペースが存在する場合に、スペース数が `shiftwidth` の倍数になるように調整してスペースを挿入したい場合は、次のように __`shiftround`__ を有効化します（デフォルトはオフ (`noshiftround`) です）。

{{< code lang="vim" title="Vim（/.vimrc の場合）" >}}
:set shiftround  "シフトコマンドでのインデント量を丸める
{{< /code >}}

{{< code lang="lua" title="Neovim（~/.config/nvim/init.lua の場合）" >}}
vim.opt.shiftround = true  -- シフトコマンドでのインデント量を丸める
{{< /code >}}

ほとんどの場合は、行頭からのインデント量は 2 の倍数とか 4 の倍数とかに揃えておきたいはずなので、`shiftround` はセットしておくことをお勧めします。


参考
----
- [自動インデントモードを有効にする (`autoindent`, `smartindent`, `cindent`)](/p/oe94dkh/)
- [インデント用のスペースを入力する（シフトコマンド） (`>>`, `<<`, `Ctrl-T`, `Ctrl-D`)](/p/i2m4nqt/)

