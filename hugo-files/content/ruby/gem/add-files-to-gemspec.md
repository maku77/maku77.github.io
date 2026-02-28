---
title: "Rubyメモ: Gem パッケージの gemspec にファイルを追加し忘れるのを防ぐ"
url: "p/sa4jekt/"
date: "2017-08-23"
tags: ["ruby"]
aliases: ["/ruby/gem/add-files-to-gemspec.html"]
---

Gem パッケージを作成するときは、パッケージに含めるファイルを gemspec ファイルの `files` に列挙しておく必要があります。

{{< code lang="ruby" title="hello_gem.gemspec" >}}
Gem::Specification.new do |s|
  s.name    = 'hello_gem'
  s.version = '0.0.1'
  #...
  s.files   = ['lib/hello_gem.rb']
end
{{< /code >}}

上記のように 1 つずつファイル名を列挙していく方法ですと、Gem パッケージに含めたいファイルを追加したときに、gemspec の更新をし忘れてしまうことがあります。
下記のように、ワイルドカードを使用してファイル名を指定しておけば、ファイルの追加し忘れを防ぐことができます。

```ruby
Gem::Specification.new do |s|
  #...
  s.files = Dir['lib/**/*.rb', 'bin/*', 'LICENSE', '*.md']
end
```
