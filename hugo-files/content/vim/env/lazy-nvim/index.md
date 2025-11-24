---
title: "Neovim 用のプラグインマネージャー lazy.nvim をインストールする"
url: "p/cfc9tpn/"
date: "2025-03-02"
tags: ["vim"]
---

[lazy.nvim](https://lazy.folke.io/) は、Neovim 用のプラグインマネージャーです。
名前の通り、各種プラグインを必要に応じて遅延ロードしてくれるため、多くのプラグインを導入しても Neovim の起動があまり遅くならないという特徴があります。


lazy.nvim のインストール
----

lazy.nvim は [公式サイトの Installation](https://lazy.folke.io/) に記載されている手順に従ってインストールします。
まずは、Neovim の設定ファイルに以下のような行を追加します。

{{< code lang="lua" title="~/.config/nvim/init.lua" >}}
require("config.lazy")
{{< /code >}}

この `require` により、**`~/.config/nvim/lua/config/lazy.lua`** というファイルが読み込まれるようになるので、lazy.nvim 関連の設定はこのファイルに記述していきます。
これは、公式サイトで **Structured Setup** として紹介されている方法です。

下記の設定も公式サイトに記述されている通りの初期設定ですが、このファイルを作成して Neovim を再起動すれば、lazy.nvim が自動的にインストールされます（内部で `git` コマンドを使ってダウンロードしています）。

{{< code lang="lua" title="~/.config/nvim/lua/config/lazy.lua" >}}
-- Bootstrap lazy.nvim
local lazypath = vim.fn.stdpath("data") .. "/lazy/lazy.nvim"
if not (vim.uv or vim.loop).fs_stat(lazypath) then
  local lazyrepo = "https://github.com/folke/lazy.nvim.git"
  local out = vim.fn.system({ "git", "clone", "--filter=blob:none", "--branch=stable", lazyrepo, lazypath })
  if vim.v.shell_error ~= 0 then
    vim.api.nvim_echo({
      { "Failed to clone lazy.nvim:\n", "ErrorMsg" },
      { out, "WarningMsg" },
      { "\nPress any key to exit..." },
    }, true, {})
    vim.fn.getchar()
    os.exit(1)
  end
end
vim.opt.rtp:prepend(lazypath)

-- Make sure to setup `mapleader` and `maplocalleader` before
-- loading lazy.nvim so that mappings are correct.
-- This is also a good place to setup other settings (vim.opt)
vim.g.mapleader = " "
vim.g.maplocalleader = "\\"

-- Setup lazy.nvim
require("lazy").setup({
  spec = {
    -- import your plugins
    { import = "plugins" },
  },
  -- Configure any other settings here. See the documentation for more details.
  -- colorscheme that will be used when installing plugins.
  install = { colorscheme = { "habamax" } },
  -- automatically check for plugin updates
  checker = { enabled = true },
})
{{< /code >}}

これで、lazy.nvim を使用する準備が整いました。
各プラグインの設定は、`~/.config/nvim/lua/plugins` ディレクトリに `.lua` ファイルを作成して記述していきます。
よって、lazy.nvim まわりのファイル構成は以下のようになります。


```
~/.config/nvim/
├── lua/
│   ├── config/
│   │   └── lazy.lua
│   └── plugins/ （使いたいプラグインがあったらここに .lua ファイルを追加する）
│       ├── spec1.lua
│       ├──   :
│       └── spec2.lua
└── init.lua
```


各種プラグインのインストール
----

新しくプラグインをインストールしたいときは、`~/.config/nvim/lua/plugins` ディレクトリに `.lua` ファイルを作成して、そのプラグイン用の設定を記述します。
その後、Neovim を再起動するか **`:Lazy install`** でプラグインをインストールできます。

### 例: ステータスラインをリッチにする (lualine)

- [nvim-lualine/lualine.nvim](https://github.com/nvim-lualine/lualine.nvim)

{{< image w="900" border="true" src="img-lualine.png" title="lualine プラグインによるステータスライン表示" >}}

Vim/Neovim の初期状態のステータスラインはとてもそっけないです。
lualine.nvim プラグインを入れると、上記のようなそれっぽいステータスラインを表示することができます。
上記はプラグインを読み込んだだけのデフォルトの表示例ですが、このままでも十分な情報量です。

{{< code lang="lua" title="~/.config/nvim/lua/plugins/lualine.lua" >}}
return {
  "nvim-lualine/lualine.nvim",
  dependencies = {
    "nvim-tree/nvim-web-devicons",
  },
  event = "VeryLazy",  -- Neovim 起動後に遅延ロードする
  opts = {},
}
{{< /code >}}

自力でステータスラインの表示設定 (`vim.opt.statusline`) をしている場合は忘れずに削除しておきます（参考: [ステータスラインの表示内容を設定する (`statusline`, `laststatus`)](/p/oegfris/)）。

### 例: ファイルツリーを表示する (neo-tree)

- [nvim-neo-tree/neo-tree.nvim](https://github.com/nvim-neo-tree/neo-tree.nvim)

ファイル操作を行うために、何らかのファイルツリーを表示するプラグインを入れておくとよいです。
Neotree プラグインは、ファイルの作成、削除、リネームだけでなく、プレビュー表示 (`P`)、Fazzy Find (`/`) など便利な機能を備えています。
下記の設定例では、`<leader>-e` というショートカットキーで Neotree を表示・非表示できるようにしています。

{{< code lang="lua" title="~/.config/nvim/lua/plugins/neotree.lua" >}}
return {
  "nvim-neo-tree/neo-tree.nvim",
  branch = "v3.x",
  dependencies = {
    "nvim-lua/plenary.nvim",
    "MunifTanjim/nui.nvim",
    "nvim-tree/nvim-web-devicons", -- optional, but recommended
  },
  lazy = false, -- neo-tree will lazily load itself
  config = function()
    -- キーマップで簡単に Neotree をトグルできるようにする
    vim.keymap.set("n", "<Leader>e", ":Neotree toggle<CR>", { desc = "Toggle Neotree" })
    vim.keymap.set("n", "<C-e>", ":Neotree toggle<CR>", { desc = "Toggle Neotree" })
  end,
}
{{< /code >}}

### 例: スクロールを滑らかにする (neoscroll)

- [karb94/neoscroll.nvim](https://github.com/karb94/neoscroll.nvim)

`Ctrl-F/B/D/U` などでスクロールするときにアニメーションさせることができます。

{{< code lang="lua" title="~/.config/nvim/lua/plugins/neoscroll.lua" >}}
return {
  "karb94/neoscroll.nvim",
  opts = {
    duration_multiplier = 0.3,  -- 1.0より小さくするとスクロール速度が上がる
  },
}
{{< /code >}}

### 例: GitHub Copilot プラグイン

Neovim 起動後に、**`:Copilot auth`** として GitHub にサインインすると、GitHub Copilot が使えるようになります。
上記の設定では、Insert モードに入ったときに自動的に GitHub Copilot が起動し、随時提案テキストが表示されるようになります。
提案されたテキストを受け入れたいときは **`<Tab>`** キーを入力します。

- [zbirenbaum/copilot.lua](https://github.com/zbirenbaum/copilot.lua)

{{< code lang="lua" title="~/.config/nvim/lua/plugins/copilot.lua" >}}
return {
  "zbirenbaum/copilot.lua",
  event = "InsertEnter",
  opts = {
    suggestion = {
      auto_trigger = true,
      keymap = {
        accept = "<Tab>",
        next = "<S-C-Space>",
      },
    },
    filetypes = {
      gitcommit = true,
      markdown = true,
      yaml = true,
    },
  },
}
{{< /code >}}

他のプラグインも同様の手順で追加できます。

便利っ ٩(๑❛ᴗ❛๑)۶


lazy.nvim の設定いろいろ
----

{{% private %}}
- https://lazy.folke.io/configuration
{{% /private %}}

### Plugin の更新通知 (checker)

```lua
require("lazy").setup({
  -- ...
  checker = {
    -- automatically check for plugin updates
    enabled = false,
    concurrency = nil, ---@type number? set to 1 to check for updates very slowly
    notify = true, -- get a notification when new updates are found
    frequency = 3600, -- check for updates every hour
    check_pinned = false, -- check for pinned packages that can't be updated
  },
  -- ...
})
```

lazy.nvim 公式サイトの設定例にあるように、`checker = { enabled = true }` と設定しておくと Neovim 起動時に `# Plugin Updates` のような通知が出るようになります。
この通知が煩わしいときは、Plugin の自動更新チェックは無効にしておきましょう。
手動での Plugin 更新は `:Lazy` → `U` でいつでもできます。

```lua
-- Disable automatic plugin update checks
checker = { enabled = false },
```

