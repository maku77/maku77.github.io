---
title: "Gem パッケージを作成する (2) 実行可能コマンドを追加する"
date: "2015-12-04"
---

RubyGems には、実行可能なコマンドをインストールできるという面白い仕組みが備わっています。
ここでは、下記のようなことを実現してみます。

1. `gem install` コマンドでインストール可能な Gem パッケージを作成する
2. Gem をインストールし終わったら、`hello` というコマンドが使えるようになる

Gem に実行可能コマンドを追加する
====

Gem パッケージ内部の構成
----

実行可能コマンドをインストールできる Gem を作成するには、`bin` ディレクトリに実行可能ファイルを格納し、gemspec ファイルの `executables` フィールドにそのパスを登録するだけで OK です。
プロジェクトのディレクトリ構成は、例えば以下のようになります。

* bin/hello（実行可能なコマンド）
* lib/hello_gem.rb（スクリプト本体）
* hello_gem.gemspec（Gem 仕様）

実行ファイルの作成
----

実行可能ファイルは、どのような形式でもよいですが、ここでは Ruby スクリプトとして作成します。
Ruby スクリプトで作成しておけば、**Linux、MacOSX、Windows、どのプラットフォームでも実行可能**なコマンドとして作成できます。
Ruby スクリプトの中では、`lib/hello_gem.rb` が読み込めることを前提に記述して構いません。

#### bin/hello
```ruby
#!/usr/bin/env ruby
require 'hello_gem'

HelloGem.greet('maku')
```

スクリプトを直接実行できるようにするために、実行権限を付けておきます（Linux のみ）。

```
$ chmod a+x bin/hello
```

実行できるか確認します。

```
$ ruby -Ilib bin/hello
Hello maku!
```

gemspec に実行可能ファイルを追加する
----

作成した実行可能ファイル `hello` を gemspec ファイルに登録します。

#### hello_gem.gemspec

```ruby
Gem::Specification.new do |s|
  s.name        = 'hello_gem'
  s.version     = '0.0.1'
  s.date        = '2015-12-04'
  s.summary     = 'Hello Gem!'
  s.description = 'The first Gem package for practice'
  s.authors     = ['Maku Makkuma']
  s.email       = 'maku@example.com'
  s.homepage    = 'http://example.com/hello_gem'
  s.license     = 'MIT'
  s.files       = ['lib/hello_gem.rb']
  s.executables << 'hello'  # ★★★これを追加
end
```

実行可能ファイルの格納されているディレクトリのパスはどこにも指定されていないことに注目してください。
デフォルトは `bin` ディレクトリになっているので、上記のように何も指定しなくてよいのですが、もしディレクトリを変更する場合は、`bindir` フィールドでディレクトリを指定してください。

```ruby
  s.bindir     = 'exe'  # ★★★実行可能ファイルのディレクトリを変更
```

Gem のビルド
----

すべてのファイルを作成し終わったら、Gem パッケージをビルドします。

```
$ gem build hello_gem.gemspec
  Successfully built RubyGem
  Name: hello_gem
  Version: 0.0.1
  File: hello_gem-0.0.1.gem
```

作成した Gem パッケージを使用する
====

gem ファイルを使ってインストールする
----

作成した Gem ファイルを使って、以下のようにインストールすることができます。

```
$ gem install hello_gem-0.0.1.gem
$ gem install hello_gem  （こう省略することもできる）
```

すると、組み込みコマンドのように `hello` コマンドを実行できるようになります。

```
$ hello
Hello maku!
```

Windows でもコマンド実行できる仕組み
----

面白いのは、シェバング (`#!/usr/bin/env ruby`) を付けた Ruby スクリプトとして作成したものが、Windows でもコマンドとして実行できるようになっているところです。
実は、`gem install` するときに、パスの通った場所に同名のバッチファイル (`hello.bat`) が作成され、間接的に本体の Ruby スクリプト (`hello`) を実行するようになっています。
Windows 環境では、`hello` というコマンドを実行しようとすると、実際には `hello.bat` が実行されるので、これで万事うまくいくわけです。

下記は、Windows の `where` コマンドで、`hello` というファイルを探しています。

```
C:\> where hello
C:\Ruby\bin\hello
C:\Ruby\bin\hello.bat （★自動生成されたバッチファイル）
```

`hello.bat` が作成されていることが分かりますね。
RubyGems のこの仕組みのおかげで、様々な OS 上で実行可能なコマンドを簡単に提供できるようになります。
しかも、ほとんどのケースでは Ruby プログラムさえ書ければよいのです。至れり尽くせりですね！

