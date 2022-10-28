---
title: "Dex 形式の Shared library (JAR) を作成する (dx)"
url: "p/huevdub/"
date: "2014-10-30"
tags: ["Android"]
aliases: /android/create-dex-jar.html
---

Android デバイスの `/system/framework` に以下に格納して使用する Shared JAR ライブラリは、DEX 形式にコンパイルされたクラスを含んでいる必要があります。Eclipse などで単純に JAR ファイルを生成すると、PC 用の JAR ライブラリができてしまうので、DEX 形式に変換してから `/system/framework` にインストールする必要があります。

```console
$ dx --dex --output=output.jar input.jar
```

