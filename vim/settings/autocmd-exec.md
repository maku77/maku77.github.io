---
title: "編集中のスクリプト（Ruby や Python など）をショートカットキーで実行する"
created: 2017-09-04
description: "ここでは、Ruby や Python などのスクリプトを編集しているときに、<F5> キー一発で実行できるようにする方法を説明します。"
---

Vim の `:autocmd` コマンドを使用すると、編集中のファイルの種類に応じた設定を行うことができます。
これと `:map` コマンドを組み合わせて使用することで、単一のショートカットキー（例えば `<F5>` キー）で編集中のスクリプトを、適切な処理系（`python` コマンドなど）で実行することができるようになります。

#### .vimrc（Windows の場合は _vimrc）

~~~
" File type detection is On.
filetype on

augroup vimrc
    " Remove all autocommands in this group
    autocmd!

    " <F5> key execution
    autocmd FileType java        nmap <buffer> <F5> :!javac %<CR>
    autocmd FileType javascript  nmap <buffer> <F5> :!node %<CR>
    autocmd FileType php         nmap <buffer> <F5> :!php %<CR>
    autocmd FileType python      nmap <buffer> <F5> :!python %<CR>
    autocmd FileType ruby        nmap <buffer> <F5> :!ruby %<CR>
    autocmd FileType go          nmap <buffer> <F5> :!go run %<CR>
    autocmd FileType groovy      nmap <buffer> <F5> :!groovy %<CR>
    autocmd FileType vim         nmap <buffer> <F5> :source %<CR>
augroup END
~~~

上記の例では、各言語のソースコードを編集中に `<F5>` キーを押すことで、次のようにコマンドを実行するように設定しています。

* Java ファイル → `javac ファイル名`（java の場合はとりあえずコンパイルだけ）
* Javascript ファイル → `node ファイル名`
* PHP ファイル → `php ファイル名`
* Python ファイル → `python ファイル名`
* Ruby ファイル → `ruby ファイル名`
* Go ファイル → `go run ファイル名`
* Groovy ファイル → `groovy ファイル名`
* Vim ファイル → Vim 自身の `:source` コマンドで実行

これら以外の言語に対しても、同じように追加していくことができます。

ちなみに、`nmap` によるキーマップ設定では、`<buffer>` というオプションを指定することで、キーマップ設定がカレントバッファにのみ反映されるようにしています。

