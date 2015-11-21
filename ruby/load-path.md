---
title: Ruby のライブラリの検索パスを調べる
created: 2015-11-21
---

Ruby を実行しているとき `$LOAD_PATH` という変数を参照すると、Ruby の実行環境がどのようなディレクトリからライブラリを検索するかを調べることができます。
下記は、著者の Mac OSX で確認したときの例です。

```
$ ruby -e 'puts $LOAD_PATH'
/Library/Ruby/Site/2.0.0
/Library/Ruby/Site/2.0.0/x86_64-darwin15
/Library/Ruby/Site/2.0.0/universal-darwin15
/Library/Ruby/Site
/System/Library/Frameworks/Ruby.framework/Versions/2.0/usr/lib/ruby/vendor_ruby/2.0.0
/System/Library/Frameworks/Ruby.framework/Versions/2.0/usr/lib/ruby/vendor_ruby/2.0.0/x86_64-darwin15
/System/Library/Frameworks/Ruby.framework/Versions/2.0/usr/lib/ruby/vendor_ruby/2.0.0/universal-darwin15
/System/Library/Frameworks/Ruby.framework/Versions/2.0/usr/lib/ruby/vendor_ruby
/System/Library/Frameworks/Ruby.framework/Versions/2.0/usr/lib/ruby/2.0.0
/System/Library/Frameworks/Ruby.framework/Versions/2.0/usr/lib/ruby/2.0.0/x86_64-darwin15
/System/Library/Frameworks/Ruby.framework/Versions/2.0/usr/lib/ruby/2.0.0/universal-darwin15
```

