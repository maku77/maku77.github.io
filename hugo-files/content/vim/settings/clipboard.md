---
title: "Vim/Neovim で OS のクリップボードとヤンクレジスターを連動させる (clipboard)"
url: "p/nnhefs3/"
date: "2024-06-11"
tags: ["vim"]
---

Vim/Neovim のデフォルト設定では、__`y`__ でテキストをヤンク（コピー）したときに、OS のクリップボードにテキストの内容がコピーされません。
別のアプリ上で `Ctrl+C` でコピーした内容も、Vim の __`p`__ でペーストすることができません。
これでは不便なので、多くの場合は次のように OS のクリップボードと連動させます。

{{< code lang="vim" title="Vim (~/.vimrc) や Neovim (init.vim) の場合" >}}
" OS のクリップボードと連動させる
set clipboard+=unnamed,unnamedplus
{{< /code >}}

{{< code lang="lua" title="Neovim (init.lua) の場合" >}}
-- OS のクリップボードと連動させる
vim.opt.clipboard:append({ "unnamed", "unnamedplus" })
{{< /code >}}

細かい説明をすると、`unnamed` というフラグは `*` レジスターとの連動、`unnamedplus` というフラグは `+` レジスターとの連動を意味するのですが、通常は上記のように両方とも設定しておけば問題ありません。

