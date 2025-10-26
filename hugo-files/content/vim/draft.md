---
title: "DRAFT: Vim/Neovim メモ"
url: "p/2gstdsd/"
date: "2014-01-27"
lastmod: "2025-03-03"
tags: ["Vim"]
draft: true
---

Neovim メモ
----

- nvim ではシンタックスハイライトがデフォルトで有効 (on) になっています。
  これを無効 (off) にするには、`init.vim` で `:syntax off` と設定します。
- nvim では `autoindent` がデフォルトで有効 (on) になっています。
- nvim では、 **`:w ++p`** で上位ディレクトリが自動生成されます。

### Lua コードを実行する (:lua)

Neovim のコマンドラインから Lua コードを実行するには、**`:lua`** コマンドを使用します。

{{< code lang="lua" title="Neovim 内での Lua コードの実行" >}}
:lua print("Hello, Lua!")
{{< /code >}}

### 変数やテーブルの値を確認する (:lua=)

Neovim のコマンドラインから変数の内容を確認するには、**`:lua=`** を使用します。

{{< code lang="lua" title="package の内容を確認する" >}}
:lua = package   -- あるいは
:lua vim.print(package)
{{< /code >}}

### Lua で記述したスクリプトファイルを実行する (:source)

Neovim のコマンドラインから Lua スクリプトファイルを実行するには、**`:luafile`** あるいは **`:source`** を使用します。
`:source` の方は、Vim スクリプトファイルを実行するときにも使用できます。

{{< code lang="lua" title="Lua スクリプトファイルを実行する" >}}
:luafile ~/path/to/script.lua
:source ~/path/to/script.lua
{{< /code >}}

編集中の（自分自身の）`.lua` ファイルを実行するには、**`%`** を使って以下のようにします。

{{< code lang="lua" title="自分自身を実行する" >}}
:luafile %
:source %
{{< /code >}}

### テーブル (table)

Lua では、テーブル（table）というデータ構造を使用して、連想配列やリストを表現します。

{{< code lang="lua" title="テーブルの定義" >}}
local person = {
  name = "Alice",
  age = 30
}

print(person.name)  -- Alice
print(person.age)   -- 30
{{< /code >}}

{{< code lang="lua" title="テーブル要素をループ処理する" >}}
-- key & value のペアでループ
local person = {
  name = "Alice",
  age = 30
}
for key, val in pairs(person) do
  print(key, val)
end

-- インデックスを付けてループ
local arr = {"AAA", "BBB", "CCC"}
for i, val in ipairs(arr) do
  print(i, val)
end
{{< /code >}}

### Lua モジュール

`runtimepath` の通ったディレクトリ（`~/.config/nvim` や `~/AppData/Local/nvim`）に **`lua`** ディレクトリを作成し、その中に任意の Lua モジュールを配置すると、**`require`** で動的に読み込めるようになります。
以下の例では、`~/.config/nvim/lua/mymodule.lua` という Lua モジュールを作成して、`require("mymodule")` で読み込んでいます。

{{< code lang="lua" title="~/.config/nvim/lua/mymodule.lua" >}}
local M = {}

function M.hello()
  print("Hello, Lua module!")
end

return M
{{< /code >}}

{{< code lang="lua" title="Lua モジュールを読み込んで利用する" >}}
local m = require("mymodule")
m.hello()
{{< /code >}}

サブディレクトリに配置したモジュールのパスは、ドット (**`.`**) あるいはスラッシュ (**`/`**) をセパレータ文字として区切ります。
例えば、`~/.config/nvim/lua/mydir/mymodule.lua` というパスに配置したモジュールを読み込むには、以下のいずれかの方法で指定します。

```lua
local m = require("mydir.mymodule")
local m = require("mydir/mymodule")
```

ディレクトリ内に **`init.lua`** という名前のファイルとして配置すると、ディレクトリ名を指定するだけで読み込むことができます。

```lua
local m = require("mydir")  -- mydir/init.lua を読み込む
```

`require()` で読み込もうとした Lua ファイルが存在しなかったり、構文エラーがあったりすると、今実行中のスクリプトはエラー終了します。
エラーをキャッチして処理を続行したい場合は、`pcall()` を使って以下のようにします。

```lua
local ok, m = pcall(require, "mymodule")
if not ok then
  print("Error:", m)
else
  m.hello()
end
```

### Lua スクリプトから Vim スクリプト関数を呼び出す (vim.fn)

Vim のスクリプト関数を Lua から呼び出すには、**`vim.fn`** を使用します。
組み込み関数とユーザー関数のどちらも呼び出すことができます。

{{< code lang="lua" title="Lua から Vim スクリプト関数を呼び出す" >}}
print(vim.fn.printf("Hello from %s", "Lua"))  -- Hello from Lua

local reversed_list = vim.fn.reverse({ "A", "B", "C" })
vim.print(reversed_list)  -- { "C", "B", "A" }

local function print_stdout(chan_id, data, name)
    print(data[1])
end
vim.fn.jobstart("ls", { on_stdout = print_stdout })
{{< /code >}}

### Lua スクリプトから Vim コマンドを呼び出す (vim.cmd)

Vim のコマンドを Lua から呼び出すには、**`vim.cmd`** を使用します。

```lua
vim.cmd("set number")           -- 行番号を表示
vim.cmd("colorscheme habamax")  -- カラースキームを読み込む
vim.cmd("%s/foo/bar/g")         -- 文字列置換
```

Lua のリテラル文字列 (literal strings) の構文 (**`[[`** 〜 **`]]`**) を使うと、複数行に渡る Vim コマンドをまとめて記述することができます。

```lua
vim.cmd[[
  set number
  colorscheme habamax
  highlight link Warning Error
]]
```

`vim.cmd` は次のように使うこともできます。

```lua
vim.cmd.set("number")
vim.cmd.colorscheme("habamax")
vim.cmd.highlight({ "Error", "guibg=red" })
vim.cmd.highlight({ "link", "Warning", "Error" })
```

### Vim スクリプトに Lua スクリプトを埋め込む

{{< private >}}
- https://neovim.io/doc/user/lua.html#%3Alua-heredoc
{{< /private >}}

```lua
lua << EOF
  local name = "Alice"
  print("Hello, " .. name)
EOF
```

### Neovim の起動時に任意の Lua スクリプトを実行する (plugin)

Neovim の runtimepath の通ったディレクトリ（`~/.config/nvim` や `~/AppData/Local/nvim`）に **`plugin`** ディレクトリを作成し、そこに任意の Lua スクリプトを配置すると、Neovim の起動時に自動的に読み込んでくれます。

{{< code lang="lua" title="~/.config/nvim/plugin/hello.lua" >}}
print("Hello, Neovim!")
{{< /code >}}


文字列と数値の比較／文字列と文字列の比較
----

文字列と数値の比較をしようとすると、文字列が整数値に変換されてから比較が行われます。

```
if '0' == 0      => true  （内部では 0 == 0）
if '0' == 0.0    => true  （内部では 0 == 0.0）
if '0.1' == 0    => true! （内部では 0 == 0）
if '0.1' == 0.1  => false!（内部では 0 == 0.1）
if 'aaa' == 0    => true! （内部では 0 == 0）
if '1AB' == 1    => true! （内部では 1 == 1）
```

文字列同士の比較では、厳密に文字列の一致を調べます。

```
if 'abc' == 'abc'  => true
if '0.0' == '0'    => false
```

文字列同士を比較するときの、大文字、小文字の区別は演算子で指定できます。

```
if 'abc' ==# 'ABC'  => false（大文字・小文字を区別する）
if 'abc' ==? 'ABC'  => true（大文字・小文字を区別しない）
if 'abc' == 'ABC'   => ignorecase がセットされている場合は true
```

単純な == による比較は、ignorecase の設定により結果が左右してしまうので、通常は ==# を使った厳密な比較を行うのがよいでしょう。


文字列が空かどうかを調べる
----

```
if empty(str)
    echo 'Empty'
endif
```

あるいは、

```
if str == ''
    echo 'Empty'
endif
```

empty() は、数値が 0 であること、リスト要素、辞書要素が空であることの確認にも使用できます。
文字列が空でないことを調べるために、以下のようにするのは間違いです。

```
if str
    echo "Empty"
endif
```

ブール値のコンテキストに指定された文字列は整数値に変換されるため、ほとんどの場合 false と評価されてしまいます。

