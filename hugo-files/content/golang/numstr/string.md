---
title: "Golang の文字列操作いろいろ"
url: "p/xssnvmh/"
date: "2022-11-04"
tags: ["Golang"]
---

Go 言語の文字列操作に関するメモです。


空文字列と nil のチェック
----

### 文字列が空かどうか調べる

```go
var s string  // s := "" と同じ（string のゼロ値は空文字列）
fmt.Println(s == "")      //=> true
fmt.Println(len(s) == 0)  //=> true
```

`string` 変数の内容が空文字列かどうかの確認は、__`s == ""`__ と __`len(s) == 0`__ のどちらでも OK です。

### 文字列ポインタが nil あるいは空文字を指しているかを調べる

{{< code lang="go" title="golibs/strutil/strutil.go" >}}
package strutil

func NilOrEmpty(s *string) bool {
	return s == nil || *s == ""
}
{{< /code >}}


文字列を連結する
----

{{< code lang="go" title="文字列同士の連結（+ 演算子）" >}}
s1 := "AAA"
s2 := s1 + "BBB"  //=> "AAABBB"
{{< /code >}}

{{< code lang="go" title="文字列同士の連結（+= 演算子）" >}}
s := "AAA"
s += "BBB"  //=> "AAABBB"
{{< /code >}}

{{< code lang="go" title="文字列スライスの連結 (strings.Join)" >}}
// import "strings"
ss := []string{"AA", "BB", "CC"}
result := strings.Join(ss, "-")  //=> "AA-BB-CC"
{{< /code >}}

{{< code lang="go" title="書式文字列で連結 (strings.Join)" >}}
str := "ABC"
num := 100
result := fmt.Sprintf("%s%d", str, num)  //=> "ABC100"
{{< /code >}}

{{< code lang="go" title="効率のよい連続連結 (strings.Builder)" >}}
// import "strings"
var buf strings.Builder // bytes.Buffer
buf.WriteString("AAA")
buf.WriteString("BBB")
buf.WriteRune('X')
buf.WriteRune('Y')
buf.WriteByte(90) // 'Z'
result := buf.String() //=> "AAABBBXYZ
{{< /code >}}

{{< code lang="go" title="繰り返し文字列 (string.Repeat)" >}}
// import "strings"
s := strings.Repeat("X", 4) //=> "XXXX"
{{< /code >}}


文字列を分割する (strings.Split)
----

### 区切り文字列（セパレーター）で分割する (strings.Split)

```go
s := "AA, BB, CC"

// 単純な分割
ss1 := strings.Split(s, ","))  //=> ["AA", " BB", " CC"]

// 単純な分割（最大分割数 N 個）
ss2 := strings.SplitN(s, ",", 2))  //=> ["AA", " BB, CC"]

// 区切り文字列を残す
ss3 := strings.SplitAfter(s, ","))  //=> ["AA,", " BB,", " CC"]

// 区切り文字列を残す（最大分割数 N 個）
ss4 := strings.SplitAfterN(s, ",", 2)  //=> ["AA,", " BB, CC"]
```

`strings.SplitN` の最大分割数 `n` に 0 を指定すると戻り値は `nil` になります。
`n` に負の値（-1 など）を指定すると、分割数を制限しません（`strings.Split` と同じ動作になります）。

分割後に区切り文字列（セパレーター）の前後のスペースを削除するには __`strings.TrimSpace`__ 関数を使用します。

```go
ss := strings.Split("AA, BB, CC", ",")
for i := range ss {
	ss[i] = strings.TrimSpace(ss[i])
}
```

{{% private %}}
- `strutil.SplitAndTrim`
{{% /private %}}

### 区切り文字列として正規表現を使う (regexp.Regexp)

{{< code lang="go" title="文字列をカンマで分割（カンマの後ろのスペースを削除）" >}}
re := regexp.MustCompile(`,\s*`)
ss := re.Split("AA, BB, CC", -1)  //=> ["AA", "BB", "CC"]
{{< /code >}}

`(*Regexp) Split` メソッドの第 2 引数には、`strings.SplitN` と同様に最大分割数を指定します。
最大分割数を制限しないのであれば、-1 を指定しておけば大丈夫です。

### 2 つに分割する (strings.Cut)

文字列を区切り文字（セパレーター）で高々 2 つに分割する場合は、`strings.Split` よりも __`strings.Cut`__ の方が便利かもしれません。
`strings.Cut` を使うと、分割された 2 つの文字列と、セパレーターが見つかったかどうかを別々の変数で取得できます。

```go
before, after, found := strings.Cut("AAA, BBB", ",")
fmt.Println(before)  //=>  "AAA"
fmt.Println(after)   //=> " BBB"
fmt.Println(found)   //=> true
```

### スペースや改行で分割する (strings.Fields)

```go
ss := strings.Fields(" AA   BB\t\tCC\nDD")  //=> ["AA", "BB", "CC", "DD"]
```

__`strings.Fields`__ 関数は、文字列をスペース系の文字（スペース、タブ、改行）で分割します。
CLI ツール（コマンドラインツール）の出力を加工したいときなどに便利です。
連続するスペースは、1 つのセパレーターとして扱われます。
スペースとみなされる文字は __`unicode.IsSpace`__ で true と判断される文字です。

### 文字単位で分割する

```go
ss := strings.Split("ABCあへ", "")) //=> ["A", "B", "C", "あ", "へ"]
```

`strings.Split` の区切り文字列（セパレーター）に空文字列を指定すると、UTF-8 文字単位 で分割されます。

