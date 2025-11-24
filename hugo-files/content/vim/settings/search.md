---
title: "Vim/Neovim の検索に関する設定 (ignorecase, smartcase, wrapscan, hlsearch, incsearch)"
url: "p/v4cuc9g/"
date: "2009-01-29"
lastmod: "2025-11-24"
tags: ["vim"]
changes:
  - 2025-11-24: Neovim の設定方法を追加
aliases: /vim/settings/search.html
---

大文字・小文字の区別 (`ignorecase`)
----

[`/{pattern}` によるカレントファイル内の検索](/p/u4gw7c3/)や、[`:vimgrep` による grep 検索](/p/c4q8amz/)では、**デフォルトでは大文字・小文字を区別** してパターンマッチングが行われます。
__`ignorecase`__ オプションを設定すると、大文字・小文字を区別せずに検索できるようになります。

{{< code lang="vim" title="Vim (.vimrc) の場合" >}}
:set ignorecase    "大文字・小文字を区別しないで検索
:set noignorecase  "大文字・小文字を区別して検索 (default)
{{< /code >}}

{{< code lang="lua" title="Neovim (init.lua) の場合" >}}
vim.opt.ignorecase = true   -- 大文字・小文字を区別しないで検索
vim.opt.ignorecase = false  -- 大文字・小文字を区別して検索 (default)
{{< /code >}}

`ignorecase` に加えて __`smartcase`__ オプションを設定すると、検索パターンに大文字を含むときだけ大文字・小文字を区別して検索できるようになります。オススメです。

{{< code lang="vim" title="Vim (.vimrc) の場合" >}}
:set ignorecase smartcase  " 検索パターンに大文字を含むときは大文字・小文字を区別して検索
{{< /code >}}

{{< code lang="lua" title="Neovim (init.lua) の場合" >}}
vim.opt.ignorecase = true  -- 大文字・小文字を区別しないで検索
vim.opt.smartcase = true  -- 検索パターンに大文字を含むときは大文字・小文字を区別して検索
{{< /code>}}


ファイル末尾まで検索したら先頭から検索 (`wrapscan`)
----

`n` キーを連打して検索パターンにヒットした箇所に次々とジャンプしていくとき、デフォルトでは最後にヒットした文字列まで到達すると、次はファイルの先頭に戻って検索されます。
**`nowrapscan`** オプションを設定しておくと、ファイルの末尾でカーソルのジャンプが停止します（**`下まで検索しましたが該当箇所はありません`** と表示されます）。

{{< code lang="vim" title="Vim (.vimrc) の場合" >}}
:set wrapscan    "折り返し検索 ON (default)
:set nowrapscan  "折り返し検索 OFF
{{< /code >}}

{{< code lang="lua" title="Neovim (init.lua) の場合" >}}
vim.opt.wrapscan = true   -- 折り返し検索 ON (default)
vim.opt.wrapscan = false  -- 折り返し検索 OFF
{{< /code >}}


検索結果のハイライト (`hlsearch`)
----

**`hlsearch`** オプションがセットされた状態で検索を行うと、検索にヒットした部分がすべてハイライト表示されるようになります。
Neovim ではデフォルトで有効になっています。

{{< code lang="vim" title="Vim (.vimrc) の場合" >}}
:set hlsearch    "検索結果のハイライト ON
:set nohlsearch  "検索結果のハイライト OFF (default)
{{< /code >}}

{{< code lang="lua" title="Neovim (init.lua) の場合" >}}
vim.opt.hlsearch = true   -- 検索結果のハイライト ON (default)
vim.opt.hlsearch = false  -- 検索結果のハイライト OFF
{{< /code >}}

`hlsearch` オプションを設定していると、検索結果のハイライト表示が出っぱなしになって邪魔になることがあります。
その場合は、**`:nohl`** コマンドを実行してハイライト表示を消すことができます。

{{< code lang="vim" title="検索結果のハイライトをクリア" >}}
:nohl  " :nohlsearch の省略形
{{< /code >}}

オプション名とコマンド名が `nohlsearch` で同じなので混同しないようにしてください。


インクリメンタル・サーチを有効にする (`incsearch`)
----

**`incsearch`** オプションがセットされた状態で検索を行うと、検索パターンを 1 文字入力するたびに検索結果がリアルタイムに表示されるようになります。
Neovim ではデフォルトで有効になっています。

{{< code lang="vim" title="Vim (.vimrc) の場合" >}}
:set incsearch    "インクリメンタル・サーチ ON
:set noincsearch  "インクリメンタル・サーチ OFF (default)
{{< /code >}}

{{< code lang="lua" title="Neovim (init.lua) の場合" >}}
vim.opt.incsearch = true   -- インクリメンタル・サーチ ON (default)
vim.opt.incsearch = false  -- インクリメンタル・サーチ OFF
{{< /code >}}

このインクリメンタル・サーチは便利ですが、パターン入力中にカーソル位置がジャンプしてしまうので、慣れるまで戸惑うことがあります。

インクリメンタル・サーチ中に **`CTRL-G`** や **`CTRL-T`** キーを入力すると、パターン入力を継続したまま、次（あるいは前）のヒット箇所にジャンプすることができます。
このキーマッピングは若干覚えにくいですが、QWERTY キーボードの場合、`G` キーは `T` キーの上にあるので、キーの位置関係で覚えるといいかもしれません。

