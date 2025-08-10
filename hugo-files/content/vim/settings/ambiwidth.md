---
title: "Vim で■や※などの記号が重なって表示される問題の解決 (ambiwidth)"
url: "p/fp2xk7y/"
date: "2012-10-11"
tags: ["vim"]
aliases: [/vim/settings/ambiwidth.html]
---

日本語の Vim 環境において、`encoding` オプションの値が `utf-8` などに設定されている場合、■ や ※ などの記号を入力したときに、その次の文字が半角分重なって表示されてしまうことがあります。
このような記号を正しく全角文字の幅で表示するには、以下のように設定します。

```vim
set ambiwidth=double  "Display double-width symbols properly
```

`guifont` を設定すると、`ambiwidth` の設定が `auto` に戻ってしまうようなので、`guifont` を設定している場合は、その後で `ambiwidth` を設定するとよいでしょう。

{{< code lang="vim" title="~/_gvimrc（Windows の場合）" >}}
"Font settings for Windows
if has("gui_win32")
    set guifont=ＭＳ_ゴシック:h10::cSHIFTJIS
    set printfont=ＭＳ_ゴシック:h22:cSHIFTJIS
    set ambiwidth=double  "Display double-width symbols properly
endif
{{< /code >}}

