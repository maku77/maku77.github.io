---
title: "Vim の画面スクロール方法まとめ"
url: "p/gu9om5z/"
date: "2007-01-30"
tags: ["vim"]
aliases: /vim/basic/scroll.html
---

画面スクロール操作
----

### 画面スクロール

| コマンド | 説明 |
| ---- | ---- |
| `Ctrl-f` | 1 画面下へスクロール |
| `Ctrl-b` | 1 画面上へスクロール |
| `Ctrl-d` | 半画面下へスクロール、あるいは `scroll` オプションで指定された行数だけ下へスクロール |
| `Ctrl-u` | 半画面上へスクロール、あるいは `scroll` オプションで指定された行数だけ上へスクロール |

### 表示位置を移動

下記の操作では、カーソル位置（行）をキープしたまま、画面の表示位置だけを移動させます。

| コマンド | 説明 |
| ---- | ---- |
| `z<CR>` | カレント行を画面上端へ <b>※1</b> |
| `zz` | カレント行を画面中央へ |
| `z.` | カレント行を画面中央へ（カーソルを最初の非空白文字へ） |
| `zb` | カレント行を画面下端へ |
| `z-` | カレント行を画面下端へ（カーソルを最初の非空白文字へ） |
| `z+` | 画面下端を画面上端へ（これ使い道ある？） |
| `Ctrl-y` | 1 行下へスクロール |
| `Ctrl-e` | 1 行上へスクロール |

<b>※1</b> 例えば、ソースコードの関数名を定義している行で、`z [Enter]` とすれば、その関数の定義が画面上端から表示されます。
`z` コマンドは、`scrolloff` オプションに `0` 以外が設定されていると、その値によって移動後の表示位置が少々変わります。

`z` コマンドを実行するときに数値プレフィックスを付けると、カレント行とみなす行番号を指定することができます。

{{< code lang="vim" title="例: 30 行目が画面上端に表示されるように移動" >}}
30z[Enter]
{{< /code >}}


画面スクロール量の設定 (scroll)
----

`Ctrl-d` や `Ctrl-u` による画面スクロール行数は、__`scroll`__ オプションで設定できます。

```vim
:set scroll=3
```

`scroll` オプションのデフォルトは 0 になっており、この場合だけは「画面に表示されている行数の半分だけスクロールする」という特殊な動きをします。
個人的には、一度に画面半分もスクロールされると脳がついていけないので、3 行くらいのスクロールに変更しておくことをオススメします。

{{< code lang="vim" title="~/.vimrc" >}}
au BufEnter * set scroll=3
{{< /code >}}


画面上端、下端でのスクロール方法の設定 (scrolljump)
----

1 番上の行にカーソルがあるときに、さらに上へカーソルを移動させようとするとスクロールが発生します。
このときのスクロール行数は __`scrolljump`__ オプションで指定することができます（デフォルトは 1）。

```vim
:set scrolljump=1
```

カーソルが画面の上端、下端まで行く前にスクロールを開始するには、__`scrolloff`__ オプションで何行残してスクロールを開始するかを指定します（デフォルトは 0）。
この値を 0 以外にしておくと、作業効率が上がります。

```vim
:set scrolloff=5
```

