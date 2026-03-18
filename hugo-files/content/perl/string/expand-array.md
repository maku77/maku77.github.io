---
title: "Perlメモ: 文字列リテラルの中で配列を展開する"
url: "p/8xeishs/"
date: "2004-04-11"
tags: ["perl"]
aliases: ["/perl/string/expand-array.html"]
---

ダブルクォートで囲まれた文字列リテラルの中で配列変数 `@arr` を参照すると、各要素の内容が展開されます。

```perl
@arr = qw{aaa bbb ccc};
print '>>> @arr <<<' . "\n";
print ">>> @arr <<<\n";
```

{{< code lang="perl" title="実行結果" >}}
>>> @arr <<<
>>> aaa bbb ccc <<<
{{< /code >}}

要素間には **`$"`** に設定されている値が挿入されます。
デフォルトでは `$"` の値はスペース1文字になっているので、上記のような結果になります。

`$"` には任意の文字列を設定することができます。
下記の例では、要素間の区切り文字列をカンマ＋スペースに変更しています。

```perl
@arr = qw{aaa bbb ccc};
$" = ', ';
print ">>> @arr <<<\n";
```

{{< code title="実行結果" >}}
>>> aaa, bbb, ccc <<<
{{< /code >}}
