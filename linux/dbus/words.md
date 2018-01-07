---
title: D-Bus 関連用語
date: "2012-05-14"
---

D-Bus 系のライブラリを使用するにあたって、知っておくべき用語を解説します。

Bus
----

D-Bus の通信は、Bus を通して行われます。
Bus は、message bus という特殊なアプリケーション (daemon) が保持しており、以下のような種類があります。

system bus
: システム内で１つだけ存在する。カーネルレイヤの情報も収集し、USB 接続の検出などにも使われている。

session buses
: インユーザごとに存在する。一番よく使われるバス。Desktop bus とも呼ばれる。

private buses
: ２アプリが Peer-to-Peer で接続するためのバス。


Message
----
Bus 経由で伝達する際の、データの１単位を表します。
プログラムで言うと１つのメソッド呼び出しのやり取りに相当します。
メッセージは内部的には非同期通信ですが、各ライブラリ (Bindings) の API として、同期メソッドが提供されています。


Namespace / Address
----

Service name (Bus name)
: 個々のアプリケーションのバスへの "Connection" を示す名前。
ドキュメントによっては、"bus names" と読んでいるが、正確にはバスの名前ではなく、個々のバスへの "接続" を示す名前であることに注意。
（あくまで、バスの実体は system bus、session bus として存在していて、ここで言う "bus name"（接続を意味する）とは概念が異なる。）
Service 名は、接続相手となるアプリの接続を指定するために使われるので、Java の package 名のように一意な名前をつける必要がある。
接続は一般的には１アプリで１つであり、例えば、mycompany.com というドメインを持つ会社が作成する MyApp というアプリの接続なら、com.mycompany.MyApp というようにドメイン名を逆順に使って名前を付ければよい。
同じアプリケーションが同時に複数起動するケースを考慮する場合は、それぞれのアプリの接続に別の Service 名を割り当てる必要がある。そのような場合は、例えば、Process ID を付加することで一意な Service 名を作り出す。

Object path
: それぞれの Service は複数の Object を公開することができ、それぞれの Object は複数の Interface を公開することができる。
Object 名は、Object path と呼ばれ、ディレクトリ構造のように / で区切った名前を定義する。
Object path は、必ず / で始めるため、最短でも "/" の一文字で構成される。
Object path は、一般的には Bus name と同様にドメイン名を使って、一意な Object path を付けることが多い（Object path の例: /com/mycompany/MyApp）。
実際には、アプリ要件ごとに柔軟に定義することができ、例えば、スプレッドシートアプリなら、1 つのセルを表す "/cell/A/10" のような Object path を定義することができる。

Interface
: Bus 経由で公開されるメソッド、シグナルの集まりのこと。メソッド名、パラメータ、戻り値などが定義される。
実際にプログラムで実装するメソッド名と合わせる必要はないが、通常は合わせる（Interface の例: org.freedesktop.DBus.Peer）。

Method & Signal
: メソッド名は Interface 名に続く形で表現される（Interface名＋Method名の例: org.freedesktop.DBus.Peer.Ping）。
Method 名だけでメソッドを一意に識別できる場合は、Method 呼び出し時に Interface 名を省略することができる（別の Interface にも同名のメソッドがある場合は、どちらのメソッドが呼び出されるか分からない）。
シグナルも同じように表現されるが、メソッドと違って一方通行のブロードキャストメッセージなので、戻り値は存在しない（投げっぱなし）。

