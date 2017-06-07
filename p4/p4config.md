---
title: カレントディレクトリの位置によって P4 設定を切り替える (P4CONFIG)
created: 2012-07-06
---

環境変数 `P4CONFIG` にファイル名を設定しておくと、そのファイルが置いてあるディレクトリ以下で実行した `p4` コマンドでは、そのファイルに記述されている `P4` 設定が使われるようになります。

#### 環境変数の設定例 (.bashrc)

~~~ bash
export P4CONFIG=.p4config
~~~

例えば、`Depot1` ディレクトリと、`Depot2` ディレクトリで、別の Perforce サーバへ接続したい場合は、下記のように設定ファイルを配置します。ここでは、`P4CONFIG` 環境変数に `.p4config` と設定しているため、その名前で設定ファイルを作成しています。

#### Depot1/.p4config

~~~
P4PORT=depot1.example.com:1666
P4CLIENT=maku.linux.depot1
~~~

#### Depot2/.p4config

~~~
P4PORT=depot2.example.com:1666
P4CLIENT=maku.linux.depot2
~~~

これらの設定ファイルで設定していない設定項目に関しては、従来通り、環境変数などで設定した値が使用されます（`.p4config` ファイルで部分的に設定を上書きできるということ）。

`.p4config` ファイルを入れ子構造で配置することもできます。
その場合は、`p4` コマンドを実行したディレクトリから上位にディレクトリを探していき、最初に見つかった設定ファイルが使用されます。
例えば、`Depot1/.p4congig` と `Depot1/Depot2/.p4config` が存在する場合、`Depot1/Depot2` ディレクトリ以下のディレクトリで `p4` コマンドを実行すると、`Depot1/Depot2/.p4config` の方に記載された設定が使用されます。


参考
----

- [P4CONFIG // P4 Command Reference](http://www.perforce.com/perforce/doc.current/manuals/cmdref/env.P4CONFIG.html)


