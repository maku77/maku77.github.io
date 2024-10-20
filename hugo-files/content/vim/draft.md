---
title: "DRAFT: Vim メモ"
url: "p/2gstdsd/"
date: "2014-01-27"
tags: ["Vim"]
draft: true
---

2014-01-27: キーマッピング時に <silent> を挟むと実行時にエコー出力しない
----

```
nmap <silent> ;s :call ToggleSyntax()<CR>
```

文字列を連結

```
let var .= expr
let var = str1 . str2

nmap <silent> ]] :let &tabstop += 1<CR>
nmap <silent> [[ :let &tabstop -= &tabstop > 1 ? 1 : 0<CR>
```


Vim の真偽値
----

Vim ではブール値コンテキストで「偽 (false)」とみなされるのは数値の 0 のみです。
文字列がブール値コンテキストで指定された場合、整数値に変換されて評価されます。

```
0         => false
1         => true
'100aaa'  => true
'aaa100'  => false
'0.1'     => false
```


文字列と数値の比較／文字列と文字列の比較
----

文字列と数値の比較をしようとすると、文字列が整数値に変換されてから比較が行われます。

```
if '0' == 0      => true  （内部では 0 == 0）
if '0' == 0.0    => true  （内部では 0 == 0.0）
if '0.1' == 0    => true! （内部では 0 == 0）
if '0.1' == 0.1  => false!（内部では 0 == 0.1）
if 'aaa' == 0    => true! （内部では 0 == 0）
if '1AB' == 1    => true! （内部では 1 == 1）
```

文字列同士の比較では、厳密に文字列の一致を調べます。

```
if 'abc' == 'abc'  => true
if '0.0' == '0'    => false
```

文字列同士を比較するときの、大文字、小文字の区別は演算子で指定できます。

```
if 'abc' ==# 'ABC'  => false（大文字・小文字を区別する）
if 'abc' ==? 'ABC'  => true（大文字・小文字を区別しない）
if 'abc' == 'ABC'   => ignorecase がセットされている場合は true
```

単純な == による比較は、ignorecase の設定により結果が左右してしまうので、通常は ==# を使った厳密な比較を行うのがよいでしょう。


文字列が空かどうかを調べる
----

```
if empty(str)
    echo 'Empty'
endif
```

あるいは、

```
if str == ''
    echo 'Empty'
endif
```

empty() は、数値が 0 であること、リスト要素、辞書要素が空であることの確認にも使用できます。
文字列が空でないことを調べるために、以下のようにするのは間違いです。

```
if str
    echo "Empty"
endif
```

ブール値のコンテキストに指定された文字列は整数値に変換されるため、ほとんどの場合 false と評価されてしまいます。


F5キーで任意の function を実行する
----

[2012-06-11]

下記のようにすると、F5 キーを押したときに、Run() が実行されるようになります。

```
command! Run call s:Run()
nmap <F5> :Run<CR>

function! s:Run()
     echo "Hello"
endfunction
```

