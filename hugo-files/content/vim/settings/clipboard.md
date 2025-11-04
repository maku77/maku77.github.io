---
title: "Vim/Neovim で OS のクリップボードを連動させる (clipboard, unnamed, unnamedplus)"
url: "p/nnhefs3/"
date: "2024-06-11"
lastmod: "2025-11-04"
tags: ["vim"]
changes:
  - 2025-11-04: "unnamed/unnamedplus の説明を追加"
---

Vim/Neovim のデフォルト設定では、__`y`__ でテキストをヤンク（コピー）したときに、OS のクリップボードにテキストの内容がコピーされません。
別のアプリ上で `Ctrl+C` でコピーした内容も、Vim の __`p`__ でペーストすることができません。
これでは不便なので、多くの場合は次のように OS のクリップボードと連動させます。

{{< code lang="vim" title="Vim (.vimrc) や Neovim (init.vim) の場合" >}}
" OS のクリップボードと連動させる
set clipboard+=unnamed,unnamedplus
{{< /code >}}

{{< code lang="lua" title="Neovim (init.lua) の場合" >}}
-- OS のクリップボードと連動させる
vim.opt.clipboard:append({ "unnamed", "unnamedplus" })
{{< /code >}}

細かい説明をすると、`unnamed` というフラグは __`*`__ レジスターとの連動、`unnamedplus` というフラグは __`+`__ レジスターとの連動を意味するのですが、通常は上記のように両方とも設定しておけば問題ありません。

- **unnamed（`*` レジスター）** ... 主に Linux OS においてマウスで選択したテキスト（選択クリップボード）の内容を、Vim の `p` で貼り付けられるようにします。
- **unnamedplus（`+` レジスター）** ... 一般的な OS における `Ctrl+C` 操作でコピーしたテキスト（通常のクリップボード）の内容を、Vim の `p` で貼り付けられるようにします。また、Vim 側で `y` でヤンクした内容が、OS 側のクリップボードにコピーされるようになります。

