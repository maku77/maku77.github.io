---
title: "画像ファイルを Markdown ファイルと同じディレクトリに置く (Page Bundle)"
date: "2019-10-19"
---

ページバンドルとは
----

初期の頃の Hugo では、画像ファイルは `static` ディレクトリに置いて、記事ファイル (Markdown) と別々のディレクトリで管理するという方法がとられていました。

- `content/`
    - `_index.md`
    - `page1.md`
    - `page2.md`
    - `page3.md`
- `static/`
    - `image1.png`
    - `image2.png`
    - `image3.png`

しかし、これでは Markdown ファイルと画像ファイルをバラバラに管理しないといけないというユーザーの不満が出て、現状は `content` ディレクトリ以下に一緒に配置できるようになっています。
この仕組みを **ページバンドル (Page Bundle)** と言います。

- 参考: [Page Bundles - Hugo](https://gohugo.io/content-management/page-bundles)

ページバンドルは、その名のとおり、各ページにバンドルする形で画像ファイル（リソース）を保持するという考え方です。
ページバンドルを作成するには、`content` ディレクトリ以下に `_index.md` あるいは `index.md` を含むディレクトリを作成し、そのディレクトリ内に一緒に画像ファイルなどのリソースファイルを格納します（画像ファイル以外のファイルでも OK）。
つまり、バンドルされた画像ファイルを表示したいページは、`_index.md` あるいは `index.md` というファイル名のコンテンツでなければいけません。

Page Bundle には、**Branch Bundle** と **Leaf Bundle** の 2 種類があり、`_index.md` を含むディレクトリは Branch Bundle となり、`index.md` を含むディレクトリは Leaf Bundle となります。

- `content/`
    - `branch/`  **← Branch Bundle**
        - `_index.md`
        - `image1.png`
        - `data/`
            - `image2.png`  （不正なリソース）
        - `leaf/`  **← Leaf Bundle**
            - `index.md`
            - `image3.png`  （Leaf Bundle のリソース）
            - `data/`
                - `image4.png`  （Leaf Bundle のリソース）

Branch Bundle は、いわゆるセクションを構成するものなので、その下にさらに別のバンドル (Branch Bundle あるいは Leaf Bundle) を入れ子で持つことができます。
一方 Leaf Bundle は、末端のページであり、その下に別のバンドルを持つことはできません（というかその下に子ページを作れない）。

本家のサイトの記述を理解するのには中々時間がかかりますが、かみ砕いてまとめると次のような感じです。

- <b>Branch Bundle</b>
    - `_index.md` を持つディレクトリ。いわゆるセクションページに、画像などのリソースをくっつけたもの。
    - 入れ子で別のバンドルを含むことができる（子ページを持てることと同義）。
    - リソースファイル用のサブディレクトリを持つことはできない（サブディレクトリは、あくまで別のページ用に作るものである）。だから、**`_index.md` から参照する画像ファイルなどは、`_index.md` と同じディレクトリ内に並べて置く**必要がある。
    - このディレクトリ以下に配置した Markdown ファイル (.md) は、別の独立したシングルページとして存在することになる（当たり前のように思えるけど、下の Leaf Bundle の場合はちょっと違う扱いになる）。
- <b>Leaf Bundle</b>
    - `index.md` を持つディレクトリ。いわゆるシングルページに、専用のディレクトリを割り当てたもの。
    - 入れ子で別のバンドルは含められない。だから、ブランチ（枝）に対して、リーフ（葉）という名前が付けられている。
    - リソースファイル用のサブディレクトリを持つことができる。このディレクトリ以下に置いた `index.md` 以外のファイルは、どんなに深いディレクトリに置こうが、この Leaf Bundle のリソースである。だから、**`index.md`** から参照する画像ファイルは、それ以下の階層であればどこにでも置ける**。
    - Markdown ファイル (.md) でさえ、このディレクトリ内に配置したら、Leaf Bundle のリソースという扱いになる（単独で表示する記事ページとはみなされない）。


Branch Bundle 内でのリソース参照
----

Branch Bundle から見ていきます。
下記の `content` ディレクトリは Branch Bundle を構成している部分の抜粋です。

- `content/`
    - `branch/`  **← Branch Bundle**
        - `_index.md`
        - `image1.png`
        - `data/`
            - `image2.png`  （不正なリソース）

`_index.md`（セクションページ）の中から、同じ階層に置いた `image1.png` を下記のように参照することができます。
1 行目は HTML のべた書き、2 行目は Markdown の文法、3 行目は Hugo 組み込みの [figure ショートコード](https://gohugo.io/content-management/shortcodes/#figure) を使用した記述方法です。
まぁ迷ったらショートコードを使用しておきましょう（まとめて振る舞いを変えやすいので）。

```
<img src="image1.png" alt="画像1">
![画像1](image1.png)
{{ "{{" }}< figure src="image1.png" alt="画像1" >}}
```

`content/branch` ディレクトリの下には、悪いリソースの配置例として `data/image2.png` ファイルを用意してあります。
実は Branch Bundle では、このように **`_index.md` と違う階層に配置したリソースを参照することができません**。
デフォルトの状態では、下記のようにすれば参照できてしまうかもしれませんが、

```
<img src="data/image2.png">
![data/image2](data/image2.png)
{{ "{{" }}< figure src="data/image2.png" >}}
```

`_index.md` のフロントマターで `url` プロパティなどを指定して、出力先のディレクトリを変更したとたんに 404 Not Found エラーになってしまいます。
なぜなら、`data` ディレクトリに配置した画像ファイルの出力先（コピー先）は、`_index.md` の出力先と連動して変化してくれないからです。
つまり、`data` ディレクトリ内のファイルは、`_index.md` にバンドルされたリソースとして扱われていないということです。

以上のような理由により、Branch Bundle では、基本的に画像ファイルを `_index.md` と同じディレクトリ内に配置することになります。
もちろん、フルパス指定で `<img src="/branch/data/image2.png">` と記述すれば、まったく別のディレクトリに出力された画像ファイルを参照できてしまいますが、そのような指定をするくらいであれば、従来の `static` ディレクトリを使ってリソースファイルを管理した方が余計なトラブルの心配が少ないと思います。


Leaf Bundle 内でのリソース参照
----

次に、もうひとつのページバンドルの種類である Leaf Bundle です。
下記の `content` ディレクトリは Leaf Bundle を構成している部分の抜粋です。
Leaf Bundle は Branch Bundle の下に入れ子にする形で配置できます。
というか、Leaf Bundle 同士で入れ子にしなければどこにでも置けます。

- `content/`
    - `branch/`  **← Branch Bundle**
        - `leaf/`  **← Leaf Bundle**
            - `index.md`
            - `image3.png`  （Leaf Bundle のリソース）
            - `data/`
                - `image4.png`  （Leaf Bundle のリソース）

`index.md` ファイル（アンダースコアなし）を配置したディレクトリが Leaf Bundle となります。
Branch Bundle と同様に、`index.md` ファイルと同じ階層に置かれた画像ファイルは、次のように参照することができます。

```
<img src="image3.png" alt="画像3">
![画像3](image3.png)
{{ "{{" }}< figure src="image3.png" alt="画像3" >}}
```

Branch Bundle とは異なり、Leaf Bundle ではそのディレクトリ以下のファイルがすべて自分自身にバンドルされているものだとみなされるため、深い階層に置いた画像ファイルにも直感的に（相対パスで）アクセスできます。

```
<img src="data/image4.png" alt="画像4">
![画像4](data/image4.png)
{{ "{{" }}< figure src="data/image4.png" alt="画像4" >}}
```

逆に、`index.md` ファイルを置いたディレクトリ以下には、別のページを構成するための Markdown ファイルを配置することはできません。
配置したとしても、それはあくまで Leaf Bundle 内のリソースファイルとして扱われます。
ショートコードなどで、`.Page.Resources` 配列の内容を出力してみると、画像ファイルと一緒に Markdown ファイルも `index.md` ページに付随するリソースとして含まれていることが分かります。
`index.md` を配置したディレクトリは、そのページ専用のディレクトリになると考えましょう。


（応用）ページバンドルに含まれるリソースファイルの一覧を表示する
----

下記のショートコードは、現在のページにバンドルされているリソースファイルを列挙します。

#### layouts/shortcodes/list-page-resources.html

```
<ul>
  {{ "{{" }}- range .Page.Resources }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Name }}</a>
  {{ "{{" }}- end }}
</ul>
```

任意のページの Markdown ファイル内で下記のように使用します。

```
{{ "{{" }}< list-page-resources >}}
```

- 参考: [Page Resources - Hugo](https://gohugo.io/content-management/page-resources/)


（応用）別のページのページバンドルリソースを参照する
----

`.Site.GetPage` 関数などを使用して、別のページの `Page` インスタンスを取得すれば、そのページバンドルとして格納されているリソースファイルを参照することができます（もちろん、そのページがページバンドルになっている必要があります）。

ただ、こういった仕組みを使いすぎると、全体的に複雑な構成になってメンテナンス性が下がってしまいます。
共有したいリソースファイルがあるときは、できるだけ `static` ディレクトリを使って単純なファイルコピーで済ませられないか検討することをオススメします。

下記のショートコードを使用すると、`dir` パラメータで指定したページから、`src` で指定した名前の画像リソースを参照して表示できます。

#### layouts/shortcodes/shared-image.html

```
{{ "{{" }} $dir := .Get "dir" }}
{{ "{{" }} $src := .Get "src" }}
{{ "{{" }} $page := .Page.GetPage $dir }}
{{ "{{" }} $imagePath := ($page.Resources.GetMatch $src).RelPermalink }}

<img src="{{ "{{" }} $imagePath }}">
```

例えば、下記のような構成で `data/index.md` による Leaf Bundle が作成されていて、その中の `sample.png` を外側のページから参照したいとします。

- `content/`
    - `section/`
        - `data/`  **← Leaf Bundle**
            - `index.md` （Leaf Bundle を生成するには index.md が必要）
            - `sample.png`
        - `_index.md` （セクションページ）
        - `page.md`  （シングルページ）

`section/_index.md` や `section/page.md` の中から、下記のようにショートコードを呼び出すと、`data/sample.png` を表示することができます。
つまり、2 つのページから 1 つのページバンドル内の画像を共有できます。

```
{{ "{{" }}< shared-image dir="data" src="sample.png" >}}
```

`.Page.GetPage` 関数に相対パス（上記の例では `data`）を渡すと、`content` ディレクトリ内の相対的な位置関係をもとに `Page` オブジェクトを取得できるようです。
なので、仮に `section/_index.md` や `section/page.md` のフロントマターで `url` プロパティを指定をして、HTML ファイルの出力先が変わったとしてもリソースは正しく参照できます。


（応用）image ショートコードを作成する
----

Hugo は画像を表示するための組み込みショートコードとして **`figure`** を提供しています。
`figure` ショートコードの実装は、下記の GitHub リポジトリで参照できます。

- [shortcodes/figure.html - Hugo](https://github.com/gohugoio/hugo/blob/aba2647c152ffff927f42523b77ee6651630cd67/tpl/tplimpl/embedded/templates/shortcodes/figure.html)

画像の表示はいろいろとカスタマイズしたかったりするので（デフォルトで CSS クラスを付加するとか）、何か不満を感じたら、早めに自分専用のショートコードを作成してしまった方がよいでしょう。
`figure` ショートコードをベースにして少しずつ修正していけば簡単です。

私は自分用にカスタマイズした `image` という名前のショートコードを作成して使っています。
基本的に画面中央に画像を大きく表示するためのショートコードになっており、画像をインライン表示したい場合は、それ専用に作成した `image-inline` という別のショートコードを使っています。

#### layouts/shortcodes/image.html

```
（大したことしてなかったので省略）
```

