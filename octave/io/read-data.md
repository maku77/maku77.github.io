---
title: "ファイルに記述したデータを読み込む (load)"
date: "2017-03-27"
---


ファイルからデータを読み込む (load)
----

ファイルに保存したデータファイルを読み込むには、組み込み関数の `load` を使用します。
例えば、下記のような二次元データ（行列データ）があったとします。

#### mydata.txt

~~~
100 200 300
400 500 600
700 800 900
~~~

これを読み込むには、下記のようにします（`cd` コマンドで、ファイルのあるディレクトリに移動しておくこと）。

~~~ matlab
load mydata.txt
~~~

すると、自動的に同じ名前の変数が作成され、そこにデータが読み込まれます。

~~~ matlab
>> mydata
mydata =

   100   200   300
   400   500   600
   700   800   900
~~~

下記のように `load` 関数の結果を代入するように記述することで、任意の名前の変数に格納することができます。

~~~ matlab
>> A = load('mydata.txt')
A =

   100   200   300
   400   500   600
   700   800   900
~~~

ちなみに、現在定義されている変数の情報を表示するには、`whos` コマンドを使用します。

~~~ matlab
>> whos
Variables in the current scope:

   Attr Name        Size                     Bytes  Class
   ==== ====        ====                     =====  =====
        mydata      3x3                         72  double
~~~

ファイルから読み込んだ `mydata` が、サイズ 3x3 のデータとして定義されていることが分かります。


ファイルにデータを保存する (save)
----

逆に、Octave の実行環境上で作成した行列データなどをファイルに保存しておくこともできます。
次の例では、行列 A と B のデータを `mydata.txt` というファイルに保存しています。

~~~ matlab
>> A = [1 2 3; 4 5 6; 7 8 9];
>> B = [100 200 300; 400 500 600];
>> save mydata.txt A B
~~~

ここでは保存する変数を、`save` のパラメータで明示的に列挙していますが、変数の指定を省略すると、ワークスペース内で定義されている変数（`whos` で出てくるデータ）がすべて保存されます。
`save` の実行に成功すると、下記のようなファイルが生成されます。

#### mydata.txt

~~~
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
~~~

`save` コマンドで作成したファイルには、上記のようにヘッダ情報が付け加えられており、変数名（ここでは `A` や `B`）も一緒に保存されています。
このファイルを `load` で読み込んだときは、ヘッダに記述された変数名でデータがロードされます。

~~~ matlab
>> clear            % ワークスペースの変数をすべてクリア
>> load mydata.txt  % ファイル読み込み
>> whos             % 変数を確認
Variables in the current scope:

   Attr Name        Size                     Bytes  Class
   ==== ====        ====                     =====  =====
        A           3x3                         72  double
        B           2x3                         48  double
~~~

ファイル内の任意のデータだけをロードすることもできます。
下記の例では、`mydata.txt` に保存された `B` のデータのみを読み込んでいます。

~~~ matlab
>> clear
>> load mydata.txt B
>> whos
Variables in the current scope:

   Attr Name        Size                     Bytes  Class
   ==== ====        ====                     =====  =====
        B           2x3                         48  double
~~~

