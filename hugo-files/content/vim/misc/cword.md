---
title: "Vim でカーソル位置の単語を取得する (<cword>)"
url: "p/f448waf/"
date: "2011-06-06"
tags: ["vim"]
---

Vim 内で、カーソル位置の単語は **`<cword>`** で参照することができます。

{{< code lang="vim" title="例: カーソル位置の単語を表示" >}}
:!echo <cword>
{{< /code >}}

{{< code lang="vim" title="例: F4 キーでカーソル位置の単語を検索できるようにする" >}}
nnoremap <F4> :grep <cword> ./*
{{< /code >}}

