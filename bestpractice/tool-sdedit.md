---
title: テキストからシーケンス図を作成するツール
created: 2008-01-10
layout: bestpractice
---

UML 図をグラフィカルに作成することのできるツールはたくさんありますが、シーケンス図のように処理の前後関係を頻繁に入れ替えたくなる図は、逆にグラフィカルなツールでの修正は面倒かもしれません。*Quick Sequence Diagram Editor (sdedit)* は、テキストでシーケンスを定義し、画像ファイルとして出力することのできるツールです。

* [Quick Sequence Diagram Editor - SourceForge](http://sdedit.sourceforge.net/)
* [Quick Sequence Diagram Editor - GitHub](https://github.com/sdedit/sdedit)

このツール自体はグラフィカルな UI を提供しており、テキストエリアに専用のフォーマットでシーケンスを入力していくと、リアルタイムに図が更新されていきます。
他にも次のような特徴があります。

* いろんな画像フォーマットで出力できる。ベクタ形式の EMF で出力して、PowerPoint などにきれいに貼り付けることも可能。
* 図中のコンポーネントをクリックすると、その定義位置へジャンプできる。
* TAB キーで入力補間できる。
* 日本語も表示可能。

シーケンスの書き方
====

オブジェクトを定義する *Object section* と、メッセージのやりとりを定義する *Message section* を分けて記述していきます。
Object section と Message section は空白行で区切って定義します。

Object section の記述
----

シーケンス図内に登場させるオブジェクトは、Object section で次のように定義します。
行の先頭を # で始めるとコメント行になります。

```
# Object section
a:ClassA
b:ClassB
c:ClassC
```

Invisible object
----

オブジェクト定義時にプレフィックスとして ```/```（スラッシュ）を付けると、そのオブジェクトは *Invisible object* となり、Message section で他のオブジェクトから ```new``` されるまで図に表示されなくなります。
逆に、```destroy``` メッセージによって図から削除することができます。
次のコードは、オブジェクト ```a``` が ```b``` を ```new``` し、最後に ```destroy``` することを示しています。

```
# Object section
a:ClassA
/b:ClassB

# Message section
a:b.new
a:b.DoSomething()
a:b.destroy
```

オブジェクト定義時に指定できるフラグ
----

* Anonymous フラグ [a]
  * インスタンス名を表示せず、クラス名だけを表示する。
* Role フラグ [r]
  * ラベルの下にアンダーラインを引かない。
* Process フラグ [p]
  * Actor のような振る舞いをするオブジェクトに対して付ける（描画方法が変わる）。
* Auto-destruction フラグ [x]
  * lifeline の最後に×マークを付ける。

### 例

```
# Object section
a:ClassA[a]
```

アクターの定義
----

アクターを定義する場合は、クラス名に Actor を指定します。
アクターは常にアクティブで、自分自身にメッセージを送ることができません。

```
# Object section
user:Actor
hoge:Hoge

# Message section
user:hoge.ShowImages()
```

Return メッセージに名前をつける
----

Return メッセージに名前を付けるには、次のようにします。

```
a:returnValue=b.Hoge()
```

上記のようにすると、オブジェクト ```a``` が ```b.Hoge()``` を呼び出し、戻り値として ```returnValue``` を受け取ることを表すことができます。
ちょうど、以下のような C++ や Java の関数呼び出しの構文に似ているので分かりやすいです。

```
returnValue = b.Hoge();
```

改行
----

メッセージ名に改行を入れる場合は ```\\n``` と入力すれば OK です。

アクティブコメント
----

コメント行を ```#!``` で始めると、アクティブコメントとなり、特別な意味を持つようになります。

### シーケンス図のタイトルを表示

```
#![Title]
```

### シーケンス図の上部にコメントを入力

```
#!>>
#! This is the first line.
#! This is the second line.
#! This is the third line.
#!<<
```

Message section のグルーピング
----

Message section の各パートを以下のように分割することができます。
このように分割されたものをフラグメントと読んでいます。

```
# Message section
[c FragmentName1]
   a:b.Message1()
   a:b.Message2()
[/c]
[c FragmentName2]
   a:b.Message3()
   a:b.Message4()
[/c]
```

ループ処理
----

ループ処理などを書きたい場合は次のような感じで記述可能です。

```
# Message section
[c:loop while count < 100]
   a:b.Message1()
   a:b.Message2(count)
[/c]
```

条件分岐
----
フラグメント内に ```--label``` という行を挿入すると、そのフラグメントを点線で区切ることができます。
例えば、条件分岐は以下のように記述できます（```x==1``` のときの処理と ```else``` の処理を表現）。

```
# Message section
[c:alt x==1]
   a:b.Message1()
   a:b.Message2()
--[else]
   a:b.Message3()
   a:b.Message4()
[/c]
```
