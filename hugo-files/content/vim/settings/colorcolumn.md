---
title: "Vim で80文字目に縦線を表示する (colorcolumn)"
url: "p/ngz6n6i/"
date: "2013-05-25"
tags: ["vim"]
aliases: [/vim/settings/colorcolumn.html]
---

指定したカラム位置の背景色を変えることで、縦線を入れたように見せることができます。
例えば、80文字目の背景色を灰色にするには、以下のように **`colorcolumn`** を設定します。

{{< code lang="vim" title="~/.vimrc（80カラム目に縦線を表示）" >}}
set colorcolumn=80
highlight ColorColumn guibg=#202020 ctermbg=lightgray
{{< /code >}}

`colorcolumn` は上記のように絶対位置で指定することもできるし、**`textwidth`** が設定されている場合は、その値からの相対位置（`+N` や `-N` という形）で指定することもできます。
例えば、下記のようにすると、`textwidth` から `+1` の位置、つまり 80 文字目の背景色が変わることになります。

{{< code lang="vim" title="~/.vimrc（textwidth+1 の位置に縦線を表示）" >}}
set textwidth=79
set colorcolumn=+1
highlight ColorColumn guibg=#202020 ctermbg=lightgray
{{< /code >}}

ここでは `colorcolumn` に一つの値だけを設定していますが、カンマで区切って複数のカラムを指定することができます。
カンマの前後にスペースは入れてはいけません。

{{< code lang="vim" title="~/.vimrc（textwidth+1 の位置、80カラム目、100カラム目に縦線を表示）" >}}
set colorcolumn=+1,80,100
{{< /code >}}

