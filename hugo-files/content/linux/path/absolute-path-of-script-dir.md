---
title: "Linuxメモ: シェルスクリプトのあるディレクトリの絶対パスを取得する"
url: "p/m5z4fi3/"
date: "2010-08-20"
tags: ["linux"]
lastmod: "2021-06-20"
aliases: /linux/path/absolute-path-of-script-dir.html
---

スクリプト自身の格納されているディレクトリの絶対パスは下記のようにして得ることができます（内部的に `cd` を実行していますが、実行後のカレントディレクトリが変わってしまうことはありません）。

```bash
self_dir=$(cd $(dirname $0); pwd)
```

ちなみに、シェルスクリプトと同じディレクトリにあるファイルの絶対パスは、上記で求めたディレクトリパスと組み合わせれば作れます。

```bash
echo ${self_dir}/sample.txt
```

