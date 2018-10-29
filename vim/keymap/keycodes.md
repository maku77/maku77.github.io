---
title: "map、noremap 系コマンドで使用できる特殊キーの一覧"
date: "2012-07-27"
---

キーマップに使えるキー名の一覧
----

`nmap` や `imap` コマンドなどで特殊なキー（<kbd>F1</kbd> キーや <kbd>BackSpace</kbd> キーなど）を使用したマッピング定義を行いたいときに、どのような名前で記述すべきかの一覧は、下記のヘルプページで参照することができます。

~~~
:help keycodes
~~~

例えば、特殊キーを表すために次のような表記が使えることがわかります。

- `<BS>`: Back Space キー
- `<CR>`: Enter キー（`<Enter>` でもOKだけど `<CR>` がよく使われている）
- `<Esc>`: Esc キー
- `<Space>`: スペースキー
- `<Tab>`: Tab キー
- `<Del>`: Delete キー
- `<Up>`/`<Down>`/`<Left>`/`<Right>`: 上下左右カーソルキー
- `<F1>`-`<F12>`: ファンクションキー（F1からF12）
- `<Help>`: Help キー
- `<Insert>`: Insert キー
- `<Home>`: Home キー
- `<End>`: End キー
- `<PageUp>`: Page Up キー
- `<PageDown>`: Page Down キー

Shiftキー、Controlキー、Alt(Meta)キー、Commandキー（macOSのみ）といったキーとの同時押しを表現するには、次のように記述します。

- `<S-Up>`: Shift + カーソルキー上
- `<C-F10>`: Control + F10
- `<M-Space>`: Alt(Meta) + Space
- `<D-Right>`: Command(macOS) + カーソルキー右
- `<C-S-F1>` : Control + Shift + F1

#### 例: インサートモードで Control + Shift + F1 キーを押したときに、Hello と挿入

~~~
:inoremap <C-S-F1> Hello
~~~

#### 例: ノーマルモードでスペースキーを押したときに、画面スクロール

~~~
:nnoremap <Space> <PageDown>
~~~

<div class="note">
ここで、<code>nmap</code> の代わりに <code>nnoremap</code> を使用しているのは、<kbd>PageDown</kbd> のデフォルトの振る舞いを割り当てるためです。
<code>nmap</code> を使用すると、<kbd>PageDown</kbd> にすでに別の機能が割り当てられている場合に、<kbd>Space</kbd> の機能にもその機能が割り当てられてしまいます。
</div>

ここまででお分かりの通り、`<` と `>` は特殊キーの名前を囲むために使用されるため、`<` という文字そのものを表すためには、`<lt>` という特殊な書き方をしなければいけません。

#### 例: インサートモードで Ctrl-D を2回押したときに &lt;Hello&gt; を挿入する

~~~
:inoremap <C-D><C-D> <lt>Hello>
~~~


キー名を自動で挿入する
----

インサートモードやコマンドラインで、<kbd>CTRL-K</kbd> に続けて特殊キーを入力すると、カーソル位置にそのキー名を挿入することができます。
例えば、<kbd>CTRL-K</kbd><kbd>CTRL-SHIFT-↓</kbd> と入力すると下記のように挿入されます。

~~~
<C-S-Down>
~~~

この表記が、`nmap` や `nnoremap` コマンドで使用できるキー名となります。
この機能を使えばキー名を覚える必要がないように思えますが、<kbd>Esc</kbd> キーなどはうまく表示できかったりする（ノーマルモードに戻ってしまう）ので、やはり基本は `:help keycodes` の一覧で確認するのがよいでしょう。

