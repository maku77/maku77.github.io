---
title: Gem パッケージの gemspec へのファイルの追加し忘れを防ぐ
date: "2017-08-23"
---

Gem パッケージを作成するときは、パッケージに含めるファイルを gemspec ファイルの `files` に列挙しておく必要があります。

#### hello_gem.gemspec

~~~ ruby
Gem::Specification.new do |s|
  s.name    = 'hello_gem'
  s.version = '0.0.1'
  #...
  s.files   = ['lib/hello_gem.rb']
end
~~~

上記のように１つずつファイル名を列挙していく方法ですと、Gem パッケージに含めたいファイルを追加したときに、gemspec の更新をし忘れてしまうことがあります。
下記のように、ワイルドカードを使用してファイル名を指定しておけば、ファイルの追加し忘れを防ぐことができます。

~~~ ruby
Gem::Specification.new do |s|
  #...
  s.files = Dir['lib/**/*.rb', 'bin/*', 'LICENSE', '*.md']
end
~~~

