---
title: "ディレクトリ内のファイルを検索する"
date: "2010-05-07"
---

`Dir[patten]` や `Dir.glob(patten)` を使うと、ワイルドカードでファイルを検索して列挙することができます。
検索パターンの中では `*`、`?`、`[]`、`{}` などを使用できます。
以下の例では、カレントディレクトリ以下の html ファイル (`*.html`) を検索しています。

```ruby
Dir.glob('**/*.html') do |item|
  puts item
end
```

もちろん、指定したディレクトリを起点にして検索することもできます。
下記の例では、コマンドライン引数で指定したディレクトリを起点にして html ファイルを再帰的に検索します。

```ruby
path = ARGV[0] ? ARGV[0] : '.'

Dir.glob("#{path}/**/*.html") do |item|
  puts item
end
```

複数のパターンで OR 検索したい場合は、それぞれのパターンを `\0` で区切って指定します。
下記の例では、カレントディレクト以下の cpp ファイルと h ファイルを検索します。

```
Dir.glob("**/*.cpp\0**/*.h")
```

大量のファイルを含むディレクトリから、`File.glob` を使用してファイルを検索しようとすると、レスポンスが帰ってくるまでに非常に時間がかかります。
そのような場合は、`Dir.foreach` などを使用して自力で再帰検索することを検討してください。

