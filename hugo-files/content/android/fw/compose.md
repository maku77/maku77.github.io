---
title: "Androidメモ: Jetpack Compose の宣言型 (declarative) の UI 定義とは？"
url: "p/4kdso44/"
date: "2021-07-07"
tags: ["android"]
aliases: ["/android/fw/compose.html"]
---

Jetpack Compose の宣言型プログラミングとは？
----

2021 年の Google I/O で、[Jetpack Compose](https://developer.android.com/jetpack/compose?hl=ja) が正式リリース (v1.0.0) されることが発表されました。
Compose は Android の新しい UI フレームワークで、__declarative（宣言的）__ に UI を定義することができるのが特徴的です。
正確には、Compose のトップページでは declarative ではなく、__intuitive（直感的）__ という表現がされているのですが、ソフトウェア開発の世界では declarative（宣言的）といった方が伝わりやすいと思います。

宣言型（宣言的）な UI 定義を一言で説明すると、「__最終的に実現したいことを記述する__」というアプローチです。
Jetpack のトップページには、次のようなサンプルコードが掲載されています。

```kotlin
@Composable
fun JetpackCompose() {
    Card {
        var expanded = by remember { mutableStateOf(false) }
        Column(Modifier.clickable { expanded = !expanded }) {
            Image(painterResource(R.drawable.jetpack_compose))
            AnimatedVisibility(expanded) {
                Text(
                    text = "Jetpack Compose",
                    style = MaterialTheme.typography.h2,
                )
            }
        }
    }
}
```

これは、ひとつのカード (Card) を表示するためのコードですが、多くの人がこのコードをパッと見たときの感想は、「ナニコレ？オイシイノ？」だと思います。
ポイントは、「こういった状態であれば、こう表示するよ」とだけ記述しているところです。

これでもまだ伝わらないかもしれないので、もう少し具体的な例で説明します。
例えば、あるアプリの「状態 A」では「ボタンを 3 つ表示する」とします。
declarative（宣言的）なコードと、従来の imperative（命令的）なコードでは、実装が次のように変ってきます。

- declarative（宣言的）なコード
    - 状態 A なので、ボタンを 3 つ表示する。
- imperative（命令的）なコード
    - 状態 A なので、ボタンを 3 つ表示する必要がある。ただし、すでに中央のボタンを表示しているので、その左右にひとつずつボタンを追加する。

従来の、XML のレイアウトファイルを使って Kotlin コードでゴリゴリ制御するというやり方は、後者の imperative（命令的）な実装です。
実装者が、現在のアプリ状態と、UI の表示状態をうまく整合性が取れるように制御してやる必要がありました。
Compose を使うと、最終的な表示結果を意識すればよいので、実装者の負担が減り、コードの見通しもよくなります。

Compose のトップページで次のように記述されているのは、こういうことです。

> <b>直感的</b>
>
> UI を記述するだけで、残りの部分は Compose で処理されます。アプリの状態が変更されると、UI が自動的に更新されます。


React や IaC と同じ考え方では？
----

はい。
React の関数コンポーネントをそのまま Android に持ってきた感じですね。

React (JavaScript) のコードがこちら。

```javascript
function Button(label, onPress) {
  const [count, setCount] = useState(0)
  // ...
}
```

Compose (Kotlin) のコードがこちら。

```kotlin
@Composable
fun Button(label: String, onPress: () -> Unit) {
  val count = remember { mutableStateOf(0) }
  // ...
}
```

ほぼ同じです。
React のフックの仕組み (`useState` など）に対応するもの (`mutableStateOf`) もあります。

宣言型 (declarative) なパラダイムは、次のような IaC (Infrastructure as Code) の世界でも取り入れられています。

- Ansible
- Azure ResourceManager
- AWS CloudFormation

これらは、クラウドリソースや PC 内の環境構築を自動化するためのツールですが、シェルスクリプトなどで構築手順を記述するのではなく、静的な YAML/JSON ファイルなどを使って「最終的に目指すべき構成」を定義するところが、まさに declarative（宣言的）です。

もっというと、AI による自動コード生成も究極の宣言型ですね。
[GitHub Copilot](https://copilot.github.com/) などは、コメントや関数名で最終的にやりたいことを記述するだけでコードができるという。。。
こう考えると、ソフトウェア開発のやり方が declarative（宣言的）な方向に向かっていくのは自然な流れなのかもしれません。

Android は ~~ちょっと古い~~ 枯れた技術しか使わないというイメージが強いですが、やっと declarative なプログラミングパラダイムにも対応してきました。
宣言的 UI フレームワークは色々ありますが、Android で使える有名どころのフレームワークは、Compose、Flutter、React Native などがあります。
簡単にまとめるとこんな感じでしょうか。

| フレームワーク（ライブラリ） | 言語 | 特徴 | Android 特有機能 |
| ---- | ---- | ---- | :--: |
| Jetpack Compose | Kotlin | Android 専用 | ◎ |
| Flutter | Dart | Android/iOS 共通 | 〇 |
| React Native | JavaScript | Android/iOS 共通 | △ |

なんだかんだ言っても、Anrdoid の標準開発言語である Kotlin が使える Jetpack Compose が主流になっていきそうな気はします。


Jetpack Compose を使ってみる
----

ここで、Compose を使った実装方法を詳細に述べることはしませんが、Jetpack Compose は簡単に使い始めることができます。
Android Studio でプロジェクトを作成するときに、__Empty Compose Activity__ を選択すれば、Jetpack Compose を使ったテンプレートプロジェクトが自動作成されます。
次のような依存パッケージの定義も自動的に `build.gradle` に追加されます。

```groovy
implementation "androidx.compose.ui:ui:$compose_version"
implementation "androidx.compose.material:material:$compose_version"
implementation "androidx.compose.ui:ui-tooling:$compose_version"
```

現時点では、次のような `Hello Android!` と表示されるだけのプロジェクトが生成されました。

```kotlin
package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}
```

