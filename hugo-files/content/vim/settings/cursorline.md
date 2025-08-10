---
title: "Vim でカーソル下の行や列をハイライト表示する (cursorline, cursorcolumn)"
url: "p/osrx94g/"
date: "2009-09-09"
tags: ["vim"]
aliases: [/vim/settings/cursorline.html]
---

下記のように設定しておくと、カーソル行やカーソル列の背景色がハイライトされるようになります。

```vim
:set cursorline    "カーソル行のハイライト
:set cursorcolumn  "カーソル列のハイライト
```

両方とも設定しておくと、ひと目でカーソル位置が分かるようになるのでオススメです。
特に、コーディング用に Vim を使っているようなケースでは、列方向のハイライト (`cursorcolumn`) を有効にしておくと、インデントのずれなどを発見しやすくなります。

背景色を変更したい場合は次のカラーグループを設定します。

```vim
:highlight CursorLine guibg=#0000A0 ctermbg=blue
:highlight CursorColumn guibg=#0000A0 ctermbg=blue
```

