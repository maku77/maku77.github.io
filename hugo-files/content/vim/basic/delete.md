---
title: "Vim の文字の削除方法まとめ (d, delete)"
url: "p/qbmdoef/"
date: "2011-12-21"
tags: ["vim"]
aliases: /vim/basic/delete.html
---

ノーマルモード時
----

| 入力 | 説明 |
| ---- | ---- |
| `dd` | 現在行を削除 |
| `D` | 行末までを削除（`C` なら行末まで削除して入力モードに） |
| `diw` | カーソル位置の単語を削除（単語の後ろのスペースも削除） |
| `daw` | カーソル位置の単語を削除（単語の後ろのスペースは残す） |
| `ciw` | カーソル位置の単語を削除して入力モードへ（単語の後ろのスペースも削除） |
| `caw` | カーソル位置の単語を削除して入力モードへ（単語の後ろのスペースは残す） |


入力モード時
----

| 入力 | 説明 |
| ---- | ---- |
| `C-w` | カーソル位置から直前の単語までを削除 |
| `C-u` | カーソル位置から行頭までを削除 |


応用例: パターンに一致する「行全体」を削除する
----

あるパターンに一致する文字列を含む __行全体__ を削除したい場合は、行範囲と組み合わせて `d` (`delete`) コマンドを使用します。
置換コマンドの `s` コマンドと同様に、最初に行範囲の指定を行います。

```vim
:行範囲 d
```

行範囲を指定しない場合は、カレント行のみが対象になります。
つまり、`:d` というコマンドは、`dd` と同様にカレント行のみを削除します。

| 入力 | 説明 |
| ---- | ---- |
| `:d` | カレント行を削除 |
| `:% d` | すべての行を削除 |
| `:1,10 d` | 1〜10 行目を削除 |
| `:g/temp/ d` | `temp` を含む行を削除 |
| `:g/^#/ d` | `#` で始まる行を削除 |
| `:g/^$/ d` | 空行をすべて削除 |

