---
title: "Vim/Neovim でビジュアルモード中に連続インデントできるようにする"
url: "p/hoihkfy/"
date: "2013-05-26"
lastmod: "2025-11-04"
tags: ["vim"]
changes:
  - 2025-11-04: "Neovim/Lua の設定例を追加"
aliases: /vim/settings/visual-indent.html
---

Vim/Neovim でビジュアルモードで複数行を選択してインデントするには、`>>` や `<<` を使いますが、このコマンドを入力すると、ビジュアルモードを抜けてしまうので、連続してインデントを行いたい場合にちょっと面倒です（何回インデントすればよいか分かっていれば `3>>` のように回数を指定することはできますが）。

下記のような設定を入れておくと、ビジュアルモード中のインデントを、__`>`__ あるいは __`<`__ だけで行うことができるようになります。
さらに、このコマンドを入力した後も、ビジュアルモードを抜けずにキープしてくれるので、行選択したまま連続してインデントを行うことができます。

{{< code lang="vim" title="Vim (.vimrc, init.vim) の場合" >}}
" Reselect visual block after indent/outdent
vnoremap < <gv
vnoremap > >gv
{{< /code >}}

{{< code lang="lua" title="Neovim/Lua (init.lua) の場合" >}}
-- Reselect visual block after indent/outdent
vim.keymap.set("v", "<", "<gv")
vim.keymap.set("v", ">", ">gv")
{{< /code >}}

同様に、[選択範囲の自動インデントを行う `=` コマンド](/p/pxpgasg/) にも適用できます。

{{< code lang="vim" title="Vim (.vimrc, init.vim) の場合" >}}
" Stay visual mode after formatting code
vnoremap = =gv
{{< /code >}}

{{< code lang="lua" title="Neovim/Lua (init.lua) の場合" >}}
-- Stay visual mode after formatting code
vim.keymap.set("v", "=", "=gv")
{{< /code >}}


参考
----

- [インデント用のスペースを入力する（シフトコマンド） (`>>`, `<<`, `Ctrl-T`, `Ctrl-D`)](/p/i2m4nqt/)

