---
title: "GENA と SSDP プロトコルを理解する"
url: "p/wai79fn/"
date: "2012-12-25"
tags: ["memo", "network"]
aliases: ["/memo/network/ssdp.html"]
---

概要
----

**SSDP (Simple Service Discovery Protocol)** は、LAN 内のデバイスが提供しているサービスを発見したり、サービスがネットワークに参加したことを通知したりするプロトコルです。
SSDP 自体はシンプルなプロトコルですが、DLNA などで使われている、機器発見プロトコルである UPnP の一部として利用されています。

SSDP は、以下のようなヘッダで始まる HTTP を拡張したような UDP ベースのプロトコルです。**M-SEARCH メソッド** は、ネットワーク内のサービスを SSDP クライアントが能動的に発見しにいくときに使用します。

### SSDP メッセージのフォーマット

```
M-SEARCH * HTTP/1.1 \r\n
ヘッダ1: ... \r\n
ヘッダ2: ... \r\n
ヘッダ3: ... \r\n
\r\n
```

SSDP では、内部で **GENA (General Event Notification Architecture)** で定義されているメッセージフォーマットも使用しており、こちらは、

* サービス（機器）がネットワークに参加した (`ssdp:alive`)
* サービス（機器）がネットワークから脱退した (`ssdp:byebye`)

などの Notify をマルチキャストするために使用されます。

### GENA メッセージのフォーマット

```
NOTIFY * HTTP/1.1 \r\n
ヘッダ1: ... \r\n
ヘッダ2: ... \r\n
ヘッダ3: ... \r\n
\r\n
```

SSDP では、以下のようなアドレス、ポート番号にメッセージを UDP でマルチキャストしましょうね、と決めています（ポート番号については、正式には UPnP の世界で決まっているのかも）。

```
239.255.255.250:1900
```


サービスの識別
----

SSDP で検出するサービスは、以下の情報で識別されます。

* ユニークサービスネーム URI
  * USN ヘッダで指定
* サービスタイプ URI
  * `ssdp:discover` リクエストの場合は ST ヘッダで指定
  * `ssdp:alive`、`ssdp:byebye` リクエストの場合は NT ヘッダで指定

「ユニークサービスネーム」はサービスを一意に特定する ID になっている必要があるため、通常は UUID を組み合わせて設定されます。

「サービスタイプ」はサービスのカテゴリのようなものであり、どのような形式の URI を入れるかは、SSDP では規定していません（DLNA の世界では、サービスタイプにどのような値を指定するかが規定されています）。


{{< note title="コラム（ST ヘッダと NT ヘッダ）" >}}
サービスタイプは、`ssdp:discover` リクエストの場合は ST ヘッダで指定しますが、`ssdp:alive` リクエスト、`ssdp:byebye` リクエストの場合は NT ヘッダで指定します。
これは、`ssdp:alive`、`ssdp:byebye` リクエストが GENA のメッセージフォーマットを採用しており、その中の必須で NT ヘッダをサービスタイプを指定するためのフィールドとして使い回しているからです。

分かりにくいですね。。。
本来ならば、SSDP は GENA と独立させて、スッキリと定義された方がよかったように思います。
まぁ、いろいろ事情があったんでしょう。
{{< /note >}}


リクエストの種類
----

* Discovery Requests (M-SEARCH method)
  * `ssdp:discover` リクエスト -- サービスの発見
  * `ssdp:all` リクエスト -- 全てのサービスの発見
* Presence Announcements (GENA's NOTIFY method)
  * `ssdp:alive` リクエスト -- サービスの参加を通知
  * `ssdp:byebye` リクエスト -- サービスの脱退を通知

Presence Announcements に分類されている、`ssdp:alive` リクエストと、`ssdp:byebye` リクエストは、GENA の NOTIFY メソッドのフォーマットで送信されます。

### ssdp:discover リクエスト／レスポンス

`ssdp:discover` リクエストは、「～のサービスを提供するデバイスはいますかぁ？」というサービス発見のための要求です。

{{< code title="ssdp:discover（リクエスト）の例" >}}
M-SEARCH * HTTP/1.1
HOST: 239.255.255.250:1900
MAN: "ssdp:discover"
ST: your:serviceType
{{</ code >}}

* 1行目の Request URI には `*` を指定する。
* MAN ヘッダには `ssdp:discover` を指定する。
* ST (Service Type) ヘッダは必須で、検索したいサービスタイプを指定する。

`ssdp:discover` にはレスポンスがあります。`ssdp:discover` リクエストを受信したサービスは、指定されたサービスタイプが自分に一致する場合は、UDP ユニキャストで以下のようなレスポンスを返します。

{{< code title="ssdp:discover（レスポンス）の例" >}}
HTTP/1.1 200 OK
USN: uuid:abcdefgh-7dec-11d0-a765-00a0c91e6bf6
ST: your:serviceType
LOCATION: http://192.168.1.101:53456/
CACHE-CONTROL: max-age = 3600
{{</ code >}}

* USN ヘッダは必須。
* ST (Service Type) ヘッダは必須。
* サービスのロケーションを表す、Location ヘッダか、AL ヘッダを含んでいる必要がある。
* キャッシュの保持期間を示す、CACHE-CONTROL: max-age ヘッダか、Expires ヘッダを含むべき。どちらのヘッダも含まれていない場合は、SSDP クライアントはそのサービスの情報をキャッシュしない。

SSDP クライアントは、受信したサービスの情報をキャッシュに保存して使い回します。
キャッシュには以下のような情報を保持します。

* サービス ID（USN ヘッダ）
* サービスタイプ（ST ヘッダ）
* サービスの場所 (Location or AL ヘッダ）
* キャッシュの有効期限（秒数）（Expire or Cache-Control: ヘッダ）

### ssdp:all リクエスト／レスポンス

`ssdp:all` リクエストは、`ssdp:discover` リクエストの特殊版で、サービスタイプ（ST ヘッダ）として `ssdp:all` を指定したものです。

{{< code title="ssdp:all（リクエスト）の例" >}}
M-SEARCH * HTTP/1.1
HOST: 239.255.255.250:1900
MAN: "ssdp:discover"
ST: ssdp:all  ★ここだけ特殊
{{</ code >}}

`ssdp:all` リクエストを受信したサービス提供機器は、必ずレスポンスを返します。
レスポンスの内容は、`ssdp:discover` リクエストのレスポンスと同様なので省略します。
`ssdp:all` リクエストは、主に Network analysis tool などが利用します。

### ssdp:alive リクエスト

`ssdp:alive` リクエストは、以下のような場合にマルチキャストされます。

* 自分（サービス）が新しくネットワークに参加したとき
* キャッシュして欲しい時間情報 (Expires) が変化したとき
* サービスのロケーション (Location) が変化したとき

`ssdp:alive` リクエストのメッセージ形式は、GENA のフォーマットを利用しています。
GENA でオプショナルなヘッダとして定義されている NTS (Notification Sub-Type) ヘッダの値を `ssdp:alive` として設定したものが `ssdp:alive` リクエストとして用いられます。

{{< code title="ssdp:alive（リクエスト）の例" >}}
NOTIFY * HTTP/1.1
HOST: 239.255.255.250:1900
USN: uuid:abcdefgh-7dec-11d0-a765-00a0c91e6bf6
NT: your:serviceType
NTS: ssdp:alive
CACHE-CONTROL: max-age = 7393
LOCATION: http://192.168.1.101:53456/
{{< /code >}}

* NT (Notification Type) ヘッダにはサービスタイプを設定します（形式は任意）。GENA で必須ヘッダとして定義されています。
* NTS (Notification Sub-Type) ヘッダには ssdp:alive を指定します。

`ssdp:alive` リクエストを受信した SSDP クライアントは、その情報でキャッシュの内容を更新します。
更新するキャッシュは、USN が一致するものです。
`ssdp:alive` リクエストには、レスポンスはありません。

### ssdp:byebye リクエスト

`ssdp:byebye` リクエストは、以下のような場合にマルチキャストされます。

* 自分（サービス）がネットワークから離脱するとき

`ssdp:byebye` リクエストのメッセージ形式も `ssdp:alive` と同様に、GENA のフォーマットを利用しています。
GENA でオプショナルなヘッダとして定義されている NTS (Notification Sub-Type) ヘッダの値を `ssdp:byebye` として設定したものが `ssdp:byebye` リクエストとして用いられます。

{{< code title="ssdp:byebye（リクエスト）の例" >}}
NOTIFY * HTTP/1.1
HOST: 239.255.255.250:1900
USN: uuid:abcdefgh-7dec-11d0-a765-00a0c91e6bf6
NT: your:serviceType
NTS: ssdp:byebye
{{< /code >}}

* NT (Notification Type) ヘッダにはサービスタイプを設定します（形式は任意）。GENA で必須ヘッダとして定義されています。
* NTS (Notification Sub-Type) ヘッダには `ssdp:byebye` を指定します。

`ssdp:byebye` リクエストには、レスポンスはありません。
NT ヘッダは GENA で必須ヘッダなので、サービスタイプを指定していますが、実際の実装では、USN の一致によりキャッシュクリアなどの判断をするため、通常は `ssdp:byebye` リクエストの NT ヘッダが参照されることはありません。


Internet Draft
----

* [GENA (General Event Notification Architecture Base: Client to Arbiter)](https://tools.ietf.org/html/draft-cohen-gena-client-00)
* [SSDP (Simple Service Discovery Protocol/1.0 Operating without an Arbiter)](https://tools.ietf.org/html/draft-cai-ssdp-v1-03)

