---
title: "Octave でファイルに記述したデータを読み込む/保存する (load, save)"
url: "p/bi5rv73/"
date: "2017-03-27"
tags: ["Octave"]
aliases: /octave/io/read-data.html
---

ファイルからデータを読み込む (load)
----

Octave で、ファイルに保存したデータファイルを読み込むには、組み込み関数の __`load`__ を使用します。
例えば、下記のような二次元データ（行列データ）があるとします。

{{< code title="mydata.txt" >}}
100 200 300
400 500 600
700 800 900
{{< /code >}}

このデータファイルを読み込むには、下記のようにします（あらかじめ `cd` コマンドで、データファイルのあるディレクトリに移動しておいてください）。
データファイルの内容は、ファイル名と同じ名前の変数に格納されます。

{{< code lang="matlab" title="データファイルを読み込む" >}}
>> load mydata.txt
>> mydata
mydata =

   100   200   300
   400   500   600
   700   800   900
{{< /code >}}

データファイルの内容を別の名前の変数に格納したいときは、次のように `load` 関数の戻り値を変数に代入します。

{{< code lang="matlab" title="データファイルを指定した変数に読み込む" >}}
>> A = load('mydata.txt')
A =

   100   200   300
   400   500   600
   700   800   900
{{< /code >}}

ちなみに、現在定義されている変数の一覧を表示するには、__`whos`__ コマンドを使用します。

{{< code lang="matlab" title="変数の一覧を表示" >}}
>> whos
Variables in the current scope:

   Attr Name        Size                     Bytes  Class
   ==== ====        ====                     =====  =====
        mydata      3x3                         72  double
{{< /code >}}

ファイルから読み込んだ `mydata` が、サイズ 3x3 のデータとして定義されていることが分かります。


ファイルにデータを保存する (save)
----

逆に、Octave の実行環境上で作成した行列データなどをデータファイルとして出力するには、__`save`__ 関数を使用します。
次の例では、行列 `A` と `B` のデータを `mydata.txt` というファイルに保存しています。

{{< code lang="matlab" title="行列データをファイルに保存する" >}}
>> A = [1 2 3; 4 5 6; 7 8 9];
>> B = [100 200 300; 400 500 600];
>> save mydata.txt A B
{{< /code >}}

ここでは保存する変数を、`save` のパラメーターで明示的に列挙していますが、変数の指定を省略すると、ワークスペース内で定義されている変数（`whos` で出てくるデータ）がすべて保存されます。
`save` の実行に成功すると、下記のようなファイルが生成されます。

{{< code title="mydata.txt" >}}
# Created by Octave 4.2.1, Mon Mar 27 22:13:04 2017 GMT <unknown@unknown>
# name: A
# type: matrix
# rows: 3
# columns: 3
 1 2 3
 4 5 6
 7 8 9


# name: B
# type: matrix
# rows: 2
# columns: 3
 100 200 300
 400 500 600
{{< /code >}}

`save` コマンドで作成したファイルには、上記のようにヘッダ情報が付け加えられており、変数名（ここでは `A` や `B`）も一緒に保存されています。
このファイルを `load` で読み込んだときは、ヘッダに記述された変数名でデータがロードされます。

{{< code lang="matlab" title="データファイルからすべての変数を復旧する" >}}
>> clear            % ワークスペースの変数をすべてクリア
>> load mydata.txt  % ファイル読み込み
>> whos             % 変数を確認
Variables in the current scope:

   Attr Name        Size                     Bytes  Class
   ==== ====        ====                     =====  =====
        A           3x3                         72  double
        B           2x3                         48  double
{{< /code >}}

ファイル内の任意のデータだけをロードすることもできます。
下記の例では、`mydata.txt` に保存された `B` のデータのみを読み込んでいます。

{{< code lang="matlab" title="データファイルから指定した変数のみ復旧する" >}}
>> clear
>> load mydata.txt B  % 変数 B だけ取り出す
>> whos
Variables in the current scope:

   Attr Name        Size                     Bytes  Class
   ==== ====        ====                     =====  =====
        B           2x3                         48  double
{{< /code >}}

