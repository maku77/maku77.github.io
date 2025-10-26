---
title: "Vim/Neovim で制御文字（改行、タブ文字、行末のスペースなど）を表示する (list, listchars)"
url: "p/s596qii/"
date: "2011-04-24"
lastmod: "2024-06-10"
tags: ["vim"]
changes:
  - 2024-06-10: Neovim の設定について追記。
aliases: /vim/settings/show-space.html
---

list モードとは
----

Vim/Neovim の __`list`__ モードを有効にすると、テキストファイル内の制御文字や空白文字（タブや末尾のスペースなど）を視覚的に表示することができます。
例えば、プログラミング言語の Python などでコーディングを行っているときは、タブ文字と通常のスペースを意識して使い分けなければいけないので、このような非表示文字を目に見えるように設定しておくことには意味があります。
もちろん通常の文章を記述するときにも、行末の余計なスペースなどを簡単に見つけられるようになるので、この設定は常に有効にしておくことをオススメします。

list モードは、Vim でも Neovim でもデフォルトでは無効になっているため、明示的に有効化する必要があります。

```vim
:set list    "制御文字を表示
:set nolist  "制御文字を非表示
```

次のように行範囲（全ての行）を指定して `list` コマンドを実行すると、一時的に制御文字を確認することができます。

```vim
:% list
```


制御文字の表示方法の設定 (listchars)
----

タブや改行などの制御文字をどのように表示するかは、__`listchars (lcs)`__ オプションで設定します。

### デフォルト設定

`listchars` のデフォルト値は Vim と Neovim で次のように異なっています。

```vim
" Vim のデフォルト設定
set listchars=eol:$

" Neovim のデフォルト設定
set listchars=tab:>\ ,trail:-,nbsp:+
```

Vim のデフォルト設定では、タブ文字が __`^I  `__、改行が __`$`__ で表示されます。
Neovim のデフォルト設定では、タブ文字が __`>   `__、行末のスペースが __`-`__ で表示されます。

### listchars の設定例

{{< code lang="vim" title="Vim (~/.vimrc) の場合" >}}
" 制御文字の設定
set listchars=tab:>.,trail:_,eol:$
set list
{{< /code >}}

{{< code lang="lua" title="Neovim (~/.config/nvim/init.lua) の場合" >}}
-- 制御文字の表示設定（方法 1）オススメ
vim.opt.listchars = { tab = ">.", trail = "_", eol = "$" }
vim.opt.list = true

-- 制御文字の表示設定（方法 2）
vim.o.listchars = "tab:>.,trail:_,eol:$"
vim.opt.list = true
{{< /code >}}

- 各設定の意味
  - __`tab:>.`__ では、タブの 1 文字目を `>` で、2 文字目以降を `.` で表示するよう指示しています。
  - __`trail:_`__ では、行末の半角スペースを `_` で表示するよう指示しています。
  - __`eol:$`__ では、行末に `$` を表示するよう指示しています。
  - __`set list`__ では、list モードを有効化して制御文字を表示するよう指示しています。


listchars オプションのヘルプ
----

どんな非表示文字を表示できるかは、__`:help 'listchars'`__ のヘルプで確認できます。
下記はヘルプ内容の抜粋です。

- __`eol:c`__
  - Character to show at the end of each line.  When omitted, there is no extra character at the end of the line.
  - 行末の表示に使われる文字。指定されないと、行末には何も表示されない。
- __`tab:xy[z]`__
  - Two or three characters to be used to show a tab. The third character is optional.
  - タブ文字の表示に使われる文字。1 文字目は 1 回だけ使われる。2 文字目はタブが通常占めるだけの空白を埋めるまで繰り返し表示される。"tab:>-" とすると、タブが 4 文字の設定では ">---" となる。指定されないと、タブは `^I` と表示される。
- __`space:c`__
  - Character to show for a space.  When omitted, spaces are left blank.
  - スペースの表示に使われる文字。指定されないと、スペースは空白のまま。
- __`trail:c`__
  - Character to show for trailing spaces.  When omitted, trailing spaces are blank.  Overrides the "space" and "multispace" settings for trailing spaces.
  - 行末のスペースの表示に使われる文字。指定されないと、行末のスペースは空白のまま。行末のスペースでは "space" の設定を上書きする。
- __`extends:c`__
  - Character to show in the last column, when `wrap` is off and the line continues beyond the right of the screen.
  - `wrap` がオフで、行が画面の右端よりも伸びているときに、最終列に表示される文字。
- __`precedes:c`__
  - Character to show in the first visible column of the physical line, when there is text preceding the character visible in the first column.
  - `wrap` がオフで、最前列で表示されている最初の文字より前にテキストが存在するとき {訳注: 上の行の末尾が画面の右端より伸びているとき} に、最前列に表示される文字。
- __`conceal:c`__
  - Character to show in place of concealed text, when `conceallevel` is set to 1.  A space when omitted.
  - `conceallevel` が 1 のときに Conceal されたテキストの代わりに表示される文字。
- __`nbsp:c`__
  - Character to show for a non-breakable space character (0xA0 (160 decimal) and U+202F).  Left blank when omitted.
  - ノンブレークスペース文字 (0xA0 (10進数では160) や U+202F) の表示に使われる文字。指定されない場合は空白のまま。訳注: 0xA0はLatin1で改行なしスペースを表す。

表示文字として UTF-8 の文字を指定できますが、`:` と `,` は使えません。
表示文字は 1 文字幅の文字を指定する必要があります。
次のように表示文字に Unicode を指定することもできます（`\\uXXXX` あるいは `\\UXXXXXXXX` の形で指定します）。

{{< code lang="vim" title="タブを »... 行末を ↵ で表示" >}}
set listchars=tab:».,eol:↵
set listchars=tab:\\u00bb.,eol:\\u21b5          "同上
set listchars=tab:\\U000000bb.,eol:\\U000021b5  "同上
{{< /code >}}

`eol`、`extends`、`precedes` には強調表示グループ __`NonText`__ が使われます。
`nbsp`、`space`、`tab`、`trail` には強調表示グループ __`SpecialKey`__ が使われます。


参考
----

- [全角スペースを見えるように表示する](/p/preoa93/)

