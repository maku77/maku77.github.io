---
title: インデント用の文字列を作成する
created: 2011-07-29
layout: ruby
---

Ruby では文字列に対して `*` 演算子を適用することで、その文字列を指定回数だけ繰り返した文字列を生成することができます。
下記の `indent_str` 関数は、指定されたレベルのインデント用スペース文字列を作成します。

```ruby
def indent_str(level)
  return '    ' * level
end
```

#### 実行例

```ruby
indent_str(2)  #=> '        '
```

