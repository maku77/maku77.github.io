---
title: "80文字目に縦線を表示する (colorcolumn)"
date: "2013-05-25"
---

指定したカラム位置の背景色を変えることで、縦線を入れたように見せることができます。
例えば、80文字目の背景色を灰色にするには、以下のように **`colorcolumn`** を設定します。

#### ~/.vimrc（80カラム目に縦線を表示）

~~~ vim
set colorcolumn=80
highlight ColorColumn guibg=#202020 ctermbg=lightgray
~~~

`colorcolumn` は上記のように絶対位置で指定することもできるし、**`textwidth`** が設定されている場合は、その値からの相対位置（`+N` や `-N` という形）で指定することもできます。
例えば、下記のようにすると、`textwidth` から `+1` の位置、つまり 80 文字目の背景色が変わることになります。

#### ~/.vimrc（textwidth+1 の位置に縦線を表示）

~~~ vim
set textwidth=79
set colorcolumn=+1
highlight ColorColumn guibg=#202020 ctermbg=lightgray
~~~

ここでは `colorcolumn` に一つの値だけを設定していますが、カンマで区切って複数のカラムを指定することができます。
カンマの前後にスペースは入れてはいけません。

#### ~/.vimrc（textwidth+1 の位置、80カラム目、100カラム目に縦線を表示）

~~~ vim
set colorcolumn=+1,80,100
~~~

