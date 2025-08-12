---
title: "CPD でコードクローンを発見する"
date: 2014-06-29
url: "p/q4n32h5/"
tags: ["memo"]
aliases: ["/memo/tool/detect-code-clones.html"]
---

CPD とは
----

PMD (A source code analyzer) という静的解析ツールのパッケージに含まれている `cpd` コマンド (Copy/Paste Detector) を使用すると、ソースコードに含まれているコードクローンを簡単に探し出すことができます。
プロジェクトの規模が数万行を超えてきたときに、リファクタリングすべき箇所を探すための強い味方になります。

下記のように、主要な言語はほとんど対応しています。

* C++
* C#
* Fortran
* Go
* Java
* JavaScript
* JSP
* PHP
* PL/SQL
* Ruby
* XML and XSL


インストール
----

### PMD/CPD のダウンロード

* https://pmd.github.io/

からアーカイブ（`pmd-bin-5.2.0.zip` など）をダウンロードして、適当なディレクトリに展開します。

### Windows の場合

Windows の場合は、展開したディレクトリにある `bin` ディレクトリにパスを通しておけば、どこからでも `cpd` コマンドを実行できるようになります。

### Linux の場合

Linux の場合は、`bin/run.sh` という、統一された入口として使用するシェルスクリプトが用意されているので、デフォルトでは `bin/run.sh cpd` のように実行しなければいけません。`cpd` というコマンドとして alias 定義しておくのがよいでしょう。

{{< code lang="bash" title=".bash_profile" >}}
alias cpd='/path/to/pmd-bin/bin/run.sh cpd'
{{< /code >}}


実行方法
----

下記のように実行すると、`src_dir` ディレクトリ以下のソースコードに関して、コードクローンの検出を行います。

```
C:\> cpd --minimum-tokens 50 --files src_dir > results.txt
```

オプションの `--minimum-tokens` の値を小さくすると、クローン検出の閾値が下がるので、より多くの箇所を検出するようになります。

変数名や定数名の違いなどを吸収して検出するためには、`--ignore-identifiers` オプションを指定します。

