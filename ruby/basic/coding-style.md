---
title: "Ruby のコーディングスタイル"
date: "2003-10-14"
---

命名規則
----

Ruby では変数名のプレフィックスに記号をつけることにより、グローバル変数、インスタンス変数、クラス変数を区別します。

| タイプ | 変数名のルール | 例 |
| ---- | ---- | ---- |
| ローカル変数 | 英数字で始まる | `local_var` |
| グローバル変数 | `$` で始まる | `$gloval_var` |
| インスタンス変数 | `@` で始まる | `@instance_var` |
| クラス変数 | `@@` で始まる | `@@class_var` |

大文字、小文字の使い分けについては次のようにすることが多いようです。

* クラス名は大文字で始める（例: `MyClass`）
* 変数名、メソッドは小文字で始める（例: `my_var`、`my_method`）
* 定数名はすべて大文字（例: `MY_CONSTANT`）


インデント／スペース
----

- インデントはスペース **2 文字**（Ruby 公式サイトと同様）
- 一行は **80 文字**まで


文字列リテラル
----

- 式展開の必要のない文字列リテラルは**シングルクォーテーション**で囲む（例: `'abc'`）
- 式展開の必要がある文字列リテラルは**ダブルクォーテーション**で囲む（例: `"Hello #{name}"`）


ファイル名
----

ファイル名はすべて小文字で、クラス名などの単語の区切りはアンダースコア (`_`) を使用する。
モジュールの階層はディレクトリ階層で表現する。

- `test_case.rb`（TestCase クラス）
- `unit/test_case.rb`（Unit モジュール以下の TestCase クラス）

このルールは、下記の RubyGems の命名規則を考慮しています。

RubyGems で公開する Gem パッケージの命名規則
----

RubyGems.org などで公開する Gem の名前は、フラットなファイル名で一意に命名する必要があるため、アンダースコアやハイフンの使い方がルール化されています。

| Gem name | Require statement | Main class or module |
| -------- | ----------------- | -------------------- |
| `ruby_parser` | `require 'ruby_parser'` | `RubyParser` |
| `rdoc-data` | `require 'rdoc/data'` | `RDoc::Data` |
| `net-http-persistent` | `require 'net/http/persistent'` | `Net::HTTP::Persistent` |
| `net-http-digest_auth` | `require 'net/http/digest_auth'` | `Net::HTTP::DigestAuth` |

- ファイルシステムへの依存を防ぐため、Gem 名はすべて小文字。
- モジュール名やクラス名の単語の区切りはアンダースコア (`_`) 。
- 既存モジュールを拡張する場合の区切りはハイフン (`-`)。実際のモジュールファイルはディレクトリ構造を作って格納する。
- 拡張が複数階層になった場合は、その分だけディレクトリ構造を掘り下げればよい。

例えば、`net-http-digest_auth` という名前の Gem が提供する `DigestAuth` クラスは、実際には下記のようなファイルとして作成されています。

#### net/http/digest_auth.rb

```ruby
class Net::HTTP::DigestAuth
  VERSION = '1.4'
  ...
end
```

上記では `Net::HTTP::DigestAuth` と、`::` 区切りでクラス定義していますが、上位のクラスが定義されていない場合は `class` 定義を入れ子構造で記述する必要があります。

クラスではなくて、モジュールを提供しているのであれば、例えば下記のようなファイルになります。

#### foo/bar/my_module.rb

```ruby
module Foo
  module Bar
    module MyModule
      def self.my_method
        ..
      end
    end
  end
end
```


参考サイト
----

* [Name your gem - RubyGems Guides](http://guides.rubygems.org/name-your-gem/)
* [GitHub の Ruby コーディングスタイル](https://github.com/styleguide/ruby)
* [LoveRuby.net コーディングスタイル](http://www.loveruby.net/w/RubyCodingStyle.html)
* [The Unofficial Ruby Usage Guide](http://www.caliban.org/ruby/rubyguide.shtml)

