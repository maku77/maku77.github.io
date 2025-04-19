---
title: "Python の型の一覧、ある値の型を調べる (type)"
url: "p/a8cta8q/"
date: "2019-05-29"
tags: ["Python"]
changes:
  - 2025-01-25: 体裁を修正
aliases: /python/syntax/types.html
---

Python の組み込み型
----

Python の主な組み込み型は、数値、シーケンス、マッピング、クラス、インスタンス、および例外です。

### 数値型

**`int`**
: 整数（精度なし）。Python2 には `long` 型というものがありましたが、Python3 で `int` に統合されました。Python3 の `int` には精度の制限はなく、巨大な整数を保持することができます。

**`float`**
: 浮動小数点数（現在のシステムにおける精度は `sys.float_info.mant_dig` で確認できる、多くの場合 53 ビットの精度）

**`complex`**
: 複素数

標準ライブラリ
: - **`fractions`** ... 分数
  - **`decimal`** ... ユーザ定義の精度の浮動小数点数

### 論理型

**`bool`**
: `True` or `False`（`and`、`or`、`not` 演算が可能）。`int` のサブタイプ。Python の `bool` 型は `int` 型を継承しているのが特徴的です。`bool` オブジェクトから `int` クラスのメソッドを呼び出すことができます。

### シーケンス

**`list`**
: 可変 (mutable) なシーケンス

**`tuple`**
: 不変 (immutable) なシーケンス

**`range`**
: 範囲を示すシーケンス（数の不変なシーケンス）

### 文字列型

**`str`**
: テキストを示す不変 (immutable) なシーケンス


ある値・変数の型を調べる (type)
----

**`type`** 関数を使用すると、パラメータで渡した値の型を調べることができます。

{{< code lang="python" title="基本型の型名を調べる" >}}
print(type(100))        #=> <class 'int'>
print(type(1.5))        #=> <class 'float'>
print(type(1+2j))       #=> <class 'complex'>
print(type(True))       #=> <class 'bool'>
print(type('ABC'))      #=> <class 'str'>
print(type([0, 1, 2]))  #=> <class 'list'>
print(type((0, 1, 2)))  #=> <class 'tuple'>
print(type(range(10)))  #=> <class 'range'>
{{< /code >}}

ちなみに、`type` 関数が返すオブジェクトの型は `type` で、このオブジェクトを **型オブジェクト (Type object)** と呼びます。

```python
print(type(type(1)))  #=> <class 'type'>
```

`type` 関数でビルトイン関数の型を調べると下記のような結果になります。

```python
print(type(print))  #=> <class 'builtin_function_or_method'>
```

Python の変数定義では型を省略して記述できますが、クラスライブラリを使用する場合は、関数の戻り値がどのクラスのインスタンスなのかを把握するようにすると、理解が進みやすくなります。

{{< code lang="python" title="例: urlopen() の戻り値の型を調べる" >}}
import urllib.request

request = urllib.request.urlopen('https://example.com/')
print(type(request))  #=> <class 'http.client.HTTPResponse'>
{{< /code >}}

