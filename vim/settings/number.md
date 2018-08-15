---
title: "行番号を表示する (number)"
date: "2009-09-09"
---

各行の行頭に行番号を表示するには、`:set number` を実行します。
逆に、行番号を非表示にするには、`:set nonumber` を実行します（デフォルト）。

~~~
:set number  "Print the line number in front of each line.
:set nonumber
~~~

行番号の背景色、文字色を変更するにはカラーグループ `LineNr` を設定します。

~~~
:highlight LineNr guifg=Black guibg=DarkGray ctermfg=Black ctermbg=DarkGray
~~~

