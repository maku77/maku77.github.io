---
title: "Neovim設定例: ヤンク時に選択範囲をハイライト表示する (TextYankPost)"
url: "p/9qyei4z/"
date: "2025-11-01"
tags: ["vim"]
---

Vim/Neovim の **`TextYankPost`** イベントは、「テキストをヤンク（コピー、カットなども含む）した直後」に自動的に呼ばれるイベントです。
例えば y（ヤンク）や d（削除）、c（変更）などレジスタにテキストが書き込まれる操作の後に自動で発火します。​

下記の AutoCmd 設定例では、ヤンク操作の直後に選択範囲を一時的にハイライト表示するようにしています。
これにより、どの部分がヤンクされたかを視覚的に確認できるようになります。

{{< code lang="lua" title="~/.config/nvim/init.lua" >}}
-- 同じグループのオートコマンドが定義されている場合はクリアする
local grp = vim.api.nvim_create_augroup("MyAutocmds", { clear = true })

-- ヤンク時に選択範囲をハイライト表示する
vim.api.nvim_create_autocmd("TextYankPost", {
  group = grp,
  desc = "Highlight on yank",
  callback = function()
    vim.highlight.on_yank({ timeout = 300 })
  end,
})
{{< /code >}}

