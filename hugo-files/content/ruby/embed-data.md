---
title: "Rubyメモ: ソースコード内にテキストデータを埋め込む"
url: "p/m6zz7z2/"
date: "2003-10-05"
tags: ["ruby"]
aliases: ["/ruby/embed-data.html"]
---

`DATA` 変数を参照すると、ソースコード内の `__END__` 行以下に配置されたテキストデータを参照することができます。

```ruby
while line = DATA.gets
  print line
end

__END__
埋め込みテキストデータ
埋め込みテキストデータ
埋め込みテキストデータ
```

