---
title: "Neovim の設定ファイル (init.lua) を GitHub や Dropbox で管理する"
url: "p/pzakg36/"
date: "2025-10-29"
tags: ["vim"]
---

Neovim の設定は `~/.config/nvim/init.lua` ファイルに記述しますが、複数の PC で同じ設定を使いたい場合は、GitHub や Dropbox などのクラウドストレージを利用して設定ファイルを共有すると便利です。
ここでは、下記の 2 つの方法を紹介します。

- **シンボリックリンク／ジャンクションを使う方法**
- **Neovim のランタイムパスを追加する方法**

共有する設定ファイルは、下記のようなディレクトリ構造で Git 管理されているとします。

```
~/gitwork/nvim-config/
    ├── .git/
    ├── init.lua
    └── lua/
        ├── options.lua
        ├── keymaps.lua
        └── ...
```


シンボリックリンク／ジャンクションを使う方法
----

特に問題がなければこの方法を使うのが簡単です。
Linux や macOS ではシンボリックリンク、Windows ではジャンクションの機能を使って、Neovim の設定ディレクトリ (`~/.config/nvim`) の実体を、GitHub や Dropbox で管理されているディレクトリに結び付けるだけです。
Neovim の設定ディレクトリのパスは、OS ごとに異なることに注意してください。

- Linux/macOS: **`~/.config/nvim`**
- Windows: **`%LOCALAPPDATA%\nvim`** （通常は `C:\Users\<ユーザー名>\AppData\Local\nvim`）

まず、Neovim の設定ディレクトリ (`~/.config/nvim`) の中身を Git 管理された共有ディレクトリに移動して、元の `~/.config/nvim` ディレクトリは削除しておきます。
その後、次のようにシンボリックリンク／ジャンクションとして `~/.config/nvim` を作り直します。

{{< code title="Linux/macOS の場合 ─ シンボリックリンクの作成" >}}
ln -s ~/gitwork/nvim-config ~/.config/nvim
{{< /code >}}

{{< code title="Windows (PowerShell) の場合 ─ ジャンクションの作成" >}}
New-Item -ItemType Junction -Path $env:LOCALAPPDATA\nvim -Target $env:USERPROFILE\gitwork\nvim-config
{{< /code >}}

これで、`~/.config/nvim/init.lua` にアクセスすると、実際には Git で管理されている `~/gitwork/nvim-config/init.lua` ファイルが読み込まれるようになります。
ちなみに、Windows のディレクトリ・ジャンクションは管理者権限がなくても作成できます。


Neovim のランタイムパスを追加する方法
----

Neovim の `require()` 関数は、ランタイムパス (`vim.opt.rtp`) に登録されているディレクトリの `lua` サブディレクトリから Lua モジュールを検索するようになっています。
つまり、ランタイムパスに GitHub や Dropbox で管理されているディレクトリを追加すれば、そこにある Lua モジュールを `require()` で読み込めるようになります。
例えば、Neovim の設定ファイル `~/.config/nvim/init.lua` に次のようなコードを追加します。

{{< code lang="lua" title="~/.config/nvim/init.lua" >}}
-- GitHub や Dropbox で管理されている設定ディレクトリをランタイムパスに追加し、
-- その lua ディレクトリ以下の .lua ファイルを require("モジュール名") で読み込めるようにする。
local shared_dir = vim.fn.expand("~/gitwork/nvim-config")
vim.opt.rtp:append(shared_dir)

-- 共有ディレクトリにあるルートの init.lua を読み込む
dofile(shared_dir .. "/init.lua")
{{< /code >}}

最後の行で `require("init")` ではなく、`dofile()` を使ってパス指定で直接 `init.lua` を読み込んでいることに注意してください。
`require()` で読み込めるようになるのは、あくまでランタイムパスで登録したディレクトリにある `lua` ディレクトリ以下のモジュールなので、`~/gitwork/nvim-config` 直下の `init.lua` を読み込むには上記のように `dofile()` を使う必要があります（その先の `init.lua` 内からは、`require("keymaps")` のようにモジュールを読み込めます）。

ちなみに、Neovim のランタイムパスとして現在どのディレクトリが登録されているかは、Neovim 内で次のコマンドを実行すると確認できます。
`init.lua` からの `require()` がうまく動作しない場合などに確認してみてください。

```vim
:lua print(vim.inspect(vim.api.nvim_list_runtime_paths()))
```

{{% note title="プラグインを lazy.nvim で管理している場合の注意" %}}
lazy.nvim が読み込むプラグインの設定ファイルを `~/gitwork/nvim-config/lua/plugins` 以下に配置する場合、lazy.nvim 内部のファイル監視機能がうまく動作しないことがあります。
その場合、lazy.nvim の `performance.rtp.reset` オプションを `false` 設定することで解決できたりします。
詳しくは lazy.nvim のドキュメントを参照してください。

{{< code lang="lua" hl_lines="7-12" >}}
require("lazy").setup({
  spec = {
    { import = "plugins" },
  },
  install = { colorscheme = { "habamax" } },
  checker = { enabled = true },
  performance = {
    rtp = {
      -- runtimepath の設定をリセットせず、すべてのパスを保持
      reset = false,
    },
  },
})
{{< /code >}}
{{% /note %}}

ランタイムパスを追加する方法は柔軟性がありますが、上記のように設定がやや複雑になります。
できれば、前者のシンボリックリンク／ジャンクションを使う方法をお勧めします。

