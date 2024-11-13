---
title: "Vim で編集中のスクリプト（Ruby や Python など）をショートカットキーで実行する"
url: "p/nwqjyx8/"
date: "2017-09-04"
tags: ["vim"]
description: "ここでは、Ruby や Python などのスクリプトを編集しているときに、<F5> キー一発で実行できるようにする方法を説明します。"
aliases: /vim/settings/autocmd-exec.html
---

Vim の **`:autocmd`** コマンドを使用すると、編集中のファイルの種類に応じた設定を行うことができます。

- 参考: [`autocmd` で自動コマンドを登録する](/p/rj6oatw/)

これと **`:map`** コマンドを組み合わせて使用することで、単一のショートカットキー（例えば `F5` キー）で編集中のスクリプトを、適切な処理系（`python` コマンドなど）で実行することができるようになります。
競技プログラミングなどでは、現在編集中のソースコードをさくっとコンパイルして、main 関数の実行結果をさくっと表示できると便利です。

{{< code lang="vim" title=".vimrc（Windows の場合は _vimrc）" >}}
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
{{< /code >}}

{{% note %}}
上記のように、`nmap` によるキーマップ設定時に `<buffer>` オプションを指定すると、キーマップ設定がカレントバッファにのみ反映されるようになります。
{{% /note %}}

上記の例では、各言語のソースコードを編集中に `<F5>` キーを押すことで、次のような処理を実行するように設定しています。

| 編集中のファイルのタイプ | 実行する処理 |
| ---- | ---- |
| Java ファイル | `javac -encoding UTF-8 ファイル名` でビルドし、さらに `java` コマンドでそのクラスの main メソッドを起動 |
| JavaScript ファイル | `node ファイル名` を実行 |
| PHP ファイル | `php ファイル名` を実行 |
| Python ファイル | `python ファイル名` を実行 |
| Ruby ファイル | `ruby ファイル名` を実行 |
| Go ファイル | `go run ファイル名` を実行 |
| Groovy ファイル | `groovy ファイル名` を実行 |
| Vim ファイル | Vim 自身の `:source` コマンドで実行 |

これら以外の言語に対しても、同じように追加していくことができます。
下記は、さらに C++ プログラム用に `clang++` コマンドを使用してコンパイル＆実行できるようにした例です。

{{< code lang="vim" title="例: F5 キーで C/C++ コードをコンパイル＆実行" >}}
augroup vimrc
    " ...省略...
    autocmd FileType c,cpp  nmap <buffer> <F5> :DoClang<CR>
augroup END

" C/C++ コードをコンパイル＆実行する関数
command! DoClang call s:DoClang()
function! s:DoClang()
    if has("win64")
        :!clang++ % -o %<.exe && %<.exe
    else
        :!clang++ % -o %<.out && ./%<.out
    endif
endfunction
{{< /code >}}

この例では、`autocmd` の末尾に直接実行するコマンドを記述するのではなく、`DoClang` という独自定義の関数を呼び出してコンパイルと実行を行うようにしています。

