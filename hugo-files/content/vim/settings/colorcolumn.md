---
title: "Vim/Neovim で80文字目に縦線を表示する (colorcolumn)"
url: "p/ngz6n6i/"
date: "2013-05-25"
lastmod: "2025-11-04"
tags: ["vim"]
changes:
  - 2025-11-04: Neovim の設定例を追加
aliases: [/vim/settings/colorcolumn.html]
---

指定した位置に縦線を表示
----

Vim/Neovim で **`colorcolumn`** オプションを設定すると、指定したカラム位置の背景色が変化します。
結果的にその位置に縦線を入れたように見せることができます。
例えば、80文字目の背景色を灰色にするには、以下のように **`colorcolumn`** を設定します。

{{< code lang="vim" title="80カラム目に縦線を表示する" >}}
:set colorcolumn=80
{{< /code >}}

`colorcolumn` は上記のように絶対位置で指定することもできるし、**`textwidth`** が設定されている場合は、その値からの相対位置（`+N` や `-N` という形）で指定することもできます。
例えば、下記のようにすると、`textwidth` から `+1` の位置、つまり 80 文字目の背景色が変わることになります。

{{< code lang="vim" title="textwidth+1 の位置に縦線を表示" >}}
:set textwidth=79
:set colorcolumn=+1
{{< /code >}}

ここでは `colorcolumn` に一つの値だけを設定していますが、カンマで区切って複数のカラムを指定することができます。
カンマの前後にスペースは入れてはいけません。

{{< code lang="vim" title="textwidth+1 の位置、80カラム目、100カラム目に縦線を表示" >}}
:set colorcolumn=+1,80,100
{{< /code >}}

縦線の色を変えたいときは、**`highlight`** コマンドで **`ColorColumn`** ハイライト・グループの設定を変更します。

{{< code lang="vim" title="縦線の色を変更する" >}}
:highlight ColorColumn guibg=#202020 ctermbg=lightgray
{{< /code >}}


設定例
----

{{< code lang="vim" title="Vim (.vimrc) / Neovim (init.vim) の場合" >}}
" 指定したカラム位置に縦線を表示
set colorcolumn=80,100
highlight ColorColumn guibg=#333300 ctermbg=lightgray
{{< /code >}}

{{< code lang="lua" title="Neovim (init.lua) の場合" >}}
-- 指定したカラム位置に縦線を表示
vim.opt.colorcolumn = "80,100"
vim.api.nvim_set_hl(0, "ColorColumn", { bg = "#333300", ctermbg = "lightgray" })
{{< /code >}}

