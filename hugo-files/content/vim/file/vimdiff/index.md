---
title: "Vim で 2 つのファイルの差分を取る・マージする (vimdiff, vim -d)"
url: "p/78gvwox/"
date: "2020-01-24"
tags: ["vim"]
aliases: ["/vim/file/vimdiff.html"]
---

2 つのテキストファイルの差分を取る
----

Vim（あるいは gVim）には、テキストファイルの差分を取る **diff モード** が付いています。

- 参考: [diff - Vim日本語ドキュメント](https://vim-jp.org/vimdoc-ja/diff.html)
- 参考: [diff - Vim Documentation](https://vim-jp.org/vimdoc-en/diff.html)

ハイライト表示された差分を確認しながらファイルを修正していくことができます。


### Vim/gVim を diff モードで起動する (vimdiff/gvimdiff)

コマンドラインから、Vim や gVim を diff モードを起動するには次のように **`vimdiff`** （あるいは **`gvimdiff`**） コマンドを実行します。
引数を増やせば、3 つ、あるいは 4 つのファイルを比較することも可能です。

- `vim` の場合
    - `vim -d ファイル1 ファイル2`
    - `vimdiff ファイル1 ファイル2` （`vim -d` のエイリアス）
- `gvim` の場合
    - `gvim -d ファイル1 ファイル2`
    - `gvimdiff ファイル1 ファイル2` （`gvim -d` のエイリアス）

例えば、gVim 上で `a.txt` と `b.txt` を比較したいときは次のように gVim を起動します。

```
$ gvimdiff a.txt b.txt
```

{{< image src="img-001.png" >}}

左右にスプリットされた画面になり、内容が異なる部分（2 行目の `BBBBB` と `XXXXX`）がハイライト表示されます。
ウィンドウ間を移動するには `Ctrl-W` `Ctrl-W` と入力します。


### ファイル編集中に他のファイルと比較する (diffsplit)

現在 vim (gvim) で編集中のファイルと、別のファイルの内容を比較したい場合は、次のように **`diffsplit`** コマンドを実行すると diff モードに移行できます。

```
:diffsplit b.txt       （上下に分割）
:vert diffsplit b.txt  （左右に分割）
```

デフォルトでは上下に分割表示しようとするので、左右に分割表示したい場合は、上記のように `vert[ical]` コマンドと組み合わせて使用する必要があります。
`diffsplit` を実行したときのデフォルトの分割方向を左右にするには、`.vimrc` (`_vimrc`) ファイルで次のように [diffopt オプション](https://vim-jp.org/vimdoc-ja/options.html#'diffopt') を設定しておきます。

```vimrc
set diffopt+=vertical
```

### 次の差分／前の差分へジャンプする

diff モードで比較表示を行っているときに、差分のある行にジャンプしたいときは、次のように入力します。

- `[c` ... 前の差分にジャンプ
- `]c` ... 次の差分にジャンプ


マージする
----

`vimdiff (gvimdiff)` コマンドなどで diff モード起動した状態で、次のコマンドを実行すると、差分を簡単にマージしていくことができます。

- 差分箇所で **`dp`** ... 自分の内容をもう一方のファイルに反映
- 差分箇所で **`do`** ... もう一方のファイルに内容を自分に反映

`dp` は diff put、`do` は diff obtain の略だと覚えましょう（公式ドキュメントより）。

左側にカーソルがあるときに、`do`、`dp` を入力すると次のような動きになります。

{{< image src="img-002.png" >}}


（トラブルシューティング）Windows で vimdiff コマンドが見つからない
----

Windows では `vimdiff (gvimdiff)` コマンドは、`vimdiff.bat (gvimdiff.bat)` というバッチファイルでインストールされます（中で `vim.exe -d` や `gvim.exe -d` を呼び出しています）。
`vimdiff` コマンドが見つからない場合は、 [Vim のインストーラ](https://www.vim.org/download.php#pc) で、**bat ファイルを作成** にチェックを入れてインストールしてください。

{{< image src="img-003.jpg" >}}

デフォルトでは、`C:\Windows` ディレクトリの下に `.bat` ファイルを作成してくれますね。
何てことするんだ！

