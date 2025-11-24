---
title: "Neovim カスタマイズのために Lua 言語をざっと理解する"
url: "p/d3exkpu/"
date: "2025-03-05"
tags: ["lua", "vim"]
---

{{% private %}}
- https://neovim.io/doc/user/lua-guide.html
{{% /private %}}

Neovim を Lua スクリプトでカスタマイズできるようになるために、Lua 言語をざっと理解しておきます。


Lua スクリプトの実行方法
----

### コマンドモードから実行する (:lua)

ワンライナー程度であれば、Neovim のコマンドモードで **`:lua`** を使って直接 Lua コードを実行できます。

{{< code lang="vim" title="コマンドーモードから Lua スクリプトを実行" >}}
:lua print("Hello")
{{< /code >}}

Neovim の設定を変更するときにも使えます。

{{< code lang="vim" title="コマンドーモードからオプションを設定" >}}
:lua vim.opt.number = true
{{< /code >}}

### コマンドラインから実行する (nvim -l)

Neovim 自体が Lua の実行環境を備えているので、次のように **`nvim -l`** コマンドで Lua スクリプトを実行できます。

{{< code lang="lua" title="hello.lua" >}}
print("Hello, Lua!")
{{< /code >}}

{{< code lang="console" title="Lua スクリプトファイルを実行" >}}
$ nvim -l hello.lua
Hello, Lua!
{{< /code >}}

### 編集中のスクリプトを実行する (:luafile %)

Neovim で Lua スクリプトを編集しているときに、そのバッファの内容を実行する場合は次のようにします。

{{< code lang="vim" title="カレントバッファの内容を実行" >}}
:luafile %
{{< /code >}}

Neovim の設定ファイル (`init.lua`) で以下のような設定をしておけば、`.lua` ファイルを編集中に（ノーマルモードで） **`F5`** キーを押すだけで実行できるようになります。

{{< code lang="lua" title="~/.config/nvim/init.lua" >}}
--- .lua ファイルを編集中に <F5> キーで実行
vim.api.nvim_create_autocmd("FileType", {
  pattern = "lua",
  callback = function()
    vim.keymap.set('n', '<F5>', '<cmd>luafile %<CR>', { buffer = true })
  end
})
{{< /code >}}

### VS Code で実行する

Visual Studio Code で Lua スクリプトを編集する場合は、[Code Runner 拡張](https://marketplace.visualstudio.com/items?itemName=formulahendry.code-runner) をインストールしておくと便利です。
Code Runner 拡張を使うと、各種言語のコードを編集中にショートカットキー (**`Ctrl+Alt+N`**) でそのコードを実行できるようになります（あるいはコマンドパレットから **Run Code** を実行）。
今回は Lua の実行環境として `nvim -l` コマンドを使うので、VS Code の設定ファイルで以下のように設定しておきます。

{{< code lang="js" title="settings.json" hl_lines="8" >}}
{
  /*
   * Code Runner 拡張用の設定
   */
  "code-runner.executorMap": {
    "go": "go run",
    "javascript": "node",
    "lua": "nvim -l",
    "python": "python"
  },
  "code-runner.saveFileBeforeRun": true, // 実行前にカレントファイルを保存する
  "code-runner.runInTerminal": true, // OUTPUT ではなく TERMINAL タブで実行する
  // ...
}
{{< /code >}}


コメント
----

Lua のコメントは 2 つのハイフン (`-`) で始まります。

```lua
-- 行末コメント

--[[
    これは複数行のコメントです。
    ブロックコメントとも言うよ。
]]
```


変数とデータ型
----

### 変数の定義

`local` を付けた場合はローカル変数、付けない場合はグローバル変数になります。
変数には任意の型の値を代入できます。

```lua
local my_local = 200
my_global = 100
```

一行でまとめて初期化できます。

```lua
local one, two, three = "one", "two", "three"
```

### データ型

Lua のデータ型は **`type`** 関数で調べられます。

```lua
print(type(nil))     --> nil（初期化されていない変数は nil になる）
print(type(true))    --> boolean
print(type(100))     --> number
print(type(0.1))     --> number
print(type("Hello")) --> string
print(type(print))   --> function
print(type({}))      --> table
```

Lua では数値の `0` や空文字列 `""` を真偽値 (boolean) として評価すると真値 (= `true`) とみなされることに注意してください。

```lua
if 0 then
    print("0 は true")
end

if "" then
    print("空文字列も true")
end

if {} then
    print("空テーブルも true")
end
```

`nil` や `false` は偽値として扱われます。

### 文字列

文字列はシングルクォート (`'`) かダブルクォート (`"`) で囲んで表現します。
複数行の文字列は `[[` と `]]` で囲みます。

```lua
local s = "hello"
local s = 'world'
local s = [[
複数行の文字列データはこのように記述します。
いわゆるヒアドキュメントです。
]]

-- フォーマット指定する場合は string.format を使います
local s = string.format("%06d", 1234)  -- > 001234
local s = string.format("%6d", 1234)   -- >   1234（右寄せ6桁）
local s = string.format("%-6d", 1234)  -- > 1234  （左寄せ6桁）
local s = string.format("%.4f", 0.12)  -- > 0.1200
local s = string.format("%X", 65535)   -- > FFFF
```

よく使う文字列操作には次のようなものがあります。
文字列変数のメソッドとして呼び出す方法 (`変数:XXX()`) と、関数の形で呼び出す方法 (`string.XXX()`) があります。

```lua
local str = "Hello World"

-- 文字列のサイズを取得する
print(#str) -- > 11
print(string.len("ABC")) -- > 3

-- 文字列を連結する
print(str .. " Goodbye") -- > Hello World Goodbye
print("AAA" .. "BBB") -- > AAABBB

-- 文字列を検索する
local first, last = string.find("foo bar hoge", "bar") -- > 5, 7
local first, last = string.find("foo bar hoge", "ZZZ") -- > nil, nil

-- 文字列の一部を取り出す
print(str:sub(1, 5)) -- > Hello
print(str:sub(7)) -- > World
print(str:sub(-5)) -- > World
print(string.sub("ABCDE", 2, 4)) -- > BCD

-- 文字列を置換する
local replaced = str:gsub("World", "Lua")
print(replaced) --> Hello Lua

-- 2つ目の戻り値で置換された数を取得できる
local replaced, count = string.gsub("foo bar foo", "foo", "AAA")
print(replaced) -- > AAA bar AAA
print(count) -- > 2

-- 大文字・小文字変換
print(str:upper()) -- > HELLO WORLD
print(str:lower()) -- > hello world

-- フォーマット
print(string.format("Hello %s %d", "World", 123)) -- > Hello World 123

-- パターンマッチ
local matched = string.match("Hello 123", "%d+")
print(matched) -- > 123

-- バイトコード
print(string.byte("ABC", 1)) -- > 65
print(string.char(65)) -- > A
print(string.char(65, 66, 67)) -- > ABC
print(string.len("ABCあ")) -- > 3

-- 繰り返し
print(string.rep("*", 8)) -- > ********
```

### テーブル (Table)

Lua には配列や連想配列を表現するためのテーブル (table) があります。

配列のインデックスは **1** から始まることに気をつけてください。
**`#arr`** とすると配列 `arr` の要素数を取り出せます。

{{< code lang="lua" title="配列としてのテーブル" >}}
local arr = {"AAA", "BBB", "CCC"}
arr[1] = "ZZZ"

-- ループ処理
for i = 1, #arr do
    print(arr[i])
end

-- インデックス付きループ処理
for i, v in ipairs(arr) do
    print(i, v)
end
{{< /code >}}

{{< code title="実行結果" >}}
ZZZ
BBB
CCC
1 ZZZ
2 BBB
3 CCC
{{< /code >}}

キー名と値のペアを指定することで、連想配列として使用できます。

{{< code lang="lua" title="連想配列としてのテーブル" >}}
local user = {
    name = "Maku",
    age = 14
}

-- キー名によるアクセス
print("name = " .. user.name)
print("age = " .. user.age)

-- キーと値でループ処理
for key, value in pairs(user) do
    print(key .. " = " .. value)
end
{{< /code >}}

{{< code title="実行結果" >}}
name = Maku
age = 14
name = Maku
age = 14
{{< /code >}}

配列と連想配列の両方の要素を持つテーブルも作成できます。

{{< code lang="lua" title="配列と連想配列を持つテーブル" >}}
local data = {
    "AAA",
    "BBB",
    name = "Maku",
    age = 14
}

-- キーと値ですべての要素をループ処理
for key, val in pairs(data) do
    print(key, val)
end

-- インデックスでアクセスできる要素のみをループ処理
for i, val in ipairs(data) do
    print(i, val)
end
{{< /code >}}

{{< code title="実行結果" >}}
1 AAA
2 BBB
name Maku
age 14
1 AAA
2 BBB
{{< /code >}}

テーブルにセットされたメソッドを呼び出す場合は、**`変数名:メソッド名`** のようにします。
ドットではなく、コロン (`:`) を使うことに注意してください。

{{< code lang="lua" title="テーブルのメソッドを呼び出す" >}}
local obj = {
    name = "Maku",
    greet = function(self)
        print("I am " .. self.name)
    end
}

obj:greet()  -- > I am Maku
obj.name = "Hemumu"
obj:greet()  -- > I am Hemumu
{{< /code >}}


関数 (Function)
----

### 関数の基本

Lua で関数を定義するには **`function`** キーワードを仕様します。

```lua
local function greet(name)
    print("Hello, " .. name)
end

greet("Maku") --> Hello, Maku
```

### 多値関数

Lua の関数は複数の戻り値を返すことができます。

```lua
-- 数値配列から最大値とそのインデックスを返します
function findMaxValueAndIndex(arr)
    local max = -math.huge
    local index = -1
    for i = 1, #arr do
        if arr[i] > max then
            max = arr[i]
            index = i
        end
    end
    return max, index
end

-- 使用例
local arr = {1, 3, 5, 2, 4}
local max, index = findMaxValueAndIndex(arr) -- > 5, 3
```

特定の位置の戻り値だけを取得したいときは、不要な戻り値を **`_`** で受け取るか、**`select`** 関数で取得したい戻り値のインデックスを指定します。

```lua
local _, index = findMaxValueAndIndex(arr) -- > 3
local index = select(2, findMaxValueAndIndex(arr)) -- > 3
```

### 可変長引数

可変長引数は **`...`** で表現します。

```lua
-- 任意の数の数値の平均値を計算します。
function average(...)
    local args = {...}
    local total = 0
    for _, v in ipairs(args) do
        total = total + v
    end
    return total / #args
end

-- 使用例
print(average(2, 2, 5, 10)) -- > 4.75
```

### デフォルト引数

`or` 演算子を利用して、引数のデフォルト値を表現することができます（指定されなかった引数が `nil` になることを利用しています）。

```lua
function greet(name, message)
    message = message or "Hello"
    print(message .. ", " .. name)
end

greet("Maku") --> Hello, Maku
greet("Maku", "Goodbye") --> Goodbye, Maku
```

### 名前付き引数

Lua は関数の名前付き引数をサポートしていませんが、テーブルを渡すことで同様のことを実現できます。

```lua
function greet(args)
    local greeting = args.greeting or "Hello"
    local name = args.name or "Guest"
    print(greeting .. ", " .. name .. "!")
end

greet {
    greeting = "Hi",
    name = "Alice"
}
greet {
    name = "Bob"
}
greet {}
```

テーブルを引数として渡すときは、上記のように呼び出し時の `()` を省略できます。


演算子
----

### 数値演算子 (Arithmetic Operators)

数値演算子は `+`、`-`、`*`、`/`、`/`、`%`、`^` が使えます。

```lua
print(1 + 2)   -- > 3
print(5 - 3)   -- > 2
print(2 * 3)   -- > 6
print(10 / 3)  -- > 3.3333333333333
print(10 % 3)  -- > 1
print(2 ^ 10)  -- > 1024
print(2 ^ 0.5) -- > 1.4142135623731
```

Lua には `++`、`--`、`+=`、`-=` などの演算子はないので、数値をインクリメントしたい場合は `a = a + 1` のように記述します）。

### 比較演算子 (Comparison Operators)

比較演算子は Java や C とほぼ同じですが、等しくないことを表す演算子は `!=` ではなく **`~=`** です。

```lua
-- 数値の比較
print(1 == 2) -- > false
print(1 ~= 2) -- > true
print(1 < 2)  -- > true
print(1 <= 2) -- > true
print(1 > 2)  -- > false
print(1 >= 2) -- > false

-- 文字列の比較
print("AAA" == "BBB") -- > false
print("AAA" ~= "BBB") -- > true
print("A" <= "B")     -- > true
print("01" < "20")    -- > true

-- 数値と文字列を比較すると false
print(1 == "1") -- > false
```

### 論理演算子 (Logical Operators)

論理演算子は **`and`**、**`or`**、**`not`** が使えます。

```lua
print(true and true)  -- > true
print(true and false) -- > false
print(true or true)   -- > true
print(true or false)  -- > true
print(not true)       -- > false
print(not false)      -- > true
```

論理演算子で繋げた式の結果は、最後に評価された式の値になります。
具体的には、`or` の場合は最初に真 (`true`) と評価された値、`and` の場合は最初に偽 (`false`) と評価された値になります。
すべての値が偽 (`false`) と評価された場合は、一番最後の値が返されます。

```lua
print(nil or "AAA" or false) -- > AAA（最初に真と評価された値）
print(false or nil)          -- > nil（すべて偽なので最後の値）
print(0 and false and 1)     -- > false （最初に偽と評価された値）
print(nil and false)         -- > false（すべて偽なので最後の値）
```


制御構文
----

### if-else

```lua
if a > 0 then
    print("a is a positive number")
elseif a < 0 then
    print("a is a negative number")
else
    print("a is zero")
end
```

### for ループ

```lua
-- 1 から 10 までの数を表示
for i = 1, 10 do
    print(i)
end

-- ステップ (-1) を指定して 10 から 1 までの数を表示
for i = 10, 1, -1 do
    print(i)
end
```

### while ループ

```lua
local i = 1

-- i が 10 以下の間ループ
while i <= 10 do
    print(i)
    i = i + 1
end
```

### repeat - until ループ

```lua
local i = 1

-- i が 10 を超えるまでループ
repeat
    print(i)
    i = i + 1
until i > 10
```


もっと学ぶ
----

以上の文法をざっと理解すれば、ほとんどの Lua スクリプトを読み書きできるはずです。
さらに Lua 言語を学びたい場合は、公式のドキュメントを参照してください。

- https://www.lua.org/

