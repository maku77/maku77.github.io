---
title: 最初から Strict モードを有効にすべし
date: "2012-11-09"
---

Strict Mode を有効にする
----
ECMAScript 5 では、文法チェックを厳しくするための **Strict Mode** という機能が導入されています。

JavaScript コードの先頭行に、以下のように記述しておくと、Strict Mode が有効になります。
仮に、Strict Mode が使えない環境でこの行が記述されていても、単純に無視されるだけなので、常に記述しておくことをお勧めします。

#### スクリプト全体を Strict Mode で動かす

```javascript
'use strict';
```

関数の中の先頭行で 'use strict' と記述すると、その関数のみが Strict Mode で実行されます。

#### 関数だけを Strict Mode で動かす

```javascript
function hoge() {
  'use strict';
  ...
}
```

Strict Mode を有効にすると、例えば、未定義の変数を参照したときに **ReferenceError** が発生するようになります。

```javascript
'use strict';

var num1 = 100;
print(num1);  // OK
print(num2);  // ReferenceError!
```

Strict Mode のエラーが発生しない分かりにくい例
----

以下の例では、`print(num)` の時点で `num` が定義されていないので、一見 `ReferenceError` が発生しそうですが、同じスコープの中で `num` を定義している箇所があればエラーは発生しません。

#### sample.js

```javascript
'use strict';

print(num);    // エラーは発生しない
var num = 100;
```

ただし、`num` を参照した時点では、値がまだ代入されていないので、`undefined` という値として参照されることになります。

#### 実行結果

```
$ jrunscript sample.js
undefined
```

参考サイト
----

* [Strict モード - JavaScript ｜ MDN](https://developer.mozilla.org/ja/docs/Web/JavaScript/Strict_mode)

