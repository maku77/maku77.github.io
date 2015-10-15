---
title: キーボードからの入力を取得する
created: 2015-10-15
layout: ruby
---

STDIN でキーボードからの入力を取得する
====

キーボードからのユーザ入力を一行分取得するには、`STDIN.gets` を使用します。
取得した入力の末尾には、改行文字が残ったままであることに注意してください。

```ruby
line = STDIN.gets
p line    #=> "ABC\n"
```

キーボードからの入力を繰り返し取得したい場合は、下記のような `while` ループで処理できます。
`Ctrl-Z` などで入力を終了させたときに、`IO#gets` が `nil` を返すことを利用してループを終了しています。

```ruby
while line = STDIN.gets
  puts line
end
```

readline モジュールでキーボードからの入力を取得する
====

readline モジュールを使用すると、プロンプトを表示しながらユーザ入力を取得する処理を簡単に記述することができます。

#### sample.rb
```ruby
require 'readline'

while line = Readline.readline('> ')
  puts line
end
```

#### 実行例
```
$ ruby sample.rb
> AAA
AAA
> BBB
BBB
>
```

また、readline モジュールによってキーボード入力を取得している最中は、`Ctrl-A` によるカーソルジャンプなど、**Emacs 風のキーボードショートカットが使える**ようになります。
さらに、下記のように第 2 引数を `true` にして呼び出すことで、`Ctrl-P`、`Ctrl-N` による入力の履歴を辿れるようになります。

```ruby
line = Readline.readline('> ', true)
```


