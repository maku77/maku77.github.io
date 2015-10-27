---
title: キーボードからパスワードの入力を取得する
created: 2015-10-15
---

IO#noecho による入力表示の抑制
====

`IO#noecho` を利用すると、キーボードから入力した内容を画面上に表示させずに、入力を取得することができます。
下記のようにして、パスワードの入力プロンプトを表示することができます。

#### sample.rb

```ruby
require 'io/console'

def read_password
  print 'Input your password: '
  password = STDIN.noecho &:gets
  puts
  password.chomp
end

puts read_password
```

#### 実行結果

```
$ ruby sample.rb
Input your password: （キーボードから ABC と入力しても、その文字は表示されない）
ABC
```

`STDIN.noecho` でパスワードを入力した後は、改行が出力されないので、自力で `puts` を呼び出しています。また、`gets` で取得したパスワードの末尾には改行文字が残っているので、`chomp` で削除していることに注意してください。

