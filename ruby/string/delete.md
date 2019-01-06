---
title: "文字列からある文字を取り除く (delete/gsub)"
date: "2011-11-02"
---

文字列内の指定した文字を取り除くには、`String#delete` を使用するか、`String#gsub` で指定した文字を空文字に置換します。

```
String#delete!('A')
String#gsub!('A', '')
```

#### 例: 文字列内の改行を取り除く

```ruby
s = <<END
aaaaaaaaaaaa
bbbbbbbbbbbb
cccccccccccccc
END

s.delete!("\n")
```

