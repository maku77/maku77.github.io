---
title: "Golang でランダム値（乱数）を扱う (math/rand, crypto/rand)"
linkTitle: "ランダム値（乱数）を扱う (math/rand, crypto/rand)"
url: "p/graq8o5/"
date: "2022-09-24"
tags: ["Go"]
---

Golang でランダム値を扱うパッケージは以下の 2 つが用意されています。

- [math/rand](https://pkg.go.dev/math/rand) ... 擬似乱数生成器 (pseudo-random number generator)。初期化のための Seed によって生成される一連の乱数が決定するため、再現性がある。高速な生成が可能だが、生成されるランダム値が予測され得るため、暗号系技術での使用には適さない。
- [crypto/rand](https://pkg.go.dev/crypto/rand) ... 暗号論的擬似乱数生成器（CSPRNG: cryptographically secure pseudo random number generator)。生成されるランダム値を予測するのが困難で、暗号系技術での使用に適している。例えば、秘密鍵の生成や、Nonce 値の生成に用いることができる。`math/rand` に比べ、`crypto/rand` でのランダム値生成は時間がかかる。


math/rand による乱数生成
----

`math/rand` パッケージのランダム生成器 (`*rand.Rand`) は、__`rand.New`__ コンストラクタで生成します。
典型的には、現在時刻を元にしたシードを与えて初期化します。

{{< code lang="go" title="*rand.Rand を生成する方法" >}}
seed := time.Now().UnixNano()
r := rand.New(rand.NewSource(seed))
val := r.Float64() // 乱数を生成（rand.Rand のメソッド）
{{< /code >}}

あるいは、__`rand.Seed`__ 関数で、トップレベル関数用のシードを設定することもできます。
こちらの方法を使う場合は、`*rand.Rand` インスタンスを生成する必要はありません。

{{< code lang="go" title="トップレベル関数用のシードを設定する方法" >}}
seed := time.Now().UnixNano()
rand.Seed(seed)
val := rand.Float64() // 乱数を生成（rand のトップレベル関数）
{{< /code >}}

`math/rand` パッケージは、次のような乱数生成関数を提供しています。

| メソッド | 説明 | 戻り値の型 | 値の範囲 |
| ---- | ---- | ---- | ---- |
| __`Int31()`__ | 0 以上の 31 ビット整数 | `int32` | `0` 〜 `2,147,483,647` |
| __`Int31n(n)`__ | 0 以上 n 未満の 31 ビット整数 | `int32` | `0` 〜 `n`（n ≧ 1） |
| __`Uint32()`__ | 0 以上の 32 ビット整数 | `uint32` | `0` 〜 `4,294,967,295` |
| __`Int63()`__ | 0 以上の 63 ビット整数 | `int64` | `0` 〜 `9,223,372,036,854,775,807` |
| __`Int63n(n)`__ |  0 以上 n 未満の 63 ビット整数 | `int64` | `0` 〜 `n`（n ≧ 1） |
| __`Uint64()`__ | 0 以上の 64 ビット整数 | `uint64` | `0` 〜 `18,446,744,073,709,551,615` |
| __`Int()`__ | 0 以上の整数（少なくとも 32 ビット） | `int` | `0` 〜 システム依存 |
| __`Intn(n)`__ | 0 以上 n 未満の整数（少なくとも 32 ビット） | `int` | `0` 〜 `n`（n ≧ 1） |
| __`Float32()`__ | 浮動小数点数（`float32` 型） | `float32` | `[0.0, 1.0)`（0.0 以上 1.0 未満） |
| __`Float64()`__ | 浮動小数点数（`float64` 型） | `float64` | `[0.0, 1.0)`（0.0 以上 1.0 未満） |

{{< code lang="go" title="math/rand による乱数生成の例" >}}
package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	seed := time.Now().UnixNano()
	r := rand.New(rand.NewSource(seed))

	fmt.Println(r.Int31())    // 1401608483 (int32)
	fmt.Println(r.Uint32())   // 3362137694 (uint32)
	fmt.Println("-------")
	fmt.Println(r.Int63())    // 9200732467715261966 (int64)
	fmt.Println(r.Uint64())   // 17815840155156866386 (uint64)
	fmt.Println("-------")
	fmt.Println(r.Float32())  // 0.34179267 (float32)
	fmt.Println(r.Float64())  // 0.7233553795829966 (float64)
	fmt.Println("-------")
	fmt.Println(r.Int31n(10000))  // 1451 (int32)
	fmt.Println(r.Int63n(10000))  // 7504 (int64)
}
{{< /code >}}

__`Read(p []byte)`__ メソッドを使用すると、任意の長さのバイト配列を生成することができます。

{{< code lang="go" title="4 バイト分のランダム配列を生成" >}}
bytes := make([]byte, 4)
r.Read(bytes)
fmt.Println(bytes) // [231 52 121 45]
{{< /code >}}


crypto/rand による乱数生成
----

予測の難しい乱数値が欲しいときは、`math/rand` パッケージの代わりに `crypto/rand` を使用します。
パッケージ内部の乱数生成には、Linux 系 OS では `/dev/urandom` や `getentropy(2)`、Windows では `RtlGenRandom` API が使われています。

次の例では、__`rand.Int`__ 関数を使って、0 以上 n 未満のランダム整数 (`*big.Int`) を生成しています。
シードによる初期化は必要ありません。

```go
package main

import (
	"crypto/rand"
	"fmt"
	"math/big"
)

func main() {
	// 0 以上 n 未満のセキュアなランダム整数を生成
	var n int64 = 1_000_000_000
	val, err := rand.Int(rand.Reader, big.NewInt(n))
	if err != nil {
		panic(err)
	}
	fmt.Println(val.Int64()) //=> 721357881
}
```

`crypto/rand` パッケージは、`math/rand` ほど柔軟なランダム生成関数を備えていませんが、`encoding/binary` パッケージと組み合わせて使用すると、任意の型にランダム値を詰めることができます。

```go
package main

import (
	"crypto/rand"
	"encoding/binary"
	"fmt"
)

func main() {
	var v int32 // 任意の型を指定可能
	binary.Read(rand.Reader, binary.LittleEndian, &v)
	fmt.Println(v) //=> 2017071203
}
```

`math/rand` と同様、`crypto/rand` でも指定した長さのバイト配列を生成することができます。

```go
func main() {
	bytes := make([]byte, 4)
	rand.Read(bytes)
	fmt.Println(bytes) //=> [235 16 197 104]
}
```


（応用）crypto/rand で math/rand のシードを生成する
----

現在時刻を `math/rand` のシードに使うのは抵抗があるけど、`crypto/rand` ほどセキュアな乱数は必要ないという場合は、シードの生成のみを `crypto/rand` で行うという方法があります。

{{< code lang="go" title="main.go" >}}
package main

import (
	cryptorand "crypto/rand"
	"encoding/binary"
	"fmt"
	"math/rand"
)

// 予測困難なシードで math/rand を初期化
func initRandomSeed() {
	var seed int64
	if err := binary.Read(cryptorand.Reader, binary.LittleEndian, &seed); err != nil {
		panic(err)
	}
	rand.Seed(seed)
}

func main() {
	initRandomSeed()
	for i := 0; i < 20; i++ {
		fmt.Print(rand.Intn(10), " ") // 0 〜 9 のランダム整数を生成
	}
	fmt.Println()
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ go run main.go
9 8 9 7 3 3 0 2 1 4 9 9 2 6 1 6 0 5 8 1
{{< /code >}}


（応用）ランダムな文字列を生成する
----

次の `RandomId` 関数は、指定した長さのランダムな文字列を生成します（例: `m6t2j7a`）。

{{< code lang="go" title="strutil/strutil.go" >}}
package strutil

import (
	cryptorand "crypto/rand"
	"encoding/binary"
	"fmt"
	"math/rand"
	"os"
	"time"
)

func init() {
	initRandomSeed()
}

// 指定した長さのランダム文字列を生成します
func RandomId(length int) string {
	const CANDIDATES = "23456789abcdefghijkmnopqrstuvwxyz"
	const LEN_CAND = len(CANDIDATES)
	bytes := make([]byte, 0, length) // 必要な capacity は明確
	for i := 0; i < length; i++ {
		bytes = append(bytes, CANDIDATES[rand.Intn(LEN_CAND)])
	}
	return string(bytes)
}

// 予測困難なシードで math/rand を初期化します
func initRandomSeed() {
	var seed int64
	if err := binary.Read(cryptorand.Reader, binary.LittleEndian, &seed); err != nil {
		fmt.Fprintln(os.Stderr, "WARN: Seed creation by crypto/rand failed, so current time was used instead")
		seed = time.Now().UnixNano()
	}
	rand.Seed(seed)
}
{{< /code >}}

{{< code lang="go" title="main.go（使用例）" >}}
package main

import (
	"fmt"
	"hello/strutil"
)

func main() {
	fmt.Println(strutil.RandomId(7)) //=> "m6t2j7a"
}
{{< /code >}}

- 参考: [ランダム ID 生成ツール｜まくろぐ](https://maku.blog/p/3nx9is3/)

