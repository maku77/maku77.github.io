---
title: "Vim の ex コマンドでの行範囲指定方法いろいろ"
url: "p/fw7q8q7/"
date: "2007-01-30"
tags: ["vim"]
aliases: /vim/basic/line-range.html
---

行範囲指定の基本
----

Vim の ex コマンドは、__`:`__ から始めるコマンドモードのことで、各コマンドの先頭には、コマンドをどの行に対して実行するかを示す、__行範囲__ を指定することができます。

```
:行範囲 コマンド
```

行範囲を省略した場合は、コマンドはカレント行にだけ影響を及ぼします。
行範囲に `.`（ドット）を指定した場合も、同様にカレント行に対してだけ実行されます。

{{< code title="例: カレント行を削除" >}}
:d
{{< /code >}}

{{< code title="例: 123 行目を削除" >}}
:123 d
{{< /code >}}

{{< code title="例: 10 行目から 20 行目をソート" >}}
:10,20 sort
{{< /code >}}


行範囲指定の例
----

### 行番号ベースで指定する方法

```vim
:% <command>     " ファイル全体に対して command を実行
:3 <command>     " 3 行目に対して command を実行
:3,5 <command>   " 3 行目から 5 行目までに対して command を実行
:3,$ <command>   " 3 行目から最後の行までに対して command を実行
:. <command>     " カレント行に対して command を実行（省略した場合と同様）
:.,5 <command>   " カレント行から 5 行目までに対して command を実行
:.;+5 <command>  " カレント行から 5 行目下の行までに対して command を実行
```

### パターンに一致する文字列を含む行を検索して行指定する方法

~~~vim
:/pattern/       " pattern を含む行（1 行のみ）
:/pattern/+1     " pattern を含む行の 1 行下の行（1 行のみ）
:/pattern/;+4    " pattern を含む行から 4 行下の行まで
:g/pattern/      " pattern を含むすべての行
:/pat1/,/pat2/   " pat1 を含む行から pat2 を含む行まで（最初に見つかった部分のみ）
:/pat1/,/pat2/g  " pat1 を含む行から pat2 を含む行まで（見つかった範囲すべて）
~~~

{{< code title="例: TODO というテキストを含む行をすべて削除" >}}
:g/TODO/ d
{{< /code >}}

範囲指定を連続して記述すると AND 条件で絞り込むことができます。

{{< code title="例: 1 行目から 10 行目までで ABC で始まる行をすべて削除" >}}
:1,10 g/^ABC/ d
{{< /code >}}
