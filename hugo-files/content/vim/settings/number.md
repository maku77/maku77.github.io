---
title: "Vim で行番号を表示する (number)"
url: "p/t8o6tum/"
date: "2009-09-09"
tags: ["vim"]
aliases: /vim/settings/number.html
---

行番号の表示・非表示 (number, nonumber)
----

Vim で各行の行頭に行番号を表示するには、__`:set number`__ を実行します。
逆に、行番号を非表示にするには、__`:set nonumber`__ を実行します（デフォルト）。

常にこの設定を有効化しておきたいときは、設定ファイル `~/.vimrc`（NeoVim なら `init.vim`）に次のように記述しておきます。

{{< code lang="vim" title="~/.vimrc" >}}
set number  "Print the line number in front of each line.
{{< /code >}}


行番号のカラー設定 (highlight LineNr)
----

行番号の背景色や文字色を変更するには __`highlight (hi)`__ コマンドで、カラーグループ __`LineNr`__ を設定します。

```vim
:highlight LineNr guifg=Black guibg=DarkGray ctermfg=Black ctermbg=DarkGray
```

