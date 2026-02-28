---
title: "Rubyメモ: 文字列からある文字を取り除く (delete/gsub)"
url: "p/6fpfhwj/"
date: "2011-11-02"
tags: ["ruby"]
aliases: ["/ruby/string/delete.html"]
---

文字列内の指定した文字を取り除くには、`String#delete` を使用するか、`String#gsub` で指定した文字を空文字に置換します。

```
String#delete!('A')
String#gsub!('A', '')
```

{{% private note %}}
例: 文字列内の改行を取り除く
{{% /private %}}
```ruby
s = <<END
aaaaaaaaaaaa
bbbbbbbbbbbb
cccccccccccccc
END

s.delete!("\n")
```
