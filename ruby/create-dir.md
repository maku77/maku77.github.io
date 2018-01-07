---
title: ディレクトリを作成／削除する
date: "2010-05-07"
---

ディレクトリ作成／削除のための API
====

ディレクトリの作成や削除を行いたい場合は、`Dir` クラスの下記のメソッドを使用します。

* ディレクトリの作成
  - `Dir.mkdir(path)`
* ディレクトリの削除
  - `Dir.delete(path)`
  - `Dir.rmdir(path)`
  - `Dir.unlink(path)`

ディレクトリ作成の例
====

カレントディレクトリ以下にディレクトリを作成する
----
下記は、カレントディレクトリに sample ディレクトリを作成する例です。

```ruby
Dir.mkdir('./sample')
```

すでにディレクトリがあるなどの理由で、ディレクトリの作成に失敗した場合は、`SystemCallError` が発生します。
この例外を捕捉する必要がある場合は、以下のようにします。

```ruby
begin
  Dir.mkdir('./sample')
rescue => e
  STDERR.puts e.message
end
```

ディレクトリの有無を先にチェックしておけば、すでに存在する場合のエラーを防止することができます。
この場合でも、ディレクトリの作成に失敗することはある（権限がないなど）ので、きちんと例外処理をしておくのが望ましいです。

```ruby
begin
  Dir.mkdir(path) if not Dir.exist?(path)
rescue => e
  STDERR.puts 'エラー: ディレクトリを作成できません'
  exit 1  # 継続不能であればプログラム終了
end
```

HOME ディレクトリ以下にディレクトリを作成する
----

#### 例: ~/.myconf ディレクトリをパーミッション 0700 で作成

```ruby
path = File.join(Dir.home, '.myconf')
Dir.mkdir(path), 0700) if not Dir.exist?(path)
```

