---
title: "Vim の map、noremap 系コマンドで使用できる特殊キーの一覧 (key-notation, key-codes)"
url: "p/ibpmg65/"
date: "2012-07-27"
tags: ["vim"]
aliases: /vim/keymap/keycodes.html
---

- 参考: [Vim のキーマップの基本 (`map`, `noremap`)](/p/nqqixxy/)


キーマップに使えるキー名の一覧
----

Vim の map 系コマンド（`nmap` や `imap`）で、`F1` キーや `BackSpace` キーなどの特殊キーを使用したマッピングを行う場合は、下記のヘルプページで表示されるキー表記を使用します。

{{< code lang="vim" title="キー表記のヘルプ" >}}
:help key-notation
{{< /code >}}

例えば、特殊キーを表すために次のような表記が使えることがわかります。

- `<BS>` ... Back Space キー
- `<Bslash>` ... Back Slash キー (`\`)
- `<CR>` or `<Enter>` ... Enter キー（`<CR>` がよく使われている）
- `<Esc>` ... Esc キー
- `<Space>` ... スペースキー
- `<Tab>` ... Tab キー
- `<Del>` ... Delete キー
- `<Up>`/`<Down>`/`<Left>`/`<Right>` ... 上下左右カーソルキー
- `<F1>` 〜 `<F12>` ... ファンクションキー
- `<Help>` ... Help キー
- `<Insert>` ... Insert キー
- `<Home>` ... Home キー
- `<End>` ... End キー
- `<PageUp>` ... Page Up キー
- `<PageDown>` ... Page Down キー

Shift キー、Control キー、Alt (Meta) キー、Command キー（macOS のみ）といったキーとの同時押しを表現するには、次のように記述します。

- `<S-Up>` ... Shift + カーソルキー上
- `<C-F10>` ... Control + F10
- `<M-Space>` ... Alt (Meta) + Space
- `<D-Right>` ... Command (macOS) + カーソルキー右
- `<C-S-F1>`  ... Control + Shift + F1


キーマッピングの設定例
----

{{< code lang="vim" title="例: インサートモードで Control + Shift + F1 キーを押したときに Hello を挿入" >}}
:inoremap <C-S-F1> Hello
{{< /code >}}

{{< code lang="vim" title="例: ノーマルモードでスペースキーを押したときに画面スクロール" >}}
:nnoremap <Space> <PageDown>
{{< /code >}}

ここで、`nmap` の代わりに `nnoremap` を使用しているのは、PageDown キーのデフォルトの振る舞いを割り当てるためです。
`nmap` を使用すると、PageDown キーにすでに別の機能が割り当てられている場合に、Space キーの機能としてその機能が割り当てられてしまいます。

{{< code lang="vim" title="例: インサートモードで Ctrl-D を 2 回押したときに <Hello> を挿入する" >}}
:inoremap <C-D><C-D> <lt>Hello>
{{< /code >}}

ここまでの設定例を見ると分かるように、`<` と `>` は特殊キーの名前を囲むために使用されます。
`<` という文字そのものを表したいときは、**`<lt>`** のように特殊な書き方をしなければいけません。


特殊キー名を素早く入力する方法
----

インサートモードやコマンドラインモードで、**`Ctrl-K`**（あるいは **`Ctrl-Q`**）に続けて特殊キーを入力すると、カーソル位置にそのキー名を挿入することができます。
例えば、`Ctrl-K` → `Ctrl-Shift-BackSpace` と入力すると、下記のような文字列が挿入されます。

```
<C-S-BS>
```

この表記が、`nmap` や `nnoremap` コマンドで使用できるキー名となります。
この機能を使えばキー名を覚える必要がないように思えますが、`Esc` キーなどはうまく表示できかったりする（ノーマルモードに戻ってしまう）ので、やはり基本は `:help key-notation` の一覧表で確認するのがよいでしょう。

