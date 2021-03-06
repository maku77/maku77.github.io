---
title: "ウィンドウ（タブ）移動時にカレントディレクトリも自動で移動する (autochdir)"
date: "2020-05-29"
---

カレントディレクトリとは
----

Vim のカレントディレクトリは、最初に開いたファイルがあるディレクトリに設定されます。
カレントディレクトリは、例えば下記のようなコマンドを実行したときに、始点となるディレクトリとして使用されます。

- `:vim pattern **/*.txt` などで複数ファイルを grep するとき
- `:e sample.txt` でファイルを開くとき
- `:cd dirname` でカレントディレクトリを移動するとき
- `:terminal` でターミナルを開くとき

このカレントディレクトリは、別のディレクトリにあるファイルを開いた場合も変化しません。
`:vim (:vimgrep)` コマンドでファイルを検索してもなぜかヒットしないというときは、カレントディレクトリが正しくセットされてない可能性があります。
カレントディレクトリは `:pwd` で確認することができます。


カレントディレクトリを変更する
----

### 手動で移動する

```
:cd %:h
```

と実行すると、現在編集中のファイルがあるディレクトリ (`%:h`) へ移動することができます。
カレントウィンドウ（カーソルのあるウィンドウ）のみを対象にカレントディレクトリを移動したいときは、`:cd` の代わりに __`:lcd`__ を使用します。

### 自動で移動する

__`autochdir`__ オプションをセットしておくと、新しくウィンドウを開いた時や、タブを切り替えたときなどに、そのファイルがあるディレクトリへ自動で移動してくれるようになります。

#### ~/.vimrc

```vim
" Change the current working directory automatically
set autochdir
```

現在の設定は `:set autochdir?` で確認できます。

[autocmd を使ってディレクトリ移動を自動化する方法](./autocmd-cd.html) もありますが、`autochdir` が使える Vim 環境であればそちらを使った方が簡単です。

