---
title: "Vim/NeoVim でインサートモード中に Backspace キーや CTRL-W で文字を削除するときの振る舞いを変更する (backspace)"
url: "p/b9tsccu/"
date: "2018-11-07"
lastmod: "2024-06-10"
tags: ["vim"]
changes:
  - 2024-06-10: NeoVim の設定について追記。
aliases: /vim/settings/backspace.html
---

backspace オプション（Vim の場合）
----

Vim エディタでは、インサートモード中に <kbd>Backspace</kbd> キーを押すと、入力した文字が削除されますが、デフォルトではインサートモードに入ったカーソル位置より前の文字を削除することはできません。
代表的な削除系のキー入力は、下記のような振る舞いをします。

- <kbd>Backspace</kbd>: カーソル位置の直前の 1 文字を削除する。ただし、インサートモードに入ったときのカーソル位置までしか削除できない。
- <kbd>CTRL-W</kbd>: カーソル位置の直前の 1 単語を削除する。ただし、インサートモードに入ったときのカーソル位置までしか削除できない。
- <kbd>CTRL-U</kbd>: カーソル位置からインサートモードに入ったときのカーソル位置までを削除する。

__`backspace`__ オプションを設定すると、上記のようなキー入力によって削除可能な文字の範囲を広げることができます。
`backspace` オプションには次のような値をカンマ区切りで設定します。

- __`indent`__: オートインデント機能で挿入されたスペースを削除できる
- __`eol`__: 改行を削除できる（前の行に遡って削除していける）
- __`start`__: インサートモードに入ったときのカーソル位置よりも前の文字を削除できる（ただし、<kbd>CTRL-W</kbd> や <kbd>CTRL-U</kbd> による削除は、インサートモードに入ったカーソル位置までで削除範囲が一度止まる）

{{< code lang="vim" title="設定例 (~/.vimrc)" >}}
" インサートモード中の BS、CTRL-W、CTRL-U による文字削除を柔軟にする
set backspace=indent,eol,start
{{< /code >}}

上記のように設定しておくと、それぞれの削除系のキー入力の振る舞いは下記のように変化します。

- <kbd>Backsapce</kbd>: カーソル位置の直前の 1 文字を削除する。**インサートモードに入った時のカーソル位置よりも前の文字を削除できる。**
- <kbd>CTRL-W</kbd>: カーソル位置の直前の 1 単語を削除する。**インサートモードに入った時のカーソル位置よりも前の単語を削除できる。ただし、インサートモードに入った時のカーソル位置も単語の境界とみなす。**
- <kbd>CTRL-U</kbd>: **カーソル位置から行頭までを削除する。ただし、インサートモードに入ったときのカーソル位置も行頭とみなす。**


backspace オプション (NeoVim の場合）
----

NeoVim の場合は、`backspace` オプションの設定値が __デフォルトで `indent,eol,start`__ となっているため、何も設定しなくても <kbd>Backspace</kbd> キーが直感的に振る舞います（編集開始位置よりも前の文字を削除できます）。

