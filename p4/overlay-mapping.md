---
title: "オーバーレイマッピングによる差分開発を行う"
date: "2005-07-16"
---

差分開発のとあるシチュエーション
----

既存のディポ `//depot/proj/...` があり、このプロジェクトを派生する形で新しいプロジェクトを立ち上げるとします。
この場合、既存のディポのファイル郡をベースにしてその中の一部のファイルを編集していくことになりますが、`//depot/proj/...` は元のプロジェクトの本線なのでここに新規プロジェクトの変更をサブミットしたくありません。
そこで、`//depot/proj_new/...` という新しいディポを作成し、編集する必要のあるファイル （既存のディポからの差分となるファイル）のみを登録するようにします。
このような状況では、全体としては `//depot/proj/...` のファイルを使い、差分ファイルだけは `//depot/proj_new/...` にあるファイルを使用する、といったことができると便利です。


差分ファイルによる上書きを行うマッピング定義
----

クライアントビューの設定で `+` を付けてマッピング指定を行うと、そのマッピング定義が他のマッピング定義よりも優先的に使用されるようになります。

~~~
View:
	//depot/proj/...      //client/proj/...
	+//depot/proj_new/... //clinet/proj/...
~~~

上記のようにすると、`//depot/proj/...` と `//depot/proj_new/...` の両方に同じファイルがある場合、`p4 sync` を実行したときに、`//depot/proj_new/...` 以下にあるファイルが優先的に取得されるようになります。
これにより、新規実装だけを `//depot/proj_new/...` の下で管理するといった差分開発を行えます。


ディレクトリ単位で参照先を入れ替えるマッピング定義
----

特定のディレクトリパス以下のファイル群を、（ディポ上の）別ディレクトリから取得するようにファイルマッピングを行うこともできます。
マッピング定義のプレフィックスに `-` を付けると、そのディレクトリ以下のファイルがマッピング定義から除外されるので、代わりに別のディレクトリへのファイルマッピングを追加してやることで、ディレクトリ単位のファイル入れ替えを実現できます。

下記の例では、`//client/proj/aaa` ディレクトリ以下のファイルだけ、ディポ上の `//depot/proj_new/aaa` から取得するようにマッピング定義を行っています。

~~~
View:
	//depot/proj/...          //client/proj/
	-//depot/proj/aaa/...     //client/proj/aaa/...
	//depot/proj_new/aaa/...  //client/proj/aaa/...
~~~

まとめ
----

クライアントビュー設定で、`+` プレフィックスを付けてディレクトリを指定すると、ファイルマッピングの上書き（優先度を付けたマッピング）を行うことができます。
一方で、`-` プレフィックスを付けてディレクトリを指定すると、そのディレクトリ以下にあるファイルがマッピング定義から除外されるため、ディレクトリ単位でのマッピング入れ替えが可能になります。

差分開発を行うときに、差分ファイルがたくさんのディレクトリに散らばっているようなケースでは `+` による上書きマッピングを使用し、ディレクトリ単位で差分ファイルを管理できるようなケースでは `-` によるディレクトリ単位の入れ替えを行うとよいでしょう。
