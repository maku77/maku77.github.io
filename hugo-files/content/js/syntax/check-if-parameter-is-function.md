---
title: "JavaScriptメモ: 渡されたパラメータが関数かどうか調べる"
url: "p/nnygkfo/"
date: "2015-02-11"
tags: ["javascript"]
aliases: [/js/syntax/check-if-parameter-is-function.html]
---

関数のパラメータを `typeof` で調べることにより、実際に渡されたパラメータが関数かどうかを調べることができます。
関数が渡された場合は、`typeof` の結果は `'function'` という文字列になります（ちなみに、関数ではなく、通常のオブジェクトであった場合は `'object'` となります）。


{{< code lang="javascript" title="sample.js" >}}
// 関数が渡されたかどうか調べる
function myfunc(opt) {
  if (opt && typeof opt === 'function') {
    // 渡されたオブジェクトが関数なら実行する
    opt(100, 200);
  } else {
    // 関数でなければ整形して出力
    console.log(JSON.stringify(opt, null, 2));
  }
}

// 引数に関数を渡した場合の動作を確認
myfunc(function(a, b) {
  console.log(a + b);
});

// 引数にオブジェクトを渡した場合の動作を確認
myfunc({ aaa: 1, bbb: 2, ccc: 3 });
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node sample
300
{
  "aaa": 1,
  "bbb": 2,
  "ccc": 3
}
{{< /code >}}
