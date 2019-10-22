---
title: "Python で YAML ファイルを扱う"
date: "2017-02-17"
lastmod: "2019-10-22"
---

[PyYaml](http://pyyaml.org/) というライブラリを使用すると、Python で簡単に YAML データを扱うことができます。

PyYaml ドキュメント: [http://pyyaml.org/wiki/PyYAMLDocumentation](http://pyyaml.org/wiki/PyYAMLDocumentation)


PyYaml のインストール
----

PyYaml は `pip` コマンドで簡単にインストールできます。

```
$ pip install pyyaml
```


YAML ファイルを読み込んで Python オブジェクトに変換する
----

`yaml.safe_load()` を使用して、YAML ファイルを読み込むことができます。

#### input.yaml

~~~ yaml
x: XXX
y: 100
z: [200, 300, 400]
~~~

#### sample.py

~~~ python
import yaml

with open('input.yaml') as file:
    obj = yaml.safe_load(file)
    print(obj['z'])
~~~

#### 実行結果

~~~
[200, 300, 400]
~~~

<b>追記 (2019-10-22)</b>: 以前は `yaml.load()` で Yaml ファイルを読みこんでいましたが、[セキュリティ上の理由から deprecated](https://github.com/yaml/pyyaml/wiki/PyYAML-yaml.load(input)-Deprecation) になったため、`yaml.safe_load()` に置き換えました。PyYAML 5.1 以降で `yaml.load()` をそのまま使用すると、`YAMLLoadWarning` という警告が発生します。


### YAML パース時の Exception をハンドルする

YAML ファイルが正しいフォーマットで記述されているかどうかわからない場合は、パース失敗時の例外をハンドルしておきましょう。

#### sample.py

~~~ python
#!/usr/bin/env python3

import yaml
import sys

try:
    with open('sample.yaml') as file:
        obj = yaml.safe_load(file)
        print(obj)
except Exception as e:
    print('Exception occurred while loading YAML...', file=sys.stderr)
    print(e, file=sys.stderr)
    sys.exit(1)
~~~

この例では、ファイルのオープンができなかった場合まで考慮して、`with open()` ごと捕捉しています。
`yaml.safe_load()` 部分だけを捕捉するかどうかは用途によって使い分けてください。

YAML ファイル内の記述に間違いがある場合は、下記のようにエラー出力してプログラムが終了します。

~~~
$ python sample.py
Exception occurred while loading YAML...
while scanning a simple key
  in "sample.md", line 4, column 1
could not find expected ':'
  in "sample.md", line 5, column 1
~~~


Python オブジェクトを YAML 文字列に変換する
----

`yaml.dump()` を使用すると、Python の任意のオブジェクトを YAML 形式のテキストに変換することができます。

#### sample.py

~~~ python
import yaml

obj = { 'x': 'XXX', 'y': 100, 'z': [200, 300, 400] }
print(yaml.dump(obj))
~~~

#### 実行結果

~~~ yaml
x: XXX
y: 100
z: [200, 300, 400]
~~~

Python オブジェクトを YAML ファイルに出力する
----

ファイルへの保存も `yaml.dump()` を使って同様に実行できます。

~~~ python
import yaml

obj = { 'x': 'XXX', 'y': 100, 'z': [200, 300, 400] }
with open('output.yaml', 'w') as file:
    yaml.dump(obj, file)
~~~

Python オブジェクト内に日本語の文字列データを含む場合は、下記のようにして UTF-8 エンコーディング形式のテキストファイルに出力することができます。

~~~ python
import codecs
import yaml

obj = { 'x': 'あいうえお', 'y': [1, 2, 3] }
with codecs.open('output.yaml', 'w', 'utf-8') as f:
    yaml.dump(obj, f, encoding='utf-8', allow_unicode=True)
~~~

#### output.yaml

~~~ yaml
x: あいうえお
y: [1, 2, 3]
~~~

