---
title: "Vim/Neovim で行番号を表示する (number)"
url: "p/t8o6tum/"
date: "2009-09-09"
lastmod: "2025-11-04"
tags: ["vim"]
changes:
  - 2025-11-04: Neovim の設定例を追加
aliases: /vim/settings/number.html
---

行番号の表示・非表示
----

Vim/Neovim で各行の行頭に行番号を表示するには、__`:set number`__ を実行します。
逆に、行番号を非表示にするには、__`:set nonumber`__ を実行します（デフォルト）。

{{< code lang="vim" title="行番号を表示する" >}}
:set number    " 行番号を表示する
:set nonumber  " 行番号を表示しない (default)
{{< /code >}}

行番号の背景色や文字色を変更するには __`highlight (hi)`__ コマンドで、__`LineNr`__ ハイライト・グループを設定します。
カーソル行の行番号を別の色で表示したいときは、__`CursorLineNr`__ ハイライト・グループも設定します。

{{< code lang="vim" title="行番号の背景色／文字色を変更する" >}}
:hi LineNr guifg=#cc2244 guibg=#551100 ctermfg=black ctermbg=gray
:hi CursorLineNr guifg=#dd3355 guibg=#771100 ctermfg=black ctermbg=gray
{{< /code >}}


設定例
----

{{< code lang="vim" title="Vim (.vimrc) / Neovim (init.vim) の場合" >}}
" 行番号を表示する
set number

" 行番号の色
hi LineNr guifg=#cc2244 guibg=#551100 ctermfg=black ctermbg=gray
hi CursorLineNr guifg=#dd3355 guibg=#772211 ctermfg=black ctermbg=gray
{{< /code >}}

{{< code lang="lua" title="Neovim (init.lua) の場合" >}}
-- 行番号を表示する
vim.opt.number = true

-- 行番号の色
vim.api.nvim_set_hl(0, "LineNr", { fg = "#cc2244", bg = "#551100", ctermfg = "black", ctermbg = "gray" })
vim.api.nvim_set_hl(0, "CursorLineNr", { fg = "#dd3355", bg = "#772211", ctermfg = "black", ctermbg = "gray" })
{{< /code >}}

