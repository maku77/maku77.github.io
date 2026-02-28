---
title: "Rubyメモ: キーボードからの入力を取得する (STDIN.gets/readline)"
url: "p/yf9e2j2/"
date: "2015-10-15"
tags: ["ruby"]
aliases: ["/ruby/io/input-from-keyboard.html"]
---

## STDIN でキーボードからの入力を取得する

キーボードからのユーザー入力を一行分取得するには、`STDIN.gets` を使用します。
取得した入力の末尾には改行文字が残ったままであることに注意してください。
改行文字は `chomp` を利用して削除できます。

```ruby
line = STDIN.gets
p line        #=> "ABC\n"
p line.chomp  #=> "ABC"
```

キーボードからの入力を繰り返し取得したい場合は、下記のような `while` ループで処理できます。
`Ctrl-Z` などで入力を終了させたときに、`IO#gets` が `nil` を返すことを利用してループを終了しています。

```ruby
while line = STDIN.gets
  puts line
end
```

## readline モジュールでキーボードからの入力を取得する

readline モジュールを使用すると、プロンプトを表示しながらユーザー入力を取得する処理を簡単に記述できます。

{{< code lang="ruby" title="sample.rb" >}}
require 'readline'

while line = Readline.readline('> ')
  puts line
end
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ ruby sample.rb
> AAA
AAA
> BBB
BBB
>
{{< /code >}}

また、readline モジュールでキーボード入力を取得している最中は、`Ctrl-A` によるカーソルジャンプなど、**Emacs 風のキーボードショートカットが使える**ようになります。
さらに、下記のように第 2 引数を `true` にして呼び出すことで、`Ctrl-P`、`Ctrl-N` で入力履歴をたどれるようになります。

```ruby
line = Readline.readline('> ', true)
```
