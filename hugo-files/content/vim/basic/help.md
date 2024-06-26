---
title: "Vim のヘルプコマンドの使い方 (:help, :helpgrep)"
url: "p/fdep5i7/"
date: "2007-03-12"
tags: ["vim"]
aliases: /vim/basic/help.html
---

ヘルプの起動／終了
----

Vim エディタから下記のコマンドを実行することで、ヘルプを起動・終了することができます。

- __`:help`__ ... ヘルプを起動する
- __`:q`__ ... ヘルプを終了する
- __`ZZ`__ ... ヘルプを終了する


:help コマンドに渡すパラメータ
----

`:help` コマンドの引数に特定のプレフィックスを付けたり、引数を引用符で囲むことで、異なるモードのコマンドのヘルプを見ることができます。

| 調べる項目 | プレフィックスなど | 例   |
| ---- | ---- | ---- |
| Normal mode のキー入力  | なし | `:help u` |
| Insert mode のキー入力  | __`i_`__ | `:help i_CTRL-N, :help i_<Esc>` |
| Visual mode のキー入力  | __`v_`__ | `:help v_u` |
| Command mode のキー入力 | __`c_`__ | `:help c_<Del>` |
| ex コマンド | __`:`__ | `:help :quit` |
| オプション | __`'name'`__ | `:help 'number'` |
| Vim の起動パラメータ | __`-`__ | `:help -t` |

特殊なキー入力は、`CTRL-A`、`<Del>`、`<Esc>` のように表現することができます（他のキーの表現方法は `:help keycodes` で確認できます）。

{{< code lang="vim" title="例: Ctrl-A（normal mode）のヘルプを表示する" >}}
:help CTRL-A
{{< /code >}}

{{< code lang="vim" title="例: Ctrl-A（insert mode）のヘルプを表示する" >}}
:help i_CTRL-A
{{< /code >}}

{{< code lang="vim" title="例: Vim の起動パラメータ -t のヘルプを表示する" >}}
:help -t
{{< /code >}}

{{< code lang="vim" title="例: number オプションのヘルプを見る" >}}
:help 'number'
{{< /code >}}


ヘルプファイルを grep する
----

__`:helpgrep`__ コマンドを使用すると、ヘルプファイル内を grep 検索することができます。

```
:helpgrep keyword
```

検索が終了すると、最初にキーワードにヒットした部分のヘルプが表示されるのですが、多くの場合は複数個所がヒットしているはずです。
検索後に __`:copen`__ コマンドを実行すると、ヒットした行の一覧を表示し、そこから各ヒット位置へジャンプすることができます。
一覧表示のウィンドウは __`:cclose`__ コマンドで閉じることができます。


ヘルプを起動した後の操作方法
----

`:help` コマンドでヘルプを起動した後は、下記の操作で項目間をジャンプすることができます。

- __`Ctrl-]`__ ... `|:help|` などのリンク先へジャンプ（タグジャンプ）
- __`Ctrl-t`__ ... ジャンプ先から戻る

Vim の `doc` ディレクトリ内に `tags` ファイルが用意されているので、__`Ctrl-]`__ を使ってカーソル位置の単語に関連するヘルプページにジャンプすることができます。
GUI 版の gvim を使用している場合は、マウスで単語をダブルクリックしても同様にジャンプできます。

例えば、`:help` で表示されたページの `usr_01.txt` にカーソルを合わせて `Ctrl-]` を入力すると、`usr_01.txt` のヘルプページへジャンプできます。
ジャンプ元に戻って来たい場合は、`Ctrl-t` と入力します。

