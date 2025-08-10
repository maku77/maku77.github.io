---
title: "Vim で省略形を用いたテキスト入力を行えるようにする (abbrevaite)"
url: "p/qxjtb39/"
date: "2007-12-26"
tags: ["vim"]
aliases: [/vim/settings/abbreviate.html]
---

省略形を登録する
----

Vim の **`:abbreviate (:ab)`** コマンドを使うと、テキストの省略形を定義することができます。
省略形のテキストを入力して `Space` や `TAB`、`Enter` キーを押すと、自動的に展開してくれます。

```
:abbreviate ＜省略形＞ ＜展開後のテキスト＞
```

{{< code title="例: YT と入力したら、自動的に Yamada Taro に展開" >}}
:ab YT Yamada Taro
{{< /code >}}


展開後のテキストの先頭文字が Space の場合
----

`:abbreviate` で指定する展開後のテキストの先頭文字に半角スペースを指定する場合は、その半角スペースが Vim に無視されてしまわないように、明示的に `<Space>` と記述する必要があります。

```
:ab hghg <Space>HogeHoge
```


省略形の一覧表示
----

登録済みの省略形を一覧表示するには、`:abbreviate (:ab)` コマンドをパラメータなしで実行します。

```
:abbreviate
```

