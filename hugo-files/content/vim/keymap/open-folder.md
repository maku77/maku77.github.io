---
title: "Vim のキーマップ例: ショートカットキーでカレントディレクトリを Windows エクスプローラーや Mac の Finder で開く"
url: "p/tqmr4od/"
date: "2020-02-29"
lastmod: "2024-06-21"
tags: ["neovim", "vim"]
changes:
  - 2024-06-21: Neovim の設定方法を追加。
aliases: /vim/keymap/open-folder.html
---

Vim/Neovim で次の設定を行っておくと、現在編集中のファイルが格納されたディレクトリをショートカットキー（ここでは `F12` キー）一発で開くことができます。
Windows の場合は `start` コマンドを使って「エクスプローラー」を開き、Mac の場合は `open` コマンドを使って「Finder」を開くようにしています。

Neovim（Lua スクリプト）の場合
----

{{< code lang="lua" title="~/.config/nvim/init.lua" >}}
-- 編集中ファイルが格納されたディレクトリを開く関数
function open_current_dir()
  local command
  if vim.fn.has("mac") == 1 then
    command = "open"
  elseif vim.fn.has("win32") == 1 or vim.fn.has("win64") == 1 then
    command = "start"
  else
    print("Could not open the directory (unsupported OS)")
    return
  end

  local dir_path = vim.fn.expand("%:p:h")
  vim.fn.system(command .. " " .. dir_path)
end

-- F12 キーにマップする
vim.keymap.set("n", "<F12>", open_current_dir, {
  silent = true,
  desc = "Open current directory in file manager"
})
{{< /code >}}


Vim（Vim スクリプト）の場合
----

{{< code lang="vim" title="~/.vimrc" >}}
" 編集中ファイルのディレクトリを Explorer や Finder で開く
if has("win32") || has("win64") || has("win32unix")
    nmap <F12> :silent ! start %:h<CR>
elseif has("macunix")
    nmap <F12> :silent ! open %:h<CR>
endif
{{< /code >}}

