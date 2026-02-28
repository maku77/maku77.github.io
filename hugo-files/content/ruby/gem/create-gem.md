---
title: "Rubyメモ: Gem パッケージを作成する (1) 基本"
url: "p/k6gcrgj/"
date: "2015-12-04"
tags: ["ruby"]
aliases: ["/ruby/gem/create-gem.html"]
---

## 作成する Gem パッケージのゴール

ここでは、**hello_gem** という名前の Gem パッケージを作成してみます。
下記ができることをゴールとします。

1. `gem install` コマンドで Gem をインストールできる
2. Ruby スクリプトの中から `require 'hello_gem'` と読み込むと、`HelloGem` クラスを参照できるようになる

Gem に関する命名規則などは、[Ruby のコーディングスタイル](/p/7wv9hrf/) を参照してください。


## Gem パッケージを作成する

### Gem パッケージ内部の構成

Gem パッケージには、`xxx.gemspec` というファイルを作成する必要があります。
Ruby のソースコード自体は `lib` ディレクトリに格納するので、プロジェクトのディレクトリには下記のようなファイルが配置されることになります。

* hello_gem.gemspec
* lib/hello_gem.rb

上記のように `hello_gem.rb` を配置しておくことで、Gem のユーザは、`require 'hello_gem'` とロードできるようになります。

### スクリプトを作成する

下記は、メインのライブラリとなる `hello_gem.rb` の内容です。
単純に挨拶テキストを出力するだけの機能を提供します。

{{< code lang="ruby" title="lib/hello_gem.rb" >}}
class HelloGem
  def self.greet(name)
    puts "Hello #{name}!"
  end
end
{{< /code >}}

クラス名が複数の単語で構成される場合は、ファイル名の中ではアンダースコア (`_`) で単語を区切って表現するのが Gem の世界の慣例です。
上記の場合では、クラス名が `HelloGem` なので、ファイル名は `hello_gem.rb` となっています。
詳しいネーミングルールは[こちら](/p/7wv9hrf/)を参照してください。

ライブラリの作成が完了したら、簡単にテストしておきましょう。

```
$ irb -Ilib
irb(main):001:0> require 'hello_gem'
=> true
irb(main):002:0> HelloGem.greet('maku')
Hello maku!
=> nil
```

`irb` コマンドのパラメータに、`-Ilib` を指定していることに注意してください。
これは、`hello_gem.rb` の格納されている `lib` ディレクトリをロードパスに追加するためです。


### gemspec ファイルを作成する

gemspec ファイルには、公開する Gem パッケージの情報として、下記のような内容を記述しておきます。
詳細は [Specification Reference - RubyGems Guides](http://guides.rubygems.org/specification-reference/) を参照してください。

* Gem の名前 (name)
* バージョン (version)
* リリース日 (date)
* 概要 (summary)
* 詳細 (description)
* 作者の名前 (author / authors)
* 作者のメールアドレス (email)
* Web サイト (homepage)
* ライセンス (license)
* パッケージングするファイル (files)

{{< code lang="ruby" title="hello_gem.gemspec" >}}
Gem::Specification.new do |s|
  s.name        = 'hello_gem'
  s.version     = '0.0.1'
  s.date        = '2015-12-04'
  s.summary     = "Hello Gem!"
  s.description = "The first Gem package for practice"
  s.authors     = ["Maku Makkuma"]
  s.email       = 'maku@example.com'
  s.homepage    = 'http://example.com/hello_gem'
  s.license     = 'MIT'
  s.files       = ["lib/hello_gem.rb"]
end
{{< /code >}}

gemspec ファイルを作成したら、`gem build` コマンドでパッケージングできるようになります。
下記のように、パラメータに gemspec ファイルを指定して実行してください。

```
$ gem build hello_gem.gemspec
  Successfully built RubyGem
  Name: hello_gem
  Version: 0.0.1
  File: hello_gem-0.0.1.gem
```

これで、`hello_gem-0.0.1.gem` という Gem パッケージが完成しました。


## 作成した Gem パッケージを使用する

### gem ファイルを使ってインストールする

ここまでの作業で、ローカルディレクトリに `hello_gem-0.0.1.gem` という Gem パッケージが作成されました。
この Gem パッケージをインストールするには、下記のように `gem install` のパラメータにファイル名を指定して実行します。

```
$ gem install hello_gem-0.0.1.gem
Successfully installed hello_gem-0.0.1
Parsing documentation for hello_gem-0.0.1
Installing ri documentation for hello_gem-0.0.1
Done installing documentation for hello_gem after 0 seconds
1 gem installed
```

ちゃんとインストールされたか確認してみます。
ここでは、`hello` という名前で、ローカルの Gem を検索しています。

```
$ gem list -d hello

*** LOCAL GEMS ***

hello_gem (0.0.1)
    Author: Maku Makkuma
    Homepage: http://example.com/hello_gem
    License: MIT
    Installed at: C:/app/Ruby/lib/ruby/gems/2.2.0

    Hello Gem!
```

### Gem を使用する

ちゃんとインストールされているみたいなので、このパッケージを Ruby スクリプトから利用してみます。

{{< code lang="ruby" title="sample.rb" >}}
require 'hello_gem'

HelloGem.greet('maku')
{{< /code >}}

{{< code title="動作確認" >}}
$ ruby sample.rb
Hello maku!
{{< /code >}}

うまく動きました。

### Gem のアンインストール

インストールした Gem が必要ない場合は、下記のように削除しておきましょう。

```
$ gem uninstall hello_gem
Successfully uninstalled hello_gem-0.0.1
```
