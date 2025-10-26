---
title: "Vim/Neovim で行番号を表示する (set number, vim.opt.number)"
url: "p/t8o6tum/"
date: "2009-09-09"
tags: ["vim"]
aliases: /vim/settings/number.html
---

行番号の表示・非表示
----

### Vim の場合 (set number)

Vim で各行の行頭に行番号を表示するには、__`:set number`__ を実行します。
逆に、行番号を非表示にするには、__`:set nonumber`__ を実行します（デフォルト）。
常にこの設定を有効化しておきたいときは、設定ファイル `~/.vimrc`（Neovim なら `~/.config/nvim/init.vim`）に次のように記述しておきます。

{{< code lang="vim" title="~/.vimrc" >}}
set number  "Print the line number in front of each line.
{{< /code >}}

### Neovim の場合 (vim.opt.number)

Neovim の `init.lua` で行番号の表示・非表示の設定を行う場合は、__`vim.opt.number`__ をセットします。

{{< code lang="lua" title="~/.config/nvim/init.lua" >}}
vim.opt.number = true  -- 行番号を表示する
{{< /code >}}


行番号のカラー設定 (highlight LineNr)
----

Vim で行番号の背景色や文字色を変更するには __`highlight (hi)`__ コマンドで、カラーグループ __`LineNr`__ を設定します。

```vim
:highlight LineNr guifg=Black guibg=DarkGray ctermfg=Black ctermbg=DarkGray
```

