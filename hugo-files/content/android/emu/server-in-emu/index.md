---
title: "Android エミュレーター内のサーバーに外部からアクセスする (adb forward)"
url: "p/q6r98p8/"
permalink: "p/q6r98p8/"
date: "2022-10-21"
tags: ["Android"]
---

Android エミュレーターへのポート転送 (adb forward)
----

サーバー機能を持つ Android アプリをエミュレーターで動作させているときに、ホスト PC の外からエミュレーター内のサーバーにアクセスするには、__`adb forward`__ コマンドによるポート転送の設定が必要です。
例えば、次のようにフォワード設定すると、ホスト PC の `10080` 番ポートへのアクセスが、エミュレーターの `8080` 番ポートへ転送されるようになります。

{{< code lang="console" title="PC の 10080 ポートをエミュレーターの 8080 ポートへ転送" >}}
$ adb forward tcp:10080 tcp:8080
10080
{{< /code >}}

次の図は、Android エミュレーターを動かしている開発用 PC (`192.168.1.1`) を経由して、エミュレーター内の Web サーバーにアクセスする構成を示しています。

{{< image w="700" src="img-001.drawio.svg" title="adb forward によるポート転送" >}}

外部の端末（PC やスマホ）から見ると、開発 PC (`192.168.1.1`) 上で Web サーバーが動いているかのように見えます。
Web ブラウザーで `http://192.168.1.1:10080` という URL を開けば、エミュレーター内の Web サーバーに繋がります。
開発 PC 上で Web ブラウザーを開いてアクセスする場合は、自分自身にアクセスする形になるので、`http://localhost:10080` や `http://127.0.0.1:10080` のようなループバックアドレスを指定します。
この構成は、Android アプリ内のサーバープログラムを、PC 上のツールを使ってデバッグしたいときに便利です。

正確に言うと、`adb forward` によるポート転送はエミュレーター専用というわけではないので、USB で接続されている Android 端末への転送も可能です。
ただ、通常、物理スマホなどは直接 LAN に繋がっていることが多いので、ポート転送が必要になるケースは少ないでしょう（`adb shell ip addr` で端末のアドレスを確認して直接アクセスした方が早い）。

ちなみに、現在のポート転送設定は __`adb forward --list`__ コマンドで確認できます。

{{< code lang="console" title="ポート転送設定の一覧" >}}
$ adb forward --list
Y7HRR58M60D tcp:10080 tcp:8080
{{< /code >}}

転送設定を削除したいときは、__`adb forward --remove/--remove-all`__ コマンドを使用します。

{{< code lang="console" title="ポート転送設定を削除" >}}
$ adb forward --remove tcp:10080  # 個別に削除
$ adb forward --remove-all        # すべて削除
{{< /code >}}


エミュレーターからホスト PC を参照する
----

{{% private %}}
- [Android Emulator のネットワークを設定する](https://developer.android.com/studio/run/emulator-networking)
{{% /private %}}

エミュレーター内で動くプログラムから、ホスト PC を IP アドレスで参照したいときは、__`10.0.2.2`__ という特殊なループバックアドレスを使用します。
`localhost` や `127.0.0.1` というアドレスは、エミュレーター内のデバイスのループバックアドレスであることに注意してください。

{{< image w="550" src="img-002.drawio.svg" title="エミュレーターからホスト PC を参照する" >}}

ホスト PC を参照した結果、そのポートが `adb forward` で転送設定されている場合は、そのパケットはさらに別のエミュレーターに転送されます。
例えば、以下の構成では、Emulator 2 からのホスト PC へのアクセス (`10.0.2.2:10080`) が、Emulator 1 へポート転送されるようになっているため、結果的にエミュレーター間の通信が実現できています。

{{< image w="540" src="img-003.drawio.svg" title="エミュレーター間の通信" >}}


（おまけ）テスト用の Web サーバー実行ファイル
----

本記事の構成を試してみたいときは、コマンドラインで実行できる簡易 Web サーバーがあると便利です。
下記は、Golang 製の簡単な Web サーバーの実装例です。
ポート番号 `8080` で待ち受け、`Hello` という文字列を返します。
IP アドレス部分に `0.0.0.0` を指定しているのは、外部のネットワークからのアクセスを許可するためです。

{{< code lang="go" title="hello.go" >}}
package main

import (
	"log"
	"net/http"
)

// 0.0.0.0 の部分を省略すると、同一ネットワークからしかアクセスできないので注意
const addr = "0.0.0.0:8080"

func main() {
	http.HandleFunc("/", func(w http.ResponseWriter, _ *http.Request) {
		w.Write([]byte("Hello"))
	})
	log.Printf("Serving on: %s\n", addr)
	log.Fatal(http.ListenAndServe(addr, nil))
}
{{< /code >}}

次のような感じでビルドして Android 端末に転送すれば、Web サーバーを起動できます。

```console
$ GOOS=linux GOARCH=arm go build -o hello hello.go
$ adb push hello /data/local/tmp
$ adb shell /data/local/tmp/hello
2022/10/21 20:25:47 Serving on HTTP port: 8080
```

Web サーバーを停止したいときは、<kbd>Ctrl + C</kbd> で終了するだけで OK です。
ホスト PC 上で別のターミナルを開いて、ポート転送設定 (`adb forward`) すれば、ホスト PC の IP アドレスを使って Web サーバーにアクセスできるはずです。

```console
$ adb forward tcp:10080 tcp:8080
10080

$ curl localhost:10080
Hello
```

ネットワークまわりのテストをしたいときに便利です ٩(๑❛ᴗ❛๑)۶ わーぃ

- 参考: [Golang で Android 上で動く CLI コマンドを作成する (go build)](/p/ftducta/)

