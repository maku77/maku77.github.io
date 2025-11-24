---
title: "Vim/Neovim でステータスラインの表示内容を設定する (statusline, laststatus)"
url: "p/oegfris/"
date: "2009-02-06"
lastmod: "2025-11-04"
tags: ["vim"]
changes:
  - 2025-11-04: "Neovim のグローバルステータスライン (laststatus=3) に関する説明を追加"
aliases: [/vim/settings/statusline.html]
---

{{< image src="img-001.png" >}}

Vim 画面下部のステータスラインには、編集中のファイル名や、カーソル位置の情報などを表示することができます。


ステータスラインのヘルプを表示する
----

```vim
:help 'statusline'
```

{{< note >}}
オプションに関するヘルプを参照するときは、正式には上記のようにパラメータをシングルクォートで囲んで指定します。ただし、`statusline` に関してはシングルクォートで囲まなくても同じ項目が表示されます。
{{< /note >}}


ステータスラインに表示する内容を設定する
----

```vim
:set laststatus=2  "常に Status Line を表示する
:set statusline {format}
```

### laststatus オプション

**`laststatus`** オプションでは、ステータスラインの表示条件を指定します。

- `laststatus` の値:
  - `0` ... ステータスラインを表示しない
  - `1` ... ウィンドウを分割したときにのみステータスラインを表示する（Vimのデフォルト）
  - `2` ... 常にステータスラインを表示する（Neovim のデフォルト）
  - `3` ... 常にステータスラインを表示する。ウィンドウが分割されても 1 つのステータスラインを共有する（Neovim 専用）

Neovim では `laststatus` の新しい設定値として `3`（グローバルステータスライン）が追加されており、ウィンドウを分割した場合にもステータスラインが 1 つだけ表示されるようになります。

### statusline オプション

**`statusline`** オプションでは、ステータスラインに表示する内容を指定します。
例えば、下記のようなパラメーター入りの文字列を指定します。

```
%F%m%h%w\ %<[ENC=%{&fenc!=''?&fenc:&enc}]\ [FMT=%{&ff}]\ [TYPE=%Y]\ %=[CODE=0x%02B]\ [POS=%l/%L(%02v)]
```

`%` で始まる各パラメータは次のような値に置換されて表示されます（詳細は、Vim のヘルプ `:help 'statusline'` を参照してください）。

- `%F` ... ファイルのフルパス。
- `%m` ... 編集されているなら [+]。リードオンリーなら [-]。
- `%h` ... Help buffer なら [HELP] と表示。
- `%w` ... Preview window なら [PREVIEW] と表示。
- `%<` ... ウィンドウの横幅が縮まってもここまでは表示することを保証。
- `%{...}` ... 式評価。中に Vimscript の式を記述できる。
  - `%{&fenc!=''?&fecn:&enc}`<br>fileencoding が設定されていればその値、設定されていなければ encoding を表示。
  - `%{&ff}`<br>fileformat の値を表示。%{&fileformat} の省略形。(dos, unix, mac)
- `%=` ... これ以降の内容を右寄せで表示。
- `%Y` ... filetype の値を表示。通常はこれに対応する syntax file が読み込まれているはず。
- `%02B` ... カーソル位置の文字コードを16進数で表示。
- `%l/%L` ... 現在行 / 総行数
- `%02v` ... カーソル位置の桁番号。

### 設定例

下記は、Vim/Neovim の設定ファイルでの具体的な設定例です。

{{< code lang="vim" title="Vim (.vimrc) / Neovim (init.vim) の場合" >}}
set laststatus=2  " 常に Status Line を表示する
set statusline=%F%m%h%w\ %<[ENC=%{&fenc!=''?&fenc:&enc}]\ [FMT=%{&ff}]\ [TYPE=%Y]\ %=[CODE=0x%02B]\ [POS=%l/%L(%02v)]
{{< /code >}}

{{< code lang="lua" title="Neovim (init.lua) の場合" >}}
vim.opt.laststatus = 2  -- 常に Status Line を表示する
vim.opt.statusline = [[%F%m%h%w %<[ENC=%{&fenc != '' ? &fenc : &enc}] [FMT=%{&ff}] [TYPE=%Y] %=[CODE=0x%02B] [POS=%l/%L(%02v)]]]
{{< /code >}}

