---
title: "エラー変数 $! について"
date: "2008-05-21"
---

エラー変数 **`$!`** は、数値コンテキストで使用するとシステム変数 `error` の値を返し、文字列コンテキストで使用すると `perror() ` 関数（あるいは同様の関数）が返すエラー文字列を返します。

~~~ perl
open FH, 'nonexistent';
my $code = 0 + $!;
print "Error code = $code\n";
print "Error msg = $!\n";
~~~

#### 実行結果

~~~
Error code = 2
Error msg = No such file or directory
~~~

<div class="note">
変数を強引に数値コンテキストで評価するには、<code>0 + $val</code> のように、数値演算子を使用します。
</div>

