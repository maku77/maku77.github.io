---
title: "カレントディレクトリを Windows エクスプローラーで開く"
date: "2018-10-22"
---

Vim で任意のファイルを開いているときに、下記のように入力すると、そのファイルのあるディレクトリを Windows エクスプローラーで開くことができます。

~~~
:silent ! start %:h
~~~

使用頻度が高ければ、下記のようにキーマッピングしておけば、<kbd>F12</kbd> キーを押すだけで Windows エクスプローラーを開くことができるようになります。

#### ~/.vimrc

~~~
nmap <F12> :silent ! start %:h<CR>
~~~

