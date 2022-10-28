---
title: "Golang の文字列操作いろいろ"
url: "p/xssnvmh/"
date: "2022-10-06"
tags: ["Golang"]
draft: true
---

Go 言語の文字列操作に関するメモです。


文字列が空かどうか調べる
----

```go
var s string  // s := "" と同じ（string のゼロ値は空文字列）
fmt.Println(s == "")      //=> true
fmt.Println(len(s) == 0)  //=> true
```

`string` 変数の内容が空文字列かどうかの確認は、__`s == ""`__ と __`len(s) == 0`__ のどちらでも OK です。


文字列ポインタが nil あるいは空文字を指しているかを調べる
----

{{< code lang="go" title="golibs/strutil/strutil.go" >}}
package strutil

func NilOrEmpty(s *string) bool {
	return s == nil || *s == ""
}
{{< /code >}}


文字列を区切り文字で分割する (strings.Split)
----

