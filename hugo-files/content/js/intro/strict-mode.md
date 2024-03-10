---
title: "JavaScript は Strict モードを有効にすべし (ECMAScript 5)"
url: "p/bxrtpbp/"
date: "2012-11-09"
tags: ["JavaScript"]
aliases: /js/intro/strict-mode.html
---

Strict Mode を有効にする
----

ECMAScript 5 では、文法チェックを厳しくするための **Strict Mode** という機能が導入されています。

JavaScript コードの先頭行に、以下のように記述しておくと、Strict Mode が有効になります。
仮に、Strict Mode が使えない環境でこの行が記述されていても、単純に無視されるだけなので、常に記述しておくことをお勧めします。

{{< code lang="js" title="スクリプト全体を Strict Mode で動かす" >}}
'use strict';
{{< /code >}}

関数の中の先頭行で 'use strict' と記述すると、その関数のみが Strict Mode で実行されます。

{{< code lang="js" title="関数だけを Strict Mode で動かす" >}}
function hoge() {
  'use strict';
  ...
}
{{< /code >}}

Strict Mode を有効にすると、例えば、未定義の変数を参照したときに __`ReferenceError`__ が発生するようになります。

```js
'use strict';

var num1 = 100;
print(num1);  // OK
print(num2);  // ReferenceError!
```


Strict Mode のエラーが発生しない分かりにくい例
----

以下の例では、`print(num)` の時点で `num` が定義されていないので、一見 `ReferenceError` が発生しそうですが、同じスコープの中で `num` を定義している箇所があればエラーは発生しません。

{{< code lang="js" title="sample.js" >}}
'use strict';

print(num);    // エラーは発生しない
var num = 100;
{{< /code >}}

ただし、`num` を参照した時点では、値がまだ代入されていないので、`undefined` という値として参照されることになります。

{{< code lang="console" title="実行結果" >}}
$ jrunscript sample.js
undefined
{{< /code >}}


参考サイト
----

* [Strict モード - JavaScript ｜ MDN](https://developer.mozilla.org/ja/docs/Web/JavaScript/Strict_mode)

