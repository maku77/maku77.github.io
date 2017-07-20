---
title: ■や※などの記号が重なって表示される問題の解決 (ambiwidth)
created: 2012-10-11
---

`encoding` が `utf-8` などに設定されている場合は、■ や ※ などの記号を入力したときに、その次の文字が半角分重なって表示されてしまうことがあります。

このような記号を、全角文字の幅で表示するには、以下のように設定します。

~~~
set ambiwidth=double  "Display double-width symbols properly
~~~

`guifont` を設定すると、`ambiwidth` の設定が `auto` に戻ってしまうようなので、`guifont` を設定している場合は、その後で `ambiwidth` を設定するとよいでしょう。

#### ~/_gvimrc（Windows の場合）

~~~
"Font settings for Windows
if has("gui_win32")
    set guifont=ＭＳ_ゴシック:h10::cSHIFTJIS
    set printfont=ＭＳ_ゴシック:h22:cSHIFTJIS
    set ambiwidth=double  "Display double-width symbols properly
endif
~~~

