---
title: "ファイルを p4 add したときにデフォルトで設定される filetype を調べる (p4 typemap)"
date: "2009-05-22"
---

あるファイルを `p4 add` で Perforce に追加しようとしたときに、そのファイルがテキストファイルとして追加されるのか、それともバイナリファイルとして追加されるのかは、Type Mapping の設定によって、拡張子を基に決定されます。
このマッピング情報は下記のようにして調べることができます。

~~~
$ p4 typemap -o
# Perforce File Type Mapping Specifications.
#
#  TypeMap:     a list of filetype mappings; one per line.
#               Each line has two elements:
#
#               Filetype: The filetype to use on 'p4 add'.
#
#               Path:     File pattern which will use this filetype.
#
# See 'p4 help typemap' for more information.

TypeMap:
        text+k //....h
        text+k //....hpp
        text+k //....hxx
        text+k //....c
        text+k //....cpp
        text+k //....cc
        text+k //....cxx
        text+k //....txt
        text+k //....html
        text+k //....htm
        ...
        binary //....png
        binary //....PNG
        ...
~~~

このデフォルトのマッピングでは都合が悪い場合には、`p4 add` を実行するときに `-t` オプションでファイルタイプを明示的に指定することができます。

* 参考: [バイナリファイルとしてファイルを追加する (p4 add)](add-as-binary.html)

