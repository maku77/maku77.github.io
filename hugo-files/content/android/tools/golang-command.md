---
title: "Golang で Android 上で動く CLI コマンドを作成する (go build)"
url: "p/ftducta/"
date: "2022-10-21"
tags: ["Android"]
---

何をするか？
----

Golang（Go 言語）のクロスコンパイル機能を使うと、Android 上で動作するコマンドラインツール (CLI) を簡単に作成することができます。
Android 端末上で何らかの解析をするときに、Android 組み込みの Linux コマンドだとちょっと足りないな、といったときに便利です。
ここでは、Golang で簡単な Hello World アプリをビルドして、Android 端末上で動かしてみます。

- Go 言語全般に関してはこちらを参照 → [まくまく Golang ノート](/go/)


Android 端末の CPU アーキテクチャを確認しておく
----

Golang でのクロスコンパイル時に CPU アーキテクチャを指定する必要があるので、対象の Anrdoid 端末のアーキテクチャを先に確認しておきます。
`adb shell` で Android 端末にシェル接続して、__`uname -a`__ コマンドの末尾あたりで確認してしまうのが手っ取り早いです。

```console
$ adb shell uname -a
Linux localhost 5.10.100 #1 SMP PREEMPT Thu Mar 17 17:10:36 UTC 2022 aarch64
```

`arm` や `aarch` という文字列が入っていたら、__ARM__ アーキテクチャの CPU だと思ってよいです。


Golang プログラムをビルドする
----

下記は、Golang の Hello World プログラムです。

{{< code lang="go" title="hello.go" >}}
package main

import "fmt"

func main() {
	fmt.Println("Hello World")
}
{{< /code >}}

Android 端末上で動く実行ファイルを生成するために、__`GOOS`__ 環境変数で `linux`、__`GOARCH`__ 環境変数で `arm` を指定してビルドします。

{{< code lang="console" title="Anrdoid (ARM) 用にビルド" >}}
$ GOOS=linux GOARCH=arm go build -o hello hello.go
{{< /code >}}

これで、実行ファイル `hello` が生成されます。


Android 端末上で実行ファイルを起動する
----

作成した実行ファイル (`hello`) を __`adb push`__ で Android 端末に転送し、Android 端末上で実行してみます。
転送先ディレクトリとしては、パーミッション制限の緩い __`/data/local/tmp`__ を使います。

```console
# hello を転送する
$ adb push hello /data/local/tmp

# hello を実行する
$ adb shell /data/local/tmp/hello
Hello World
```

うまく動きました ٩(๑❛ᴗ❛๑)۶ わーぃ

