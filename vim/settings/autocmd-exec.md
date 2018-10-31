---
title: "編集中のスクリプト（Ruby や Python など）をショートカットキーで実行する"
date: "2017-09-04"
description: "ここでは、Ruby や Python などのスクリプトを編集しているときに、<F5> キー一発で実行できるようにする方法を説明します。"
---

Vim の `:autocmd` コマンドを使用すると、編集中のファイルの種類に応じた設定を行うことができます。
これと `:map` コマンドを組み合わせて使用することで、単一のショートカットキー（例えば <kbd>F5</kbd> キー）で編集中のスクリプトを、適切な処理系（`python` コマンドなど）で実行することができるようになります。

競技プログラミングなどでは、現在編集中のソースコードをさくっとコンパイルして、main 関数の実行結果をさくっと表示できると便利です。

#### .vimrc（Windows の場合は _vimrc）

~~~ vim
" File type detection is On.
filetype on

augroup vimrc
    " Remove all autocommands in this group
    autocmd!

    " <F5> key execution
    autocmd FileType java        nmap <buffer> <F5> :!javac -encoding UTF-8 % && java %<<CR>
    autocmd FileType javascript  nmap <buffer> <F5> :!node %<CR>
    autocmd FileType php         nmap <buffer> <F5> :!php %<CR>
    autocmd FileType python      nmap <buffer> <F5> :!python %<CR>
    autocmd FileType ruby        nmap <buffer> <F5> :!ruby %<CR>
    autocmd FileType go          nmap <buffer> <F5> :!go run %<CR>
    autocmd FileType groovy      nmap <buffer> <F5> :!groovy %<CR>
    autocmd FileType vim         nmap <buffer> <F5> :source %<CR>
augroup END
~~~

<div class="note">
<code>nmap</code> によるキーマップ設定では、<code>&lt;buffer&gt;</code> というオプションを指定することで、キーマップ設定がカレントバッファにのみ反映されるようになります。
</div>

上記の例では、各言語のソースコードを編集中に `<F5>` キーを押すことで、次のようにコマンドを実行するように設定しています。

* Java ファイル → `javac -encoding UTF-8 ファイル名` でビルドし、さらに `java` コマンドでそのクラスの main メソッドを起動。
* Javascript ファイル → `node ファイル名` を実行。
* PHP ファイル → `php ファイル名` を実行。
* Python ファイル → `python ファイル名` を実行。
* Ruby ファイル → `ruby ファイル名` を実行。
* Go ファイル → `go run ファイル名` を実行。
* Groovy ファイル → `groovy ファイル名` を実行。
* Vim ファイル → Vim 自身の `:source` コマンドで実行。

これら以外の言語に対しても、同じように追加していくことができます。
下記は、さらに C++ プログラム用に `clang++` コマンドを使用してコンパイル＆実行できるようにした例です。

~~~ vim
augroup vimrc
    " ...省略...
    autocmd FileType c,cpp  nmap <buffer> <F5> :DoClang<CR>
augroup END

" C/C++ コードをコンパイル＆実行する関数
command! DoClang call s:DoClang()
function! s:DoClang()
    if has("win32") || has("win64") || has("win32unix")
        :!clang++ % -o %<.exe && %<.exe
    else
        :!clang++ % -o %<.out && ./%<.out
    endif
endfunction
~~~

上記の例では、`autocmd` の末尾に直接実行するコマンドを記述するのではなく、`DoClang` という独自定義の関数を呼び出してコンパイルと実行を行うようにしています。

