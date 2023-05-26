---
title: "HTML ファイルや Markdown ファイルをプレーンテキストに変換する (unstructured)"
url: "p/uivwecs/"
date: "2023-05-26"
tags: ["Python"]
---

{{% private %}}
- [Unstructured documentation](https://unstructured-io.github.io/unstructured/)
{{% /private %}}

unstructured ライブラリとは
----

Python の __`unstructured`__ ライブラリは、様々な形式のデータを機械学習システムに投入する前の変換処理などを行ってくれるライブラリです。
例えば、非構造化データ（HTML や Markdown）には、機械学習には必要のないタグが含まれていたりしますが、`unstructured` を使ってテキストだけを取り出すことができます。

- [Unstructured-IO/unstructured: Open source libraries and APIs to build custom preprocessing pipelines for labeling, training, or production machine learning pipelines.](https://github.com/Unstructured-IO/unstructured/)

現在、ChatGPT などの大規模言語モデル (LLM) を利用したチャットシステムが注目を浴びていますが、社内ドキュメントなどの情報と統合するためには、ユーザー入力から類似ドキュメントを検索する仕組みが必要になります。
このために使用されるのが、テキスト間の類似度に基づいた検索システムであるベクトル検索 DB などです。
そこに登録するドキュメントのベクトルデータ（Embeddings）は、できるだけクリーンなテキストから生成することが望ましいのですが、`unstructured` ライブラリを使えば、様々な形式のデータからそういったテキストを作成できます。


unstructured ライブラリのインストール
----

`unstructured` の [Installation マニュアル](https://unstructured-io.github.io/unstructured/installing.html) に従って、必要なライブラリをインストールします。

{{< code lang="console" title="unstructured ライブラリのインストール" >}}
$ pip install unstructured
{{< /code >}}

後述のプログラムを実行したときに、`tabulate` が足りないと言われたので入れました。
このあたりは試行錯誤です。

```console
$ pip install tabulate
```


HTML ファイルをプレーンテキストに変換してみる
----

サンプルの入力ファイルとして、次のような HTML ファイルを用意します。
`html` や `body` タグを省略していますが、これも正しい HTML5 文書です。

{{< code lang="html" title="input.html（入力ファイル）" >}}
<!DOCTYPE html>
<meta charset="UTF-8" />
<title>Page Title</title>
<h1>Hello HTML!</h1>
<p>This is <b>bold</b> text.</p>
<ol>
  <li>Ordered list item 1</li>
  <li>Ordered list item 2</li>
  <li>
    Ordered list item 3
    <ul>
      <li>Unordered list item 1</li>
      <li>Unordered list item 2</li>
    </ul>
  </li>
</ol>
{{< /code >}}

このファイルを、`unstructured` ライブラリの __`partition`__ 関数に渡すと、`Element` オブジェクトのリストに変換して返してくれます。
`Element` オブジェクトは、構造的なまとまりごとのテキストを保持しています。
例えば、1 つの段落内のテキストです。

{{< code lang="python" title="main.py" >}}
from unstructured.partition.auto import partition

elems = partition(filename="input.html")  #-> List[Element]
text = "\n".join([str(e) for e in elems])
print(text)
{{< /code >}}

このプログラムを実行すると、うまくテキスト部分だけを抽出できていることが分かります。

{{< code title="出力結果" >}}
Hello HTML!
This is bold text.
Ordered list item 1
Ordered list item 2
Ordered list item 3

      Unordered list item 1
      Unordered list item 2

{{< /code >}}


Markdown ファイルをプレーンテキストに変換してみる
----

Markdown 形式のファイルも、HTML ファイルと同様に `partition` 関数で処理できます。

{{< code lang="md" title="input.md（入力ファイル）" >}}
---
title: "ページタイトル"
date: "2023-05-26"
---

セクション 1
----

文章内には __強調されたテキスト__ も含まれています。
次のようなリストも含まれています。

- 項目 1
  - 項目 1-1
  - 項目 1-2
- 項目 2
- 項目 3

表も含まれていることがあるよ。

| カラム1 | カラム2 | カラム3 |
| ---- | ---- | ---- |
| A-1 | A-2 | A-3 |
| B-1 | B-2 | B-3 |
{{< /code >}}

先ほどのプログラムの入力ファイル名を `input.md` に変えるだけで OK です。

{{< code lang="python" title="main.py" >}}
from unstructured.partition.auto import partition

elems = partition(filename="input.md")  #-> List[Element]
text = "\n".join([str(e) for e in elems])
print(text)
{{< /code >}}

{{< code title="出力結果" >}}
title: "ページタイトル"
date: "2023-05-26"
セクション 1
文章内には 強調されたテキスト も含まれています。
次のようなリストも含まれています。
項目 1
項目 1-1
項目 1-2
項目 2
項目 3
表も含まれていることがあるよ。
| カラム1 | カラム2 | カラム3 |
| ---- | ---- | ---- |
| A-1 | A-2 | A-3 |
| B-1 | B-2 | B-3 |
{{< /code >}}


HTML や Markdown 専用の関数を使う方法
----

前述の例では、入力ファイルの種類を自動判別する `unstructured.partition.auto.partition` 関数を使用しましたが、次のようなファイルタイプ別の関数も用意されています。

- HTML 用: [unstructured.partition.html.partition_html](https://unstructured-io.github.io/unstructured/bricks.html#partition-html) 関数
- Markdown 用: [unstructured.partition.md.partition_md](https://unstructured-io.github.io/unstructured/bricks.html#partition-md) 関数

これらの関数を使うと、ファイル名ではなく、文字列や URL で入力データを指定することができます。

```python
from unstructured.partition.html import partition_html
from unstructured.partition.md import partition_md

elems = partition_html(text="<p>It is OK.</p>")    # HTML 形式のテキスト
elems = partition_html(url="https://example.com")  # HTML を取得可能な URL

elems = partition_md(text=markdown_text)              # Markdown 形式のテキスト
elems = partition_md(url="https://example.com/1.md")  # Markdown を取得可能な URL
```

