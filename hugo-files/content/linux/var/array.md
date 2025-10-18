---
title: "Linuxメモ: シェルスクリプトで配列を扱う"
url: "p/m82nd2v/"
date: "2012-02-02"
tags: ["linux"]
aliases:
  - /linux/var/create-array.html
  - /linux/var/loop-array.html
  - /linux/var/array-size.html
  - /linux/var/add-elem-to-array.html
  - /linux/var/concat-arrays.html
---

Bash シェルスクリプト内で配列を扱う方法です。

配列を作成する
----

下記の例では、配列変数 `arr` に 3 つの要素を格納しています。

### インデックスを指定して1つずつ代入していく方法

```bash
arr[0]=AAA
arr[1]=100
arr[2]='Hello world'

echo ${arr[0]}
echo ${arr[1]}
echo ${arr[2]}
```

{{< code title="実行結果" >}}
AAA
100
Hello world
{{< /code >}}

### 配列の初期化時に要素をまとめて指定する方法

```bash
arr=(AAA BBB CCC)

echo ${arr[0]}
echo ${arr[1]}
echo ${arr[2]}
```

{{< code title="実行結果" >}}
AAA
BBB
CCC
{{< /code >}}

以下のように、複数行にまたいで値を記述することもできます。

```bash
arr=(
  AAA
  BBB
  CCC
)
```


配列をループ処理する
----

下記の例では、3 つの要素を持つ配列変数 `arr` を定義し、各要素をループで 1 つずつ処理しています。
配列要素の値にスペースが含まれる場合、下記の `"CCC DDD"` のようにダブルクォートで囲む必要があります。

```bash
arr=(AAA BBB "CCC DDD")

for x in "${arr[@]}"; do
  echo "$x"
done
```

{{< code title="実行結果" >}}
AAA
BBB
CCC DDD
{{< /code >}}

ちなみに、上記のコードは、以下のようにすれば配列変数を定義しないで書けます。

```bash
for x in AAA BBB "CCC DDD"; do
  echo "$x"
done
```

配列のサイズ（要素数）を調べる
----

Bash で定義した配列変数のサイズを調べるには下記のようにします。
ここでは、配列変数 `arr` を定義して、そのサイズを調べています。

```bash
arr=(100 200 300)
echo ${#arr[*]}
```

{{< code title="実行結果" >}}
3
{{< /code >}}


配列に要素を追加する
----

Bash の配列変数に要素を追加するには下記のようにします。

{{< code lang="bash" title="例: 配列 arr の末尾に要素を 3 つ追加する" >}}
arr=(100 200 300)
arr=("${arr[@]}" XXX YYY ZZZ)
echo ${arr[@]}
{{< /code >}}

{{< code title="実行結果" >}}
100 200 300 XXX YYY ZZZ
{{< /code >}}

同様にして、配列の先頭に要素を追加できます。

```bash
arr=(100 200 300)
arr=(XXX YYY ZZZ "${arr[@]}")
echo ${arr[@]}
```

{{< code title="実行結果" >}}
XXX YYY ZZZ 100 200 300
{{< /code >}}


配列と配列を結合する
----

Bash の複数の配列を結合して、新しい配列を作成するには下記のようにします。

{{< code lang="bash" title="例: arr1 と arr2 を結合して new_arr を作成する" >}}
arr1=(100 200 300)
arr2=(400 500 600)

new_arr=("${arr1[@]} ${arr2[@]}")
echo "${new_arr[@]}"
{{< /code >}}

{{< code title="実行結果" >}}
100 200 300 400 500 600
{{< /code >}}

同様にして、3 つ以上の配列を結合することもできます。

{{< code lang="bash" title="例: arr1 と arr2 と arr3 を結合して new_arr を作成する" >}}
arr1=(100 200 300)
arr2=(400 500 600)
arr3=(700 800 900)

new_arr=("${arr1[@]} ${arr2[@]} ${arr3[@]}")
echo "${new_arr[@]}"
{{< /code >}}

{{< code title="実行結果" >}}
100 200 300 400 500 600 700 800 900
{{< /code >}}

