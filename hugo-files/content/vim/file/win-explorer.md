---
title: "Vim でカレントディレクトリを Windows エクスプローラーで開く"
url: "p/s8wxbon/"
date: "2018-10-22"
tags: ["vim"]
aliases: ["/vim/file/win-explorer.html"]
---

Vim で任意のファイルを開いているときに、下記のように入力すると、そのファイルのあるディレクトリを Windows エクスプローラーで開くことができます。

```
:silent ! start %:h
```

使用頻度が高ければ、下記のようにキーマッピングしておけば、<kbd>F12</kbd> キーを押すだけで Windows エクスプローラーを開くことができるようになります。

{{< code title="~/.vimrc" >}}
nmap <F12> :silent ! start %:h<CR>
{{< /code >}}

