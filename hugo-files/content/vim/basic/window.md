---
title: "Vim でウィンドウを分割する"
url: "p/ym9pa88/"
date: "2007-10-05"
tags: ["vim"]
description: "Vim のウィンドウを分割すると、複数のファイルの内容を同時に表示しながら作業することができます（同一ファイル内の 2 か所を表示することもできます）。"
changes:
  - 2024-10-19: 説明文を追加。
aliases: /vim/basic/window.html
---

Vim のウィンドウを分割すると、複数のファイルの内容を同時に表示しながら作業することができます。
同一ファイル内の 2 か所を表示することもできます。


ウィンドウを分割する
----

| 入力 | 説明 |
| ---- | ---- |
| `:sp[lit]` | 上下に分割（編集中のファイルを開く）。`Ctrl-w s` としても OK |
| `:vs[plit]` | 左右に分割（編集中のファイルを開く）。`Ctrl-w v` としても OK |
| `:new` | 上下に分割（空のファイルを開く）。`Ctrl-w n` としても OK |
| `:vnew` | 左右に分割（空のファイルを開く） |
| `:sview [file]` | 閲覧用にファイルを開く。`:split` → `:view` としても OK |


ウィンドウを閉じる
----

| 入力 | 説明 |
| ---- | ---- |
| `Ctrl-w q` | ウィンドウを閉じる。最後のウィンドウであれば Vim を終了 (= `:quit`) |
| `Ctrl-w c` | ウィンドウを閉じる。最後のウィンドウを閉じることはできない (= `:close`) |
| `Ctrl-w o` | カレントウィンドウ以外を閉じる |


ウィンドウを切り替える
----

| 入力 | 説明 |
| ---- | ---- |
| `Ctrl-w w` | 次のウィンドウへ移動（★最低限これだけ覚えておく） |
| `Ctrl-w h` | 左のウィンドウへ移動 |
| `Ctrl-w j` | 下のウィンドウへ移動 |
| `Ctrl-w k` | 上のウィンドウへ移動 |
| `Ctrl-w l` | 右のウィンドウへ移動 |

キーシーケンスの 2 番目のキーは、`Ctrl` キーを押しながら入力することもできるようになっています。
例えば、`Ctrl-w w` と入力する代わりに、`Ctrl-w Ctrl-w` と入力しても OK です。


ウィンドウサイズの変更
----

| 入力 | 説明 |
| ---- | ---- |
| `<NUM>z` | 指定した行数にウィンドウサイズを変更 |
| `Ctrl-w =` | すべて同じサイズにする（★これが基本） |
| `Ctrl-w +` | ウィンドウを縦に大きくする |
| `Ctrl-w -` | ウィンドウを縦に小さくする |
| `Ctrl-w _` | ウィンドウを縦に最大化 |
| `Ctrl-w >` | ウィンドウを横に大きくする |
| `Ctrl-w <` | ウィンドウを横に小さくする |
| `Ctrl-w \|` | ウィンドウを横に最大化 |

ウィンドウサイズを大きくするときに、先に数値を入力すると、何行大きくするかを指定できます。

{{< code lang="vim" title="例: 現在のウィンドウを 3 行分大きくする" >}}
3 Ctrl-w +
{{< /code >}}

ウィンドウサイズを最大化したい場合は、実際にはカレントウィンドウ以外を閉じたいことが多いです。
その場合は、__`Ctrl-w o`__ で他のウィンドウをすべて閉じることができます。

