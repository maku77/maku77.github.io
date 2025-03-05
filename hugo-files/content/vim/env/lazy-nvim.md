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

### 例: GitHub Copilot プラグイン

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

Neovim 起動後に、**`:Copilot auth`** として GitHub にサインインすると、GitHub Copilot が使えるようになります。
上記の設定では、Insert モードに入ったときに自動的に GitHub Copilot が起動し、随時提案テキストが表示されるようになります。
提案されたテキストを受け入れたいときは **`<Tab>`** キーを入力します。

他のプラグインも同様の手順で追加できます。

便利っ ٩(๑❛ᴗ❛๑)۶

