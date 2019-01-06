---
title: "フォント設定ダイアログを開いてフォントを設定する"
date: "2008-05-12"
---

Windows や Mac のフォント設定ダイアログを開いて、GVim のフォントを設定したい場合は、

~~~ vim
:set guifont=*
~~~

と実行します。
ダイアログでフォントの設定を行った後は、

~~~ vim
set guifont?
~~~

として `guifont` オプションに具体的にどのような設定値が格納されているかを確認することができます。
この設定値を、設定ファイル (`.gvimrc`) に記述しておけば、次回の起動時からは自動的にそのフォント設定が反映されます。

#### ~/.gvimrc

~~~ vim
"Font settings for Windows and MacOSX
if has("win32") || has("win64") || has("win32unix")
    set guifont=ＭＳ_ゴシック:h14::cSHIFTJIS
    set ambiwidth=double  "Display double-width symbols properly
elseif has("macunix")
    set guifont=Menlo\ Regular:h14
endif
~~~

GVim ウィンドウ専用の設定なので、`.vimrc` ファイルではなく、`.gvimrc` ファイルに設定することに注意してください。

