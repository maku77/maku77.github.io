---
title: "GVim 起動時のウィンドウの幅、高さを設定する (columns, lines)"
url: "p/syn3cyx/"
date: "2007-11-14"
tags: ["vim"]
aliases: [/vim/settings/window-size.html]
---

GVim を起動したときのウィンドウのサイズは、**`columns`** オプション（横幅）と、**`lines`** オプション（高さ）で指定します。
単位は半角文字の数です。
起動時に読み込まれる `~/.gvimrc` に記述してください（Windows の場合は `%HOME%\_gvimrc`）。

```vim
set columns=120  "横幅
set lines=60     "高さ
```

