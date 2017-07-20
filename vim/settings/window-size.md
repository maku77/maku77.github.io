---
title: GVim 起動時のウィンドウの幅、高さを設定する (columns, lines)
created: 2007-11-14
---

GVim を起動したときのウィンドウのサイズは、`columns` オプショ（横幅）と、`lines` オプション（高さ）で指定します。
単位は半角文字の数です。
起動時に読み込まれる `~/.gvimrc` に記述してください（Windows の場合は `%HOME%\_gvimrc`）。

~~~
set columns=120  "横幅
set lines=60    "高さ
~~~

