---
title: "Vim/Neovim でカーソル位置の行や列をハイライト表示する (cursorline, cursorcolumn)"
url: "p/osrx94g/"
date: "2009-09-09"
lastmod: "2025-11-04"
tags: ["vim"]
changes:
  - 2025-11-04: Neovim の設定例を追加
aliases: [/vim/settings/cursorline.html]
---

カーソル行／カーソル列のハイライト表示
----

Vim/Neovim 内で **`cursorline`** / **`cursorcolumn`** オプションを有効化することで、カーソル行やカーソル列の背景色がハイライトされるようになります。

```vim
:set cursorline    "カーソル行をハイライト
:set cursorcolumn  "カーソル列をハイライト
```

両方とも設定しておくと、ひと目でカーソル位置が分かるようになるのでオススメです。
特に、コーディングで Vim を使っているようなケースでは、列方向のハイライト (`cursorcolumn`) を有効にしておくと、インデントのずれなどを発見しやすくなります。

背景色を変更したい場合は **`highlight`** コマンドで **`CursorLine`** と **`CursorColumn`** というカラーグループの色を設定します。

```vim
:highlight CursorLine guibg=#0000A0 ctermbg=blue
:highlight CursorColumn guibg=#0000A0 ctermbg=blue
```


設定例
----

{{< code lang="lua" title="Neovim (init.lua) の場合" >}}
-- カーソル行／カーソル列を強調表示
vim.opt.cursorline = true
vim.opt.cursorcolumn = true

-- カーソル行／カーソル列の背景色
vim.api.nvim_set_hl(0, "CursorLine", { bg = "#0044ee", ctermbg = "blue" })
vim.api.nvim_set_hl(0, "CursorColumn", { bg = "#002299", ctermbg = "blue" })
{{< /code >}}

{{< code lang="vim" title="Vim (.vimrc) / Neovim (init.vim) の場合" >}}
" カーソル行／カーソル列を強調表示
set cursorline
set cursorcolumn

" カーソル行／カーソル列の背景色
highlight CursorLine guibg=#0044ee ctermbg=blue
highlight CursorColumn guibg=#002299 ctermbg=blue
{{< /code >}}

