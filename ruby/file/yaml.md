---
title: Ruby で YAML ファイルを扱う
created: 2016-10-20
---

Ruby には [YAML ファイル](https://ja.wikipedia.org/wiki/YAML)を扱うためのモジュール `yaml` が標準で搭載されています。

YAML 形式の文字列をパースする
----

下記のように、`YAML.load` メソッドを使用することで、YAML 形式のテキストをパースして Ruby のオブジェクトにしてくれます。

#### sample.rb

```ruby
require 'yaml'

text = <<END
  key1: value1
  key2: value2
  key3:
    - value3-1
    - value3-2
    - value3-3
  key4: {a1: a2, b1: b2, c1: c2}
END

ym = YAML.load(text)
puts ym
```

#### 実行結果

```
$ ruby sample.rb
{"key1"=>"value1", "key2"=>"value2", "key3"=>["value3-1", "value3-2", "value3-3"], "key4"=>{"a1"=>"a2", "b1"=>"b2", "c1"=>"c2"}}
```

YAML ファイルを読み込む
----

`YAML.load_file` メソッドを使用すると、YAML 形式のテキストファイルを読み込んで Ruby のオブジェクトに変換してくれます。

#### books.yml

```yaml
- name: The Ruby Programming Language
  author: [David Flanagan, Yukihiro, Matsumoto]
- name: Ruby Cookbook
  author: [Lucas Carlson, Leonard Richardson]
```

#### sample.rb

```ruby
require 'yaml'

ym = YAML.load_file('input.yml')
p ym
```

#### 実行結果

```
$ ruby sample.rb
[{"name"=>"The Ruby Programming Language", "author"=>["David Flanagan", "Yukihiro", "Matsumoto"]}, {"name"=>"Ruby Cookbook", "author"=>["Lucas Carlson", "Leonard Richardson"]}]
```

Ruby のオブジェクトを YAML 形式のテキストに変換する
----

`yaml` モジュールをロードしておくと、Ruby のオブジェクトを YAML 形式のテキストに変換するための `Object#to_yaml` メソッドが使えるようになります。

#### sample.rb

```ruby
require 'yaml'

obj = {'key1' => 'value1', 'key2' => 'value2', 'key3' => [100, 200, 300]}
puts obj.to_yaml
```

#### 実行結果

```
$ ruby sample.rb
---
key1: value1
key2: value2
key3:
- 100
- 200
- 300
```

