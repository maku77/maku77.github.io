---
title: "Pythonメモ: Python で YAML ファイルを扱う"
url: "p/fy8j9h2/"
date: "2017-02-17"
lastmod: "2019-10-22"
tags: ["python"]
aliases: /python/io/yaml.html
---

[PyYaml](http://pyyaml.org/) というライブラリを使用すると、Python で簡単に YAML データを扱うことができます。

PyYaml ドキュメント: [http://pyyaml.org/wiki/PyYAMLDocumentation](http://pyyaml.org/wiki/PyYAMLDocumentation)


PyYaml のインストール
----

PyYaml は `pip` コマンドで簡単にインストールできます。

```console
$ pip install pyyaml
```


YAML ファイルを読み込んで Python オブジェクトに変換する
----

`yaml.safe_load()` を使用して、YAML ファイルを読み込むことができます。

{{< code lang="yaml" title="input.yaml" >}}
x: XXX
y: 100
z: [200, 300, 400]
{{< /code >}}

{{< code lang="python" title="sample.py" >}}
import yaml

with open('input.yaml', encoding='utf-8') as file:
    obj = yaml.safe_load(file)
    print(obj['z'])
{{< /code >}}

{{< code title="実行結果" >}}
[200, 300, 400]
{{< /code >}}

**追記 (2019-10-22)**: 以前は `yaml.load()` で YAML ファイルを読み込んでいましたが、[セキュリティ上の理由から deprecated](https://github.com/yaml/pyyaml/wiki/PyYAML-yaml.load(input)-Deprecation) になったため、`yaml.safe_load()` に置き換えました。PyYAML 5.1 以降で `yaml.load()` をそのまま使用すると、`YAMLLoadWarning` という警告が発生します。


### YAML パース時の例外をハンドルする

YAML ファイルが正しいフォーマットで記述されているかどうかわからない場合は、パース失敗時の例外をハンドルしておきましょう。

{{< code lang="python" title="sample.py" >}}
#!/usr/bin/env python3

import yaml
import sys

try:
    with open('sample.yaml', encoding='utf-8') as file:
        obj = yaml.safe_load(file)
        print(obj)
except Exception as e:
    print('Exception occurred while loading YAML...', file=sys.stderr)
    print(e, file=sys.stderr)
    sys.exit(1)
{{< /code >}}

この例では、ファイルのオープンに失敗した場合まで考慮して、`with open()` を含めて例外を捕捉しています。
`yaml.safe_load()` 部分だけの例外を捕捉するかどうかは、用途によって使い分けてください。

YAML ファイル内の記述に間違いがある場合は、下記のようにエラーを出力してプログラムが終了します。

```console
$ python sample.py
Exception occurred while loading YAML...
while scanning a simple key
  in "sample.md", line 4, column 1
could not find expected ':'
  in "sample.md", line 5, column 1
```


Python オブジェクトを YAML 文字列に変換する
----

`yaml.dump()` を使用すると、Python の任意のオブジェクトを YAML 形式のテキストに変換することができます。

{{< code lang="python" title="sample.py" >}}
import yaml

obj = { 'x': 'XXX', 'y': 100, 'z': [200, 300, 400] }
print(yaml.dump(obj))
{{< /code >}}

{{< code lang="yaml" title="実行結果" >}}
x: XXX
y: 100
z: [200, 300, 400]
{{< /code >}}

Python オブジェクトを YAML ファイルに出力する
----

ファイルへの保存も `yaml.dump()` を使って同様に実行できます。

```python
import yaml

obj = { 'x': 'XXX', 'y': 100, 'z': [200, 300, 400] }
with open('output.yaml', 'w', encoding='utf-8') as file:
    yaml.dump(obj, file)
```

Python オブジェクト内に日本語の文字列データを含む場合は、下記のようにして UTF-8 エンコーディング形式のテキストファイルに出力することができます。

```python
import yaml

obj = { 'x': 'あいうえお', 'y': [1, 2, 3] }
with open('output.yaml', 'w', encoding='utf-8') as f:
    yaml.dump(obj, f, allow_unicode=True)
```

{{< code lang="yaml" title="output.yaml" >}}
x: あいうえお
y: [1, 2, 3]
{{< /code >}}
