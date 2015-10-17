---
title: ソースコード内にテキストデータを埋め込む
created: 2003-10-05
layout: ruby
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

