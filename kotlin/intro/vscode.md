---
title: "VS Code で Kotlin の開発環境を構築する"
date: "2020-05-12"
description: "統合開発環境として Visual Studio Code の人気が高まってきています。ここでは、VS Code 上で Kotlin コードのハイライト表示や、ショートカットキーでのビルドを行えるように環境構築します。"
---

Kotlin 構築の構築
----

### kotlinc のインストール

VS Code 自体は Kotlin コードのコンパイル機能を持っていないので、`kotlinc` コマンドはあらかじめインストールしておく必要があります。

* [Kotlin をインストールする](install.html#kotlinc)

コマンドラインから次のように `kotlinc` コマンドを実行できるようになっていれば OK です。

```
$ kotlinc -version
info: kotlinc-jvm 1.3.71 (JRE 1.8.0_212-release-1586-b04)
```

### Kotlin Language プラグインのインストール

VS Code の Extensions タブから `kotlin` で検索して、 __`Kotlin Language`__ プラグインをインストールします。
このプラグインにより、Kotlin コードのシンタックスハイライトなどが効くようになります。

![vscode-001.png](vscode-001.png)

### Code Runner プラグインのインストール

VS Code の Extensions タブから `code` で検索して、 __`Code Runner`__ プラグインをインストールします。
このプラグインにより、ショートカットキー一発で Kotlin コードを実行できるようになります。

![vscode-002.png](vscode-002.png)


Kotlin プログラムを実行してみる
----

VS Code への各種プラグインのインストールが済んだら、簡単な Kotlin プログラムを記述して実行してみます。

### コードの作成

まず、VS Code を適当な空のディレクトリを起点にして起動します。

```
$ mkdir sample-kotlin
$ code sample-kotlin
```

`Ctrl + N` キーでファイルを新規作成し、次のように入力して `Ctrl + S` で保存します。
ファイルの拡張子は `.kt` にしてください。

#### main.kt

```kotlin
fun main() {
    println("Hello World!")
}
```

### 実行

後は、下記のいずれかの方法でプログラムを実行できます。

- __`Ctrl + Alt + N`__ ショートカットキーを押す
- エディタ上で右クリックして __`Run Code`__ を選択
- __`F1`__ キーでコマンドパレットを開いて __`Run Code`__ を実行

`OUTPUT` ウィンドウに下記のように出力されれば成功です。

```
[Running] cd "d:\sample-kotlin\" && kotlinc main.kt -include-runtime -d main.jar && java -jar main.jar
Hello World!

[Done] exited with code=0 in 6.605 seconds
```

ビルドしてから実行しているので結構時間がかかりますね。。。
ちなみに、出力を見ると分かるのですが、内部的には下記のようなコマンドが実行されています。

```
cd <作業ディレクトリ> && kotlinc main.kt -include-runtime -d main.jar && java -jar main.jar
```

### kotlinc コマンドが見つからないというエラーが出るとき

VS Code を起動した状態で `kotlinc` コマンドをインストールした場合は、環境変数 `PATH` の設定が反映されていない可能性があります。
その場合は、VS Code を再起動すれば直ります。

