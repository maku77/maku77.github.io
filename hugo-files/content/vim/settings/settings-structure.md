---
title: "Neovim の設定ファイルをモジュール化して管理する"
url: "p/t76nvuq/"
date: "2025-10-26"
tags: ["neovim"]
draft: true
---

Neovim の設定は `~/.config/nvim/init.lua` に記述しますが、設定内容が増えてくると見通しが悪くなってきます。
そこで、次のような感じで設定ファイルをモジュール化して管理する方法をおすすめします。

```
~/.config/nvim/
├── init.lua  （エントリーポイント）
└── lua/
    ├── options.lua （基本設定）
    ├── keymaps.lua （キーマッピング）
    ├── utils.lua   （ユーティリティ関数）
    ├── plugins.lua （プラグイン管理）
    └── plugins/    （プラグイン設定）
        ├── plugin1.lua
        ├── plugin2.lua
        └── plugin3.lua
```

init.lua ─ エントリーポイント
----

Neovim のエントリーポイントとなる設定ファイルです。
ここで **`require`** 関数を使って、`lua` ディレクトリ以下のモジュールを読み込みます。

{{< code lang="lua" title="~/.config/nvim/init.lua" >}}
require("options")   -- 基本設定 (lua/options.lua)
require("keymaps")   -- キーマッピング (lua/keymaps.lua)
require("utils")     -- ユーティリティ関数 (lua/utils.lua)
require("plugins")   -- プラグイン管理 (lua/plugins.lua)
{{< /code >}}


lua/options.lua ─ 基本設定
----

基本的な Neovim の設定を記述します。

{{< code lang="lua" title="~/.config/nvim/lua/options.lua" >}}
-- OS のクリップボードと連携する
vim.opt.clipboard:append({ "unnamed", "unnamedplus" })

-- インデントやタブの設定
vim.opt.tabstop = 4        -- タブ文字の表示幅
vim.opt.expandtab = true   -- タブキーでスペースを入力する (default: noexpandtab)
vim.opt.softtabstop = -1   -- タブキーで入力するスペース数 (-1: tabstop に合わせる)
vim.opt.shiftwidth = 4     -- シフトコマンドでのインデント量
vim.opt.shiftround = true  -- シフトコマンドでのインデント量を tabstop 単位に丸める
{{< /code >}}


lua/keymaps.lua ─ キーマッピング
----

キーマッピングの設定を記述します。

{{< code lang="lua" title="~/.config/nvim/lua/keymaps.lua" >}}
-- Leader キーをスペースに変更
vim.g.mapleader = " "

-- タブの切り替え
vim.keymap.set("n", "<C-l>", ":tabnext<CR>")
vim.keymap.set("n", "<C-k>", ":tabnext<CR>")
vim.keymap.set("n", "<C-j>", ":tabprevious<CR>")
vim.keymap.set("n", "<C-h>", ":tabprevious<CR>")
vim.keymap.set("n", "<Leader>h", ":tabprev<CR>")
vim.keymap.set("n", "<Leader>l", ":tabnext<CR>")
{{< /code >}}

{{% note %}}
過去のバージョンの Neovim では `vim.api.nvim_set_keymap` を使ってキーマッピングを設定していましたが、Neovim 0.7 以降では `vim.keymap.set` を使うことが推奨されています。
{{% /note %}}


lua/utils.lua ─ ユーティリティ関数
----

他の設定ファイルから使用する関数を定義します。
この Lua モジュールからはテーブル（連想配列オブジェクト）を戻り値として返すようにします。
慣例的にこのテーブル変数は **`M`** という名前にします。
次の例では、`insert_date()` という関数を定義しています。

{{< code lang="lua" title="~/.config/nvim/lua/utils.lua" >}}
local M = {}

-- 日付 (YYYY-MM-DD) を挿入する関数
function M.insert_date()
  -- built-in 関数を使って現在の日付を YYYY-MM-DD 形式で取得
  local date_str = vim.fn.strftime("%Y-%m-%d", vim.fn.localtime())
  -- カーソルの位置に日付を挿入
  vim.api.nvim_put({ date_str }, "", false, true)
end

return M
{{< /code >}}

例えば、`keymaps.lua` ファイルなどから次のように **`require`** で読み込んで使用します。

{{< code lang="lua" title="~/.config/nvim/lua/keymaps.lua" >}}
local utils = require("utils")

-- F1 キーで今日の日付を挿入
vim.keymap.set("i", "<F1>", utils.insert_date, {
  silent = true,
  desc = "Insert today’s date under the cursor"
})
{{< /code >}}


（おまけ）GitHub や Dropbox で設定ファイルを管理する
----

複数の PC で Neovim の設定を共有したい場合、GitHub や Dropbox で設定ファイルを管理すると便利です。
例えば、Dropbox の共有フォルダに Neovim の設定ファイル (`init.lua`) を置き、各 PC の `~/.config/nvim/init.lua` からその共有フォルダにある設定ファイルを読み込むようにします。

{{< code lang="lua" title="~/.config/nvim/init.lua" >}}
-- 共有フォルダをモジュール検索パスに追加し、そこに置かれた共有 init.lua を読み込む
local shared_dir = os.getenv("HOME") .. "/Dropbox/share/config/nvim"
package.path = shared_dir .. "/?.lua;" .. package.path
require("init")
{{< /code >}}

共有フォルダには次のような感じで複数の Lua ファイルが格納されています。

```
~/Dropbox/share/config/nvim/
  ├─ init.lua  （共有されたエントリーポイント）
  ├─ options.lua
  ├─ keymaps.lua
  ├─ utils.lua
```

