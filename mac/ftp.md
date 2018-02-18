---
title: "昔ながらの ftp コマンドを使用できるようにする"
date: "2018-02-19"
---

Mac High Sierra からは、`ftp` コマンドは廃止され、よりセキュアな `sftp` コマンドしか使用することができません。
`sftp` コマンドでファイル転送するには、サーバ側で sftp プロトコルに対応している必要があります。

下記のように `tnftp` をインストールすれば、昔ながらの `ftp` コマンドを使用できるようになります。

~~~
$ brew install tnftp
~~~

~~~
$ ftp user@ftp.example.com
~~~

ちなみに、次のようにすれば、FTP コマンドを列挙したスクリプトを実行することができます（`-n` オプションで、ログインプロンプトの表示を抑制する必要があります）。

~~~
$ ftp -n < upload.ftp
~~~

#### upload.ftp

~~~
open ftp.exmple.com
user username password
bin
prompt
cd hoge
put sample.txt
quit
~~~

