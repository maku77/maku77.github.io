---
title: "APK ファイルを逆コンパイルする (apktool)"
url: "p/xr3n5jk/"
date: "2011-05-27"
tags: ["Android"]
aliases: /android/decompile-apk.html
---

__Apktool__ を使用すると、APK ファイルを逆コンパイルして、内部のファイルの内容を確認することができます。

- [Apktool - A tool for reverse engineering Android apk files](https://ibotpeaches.github.io/Apktool/)

使い方は簡単で、次のように解析対象の APK ファイルと、出力先のディレクトリを指定するだけです。

{{< code lang="console" title="使い方" >}}
$ apktool d <APKファイル名> <展開先ディレクトリ>
{{< /code >}}

