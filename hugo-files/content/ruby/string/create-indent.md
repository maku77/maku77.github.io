---
title: "Rubyメモ: インデント用の文字列を作成する"
url: "p/s84ydu4/"
date: "2011-07-29"
tags: ["ruby"]
aliases: ["/ruby/string/create-indent.html"]
---

Ruby では文字列に対して `*` 演算子を適用することで、その文字列を指定回数だけ繰り返した文字列を生成することができます。
下記の `indent_str` 関数は、指定されたレベルのインデント用スペース文字列を作成します。

```ruby
def indent_str(level)
  return '    ' * level
end
```

{{% private note %}}
実行例
{{% /private %}}
```ruby
indent_str(2)  #=> '        '
```
