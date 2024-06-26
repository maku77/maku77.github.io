---
title: "プログラムの関数定義などにジャンプする（tags ファイルの利用）"
date: "2018-10-30"
description: "ctags プログラムを利用して、ソースコードなどの tags ファイルを作成しておくと、関数名や変数名を使って定義位置にタグジャンプすることができます。"
---

Universal Ctags コマンドのインストール
----

タグジャンプ系の機能を使用するには、あらかじめ検索対象のソースコードから `tags` ファイルを生成しておく必要があります。
`tags` ファイルを作成するには、外部コマンドの `ctags` を使用するのですが、このコマンドは下記の Universal Ctags で提供されているものを使用するのがよいでしょう。

- [Universal Ctags](http://ctags.io/)
    - [各OS用のインストールパッケージ](https://github.com/universal-ctags/ctags)

Windows であれば、ダウンロードしたアーカイブに含まれている `ctags.exe` をパスの通ったディレクトリに配置するだけでインストールは完了です。

昔は [exhuberant ctags](http://ctags.sourceforge.net/) で提供されている `ctags` が主流でしたが、現在はメンテナンスされておらず、新しい言語にも対応していないので、そこから fork されたプロジェクトである Universal Ctags の方を使うことをオススメします。

Univasal Ctags でサポートしている言語は、下記のようにして確認することができます。

~~~
$ ctags --list-languages
~~~


tags ファイルを作成する
----

`tags` ファイルを作成するには、検索したいソースコードファイルのあるディレクトリで `ctags` コマンドを実行します。
ディレクトリを再帰的に走査するには `-R (--recurse)` オプションを付けて実行してください。

~~~
$ ctags -R
~~~

ある言語のファイル群だけ（例えば .java だけ）を対象にしたいときは、`--languages` オプションを使用して以下のように実行します。

~~~
$ ctags -R --language=Java
~~~

指定するのは拡張子ではなく、言語名であることに注意してください（`ctags --list-languages` で表示される言語名から選択）。
複数の言語を指定することも可能です。

~~~
$ ctags -R --languages=Java,C,C++,Make,Python,Ruby
~~~

しばらく待って、カレントディレクトリに `tags` ファイルが生成されれば成功です。

### どの拡張子のファイルが検索対象になるか

どのような言語名でどの拡張子のファイルが走査対象になるかは、`--list-maps` オプションで確認することができます（指定するときは大文字と小文字の違いは気にしなくてよいようです）。

~~~
$ ctags --list-maps | head -5
Ada      *.adb *.ads *.Ada
Ant      build.xml *.build.xml *.ant *.xml
Asciidoc *.asc *.adoc *.asciidoc *.asc *.adoc *.asciidoc
Asm      *.A51 *.29[kK] *.[68][68][kKsSxX] *.[xX][68][68] *.asm *.ASM *.s *.S
Asp      *.asp *.asa
~~~

いくつかの言語名にはエイリアス名が定義されていて、エイリアス名でも指定することができるようになっています。
エイリアス名の一覧は `ctags --list-aliases` で確認することができます。

~~~
$ ctags --list-aliases | head -5
#LANGUAGE   ALIAS
Awk         gawk
Awk         mawk
C#          csharp
JavaScript  js
~~~


Vim に tags ファイルの検索パスを設定する（tags オプションの設定）
----

Vim が `tags` ファイルをどのように検索するかは、`tags` オプションで設定されています。
デフォルトでは下記のように設定されています（大文字の `TAGS` も含まれているかも）。

~~~
:set tags=./tags,tags
~~~

複数のパスがカンマ区切りで指定されており、それぞれ下記のような意味をもっています。

- <b>`./tags`</b>: 現在編集中のファイルと同じディレクトリから `tags` を探す
- <b>`tags`</b>: カレントディレクトリから tags を探す

この設定のままだと、同じディレクトリに `tags` ファイルがないとタグジャンプ機能が使えない（「E433: タグファイルが見つかりません」エラーになる）のでとても不便です。
**次のように末尾にセミコロン (`;`) を入れて設定しておくと、親ディレクトリを上って `tags` ファイルを検索してくれる**ようになります。

#### ~/.vimrc （tags ファイルを上位のディレクトリからも検索）

~~~ vim
set tags=./tags;,tags;
~~~

逆に下位ディレクトリを再帰的に検索する (`**`) といったワイルドカードなども使用できます。
このあたりの Vim のファイル検索の仕組みは、`:help file-searching` のヘルプで確認することができます。


tags ファイルを使って定義位置にタグジャンプする
----

### タグジャンプの基本

作成された `tags` ファイルを利用して、関数などの定義位置にタグジャンプするには下記のように **`:tag`**（あるいは **`:tags`**）コマンドを使用します。

~~~ vim
:tag FuncName   "現在のウィンドウでタグジャンプ
:stag FuncName  "ウィンドウを分割してタグジャンプ
~~~

あるいは、検索したい単語にカーソルを合わせ、下記のように入力することでもタグジャンプできます。

- <kbd>CTRL-]</kbd>: 現在のウィンドウでタグジャンプ
- <kbd>CTRL-W</kbd><kbd>]</kbd>: ウィンドウを分割してタグジャンプ

ジャンプ元に戻るには、<kbd>CTRL-T</kbd> と入力します。
連続してタグジャンプした場合は、繰り返し <kbd>CTRL-T</kbd> を入力することで、順番にジャンプ元に戻っていきます。
戻りすぎた場合は、<kbd>:tag</kbd> とパラメータなしで実行すれば、タグジャンプ先に戻れます。
例えば、下記ようなシナリオを考えてみるとわかりやすいと思います。

1. <b>ファイル1</b>から `:tag MyFunc` で MyFunc を含む<b>ファイル2</b>にタグジャンプ
2. <b>ファイル2</b>から <kbd>CTRL-]</kbd> でカーソル下のキーワードを含む<b>ファイル3</b>にタグジャンプ
3. <b>ファイル3</b>から <kbd>CTRL-T</kbd> で<b>ファイル2</b>に戻る
4. <b>ファイル2</b>から <kbd>CTRL-T</kbd> で<b>ファイル1</b>に戻る
5. <b>ファイル1</b>から <kbd>:tag</kbd> で<b>ファイル2</b>にジャンプ（戻りすぎのリドゥ）

タグジャンプで移動してきた履歴は **`:tags`** コマンドで確認することができます。
<kbd>CTRL-]</kbd> でジャンプ元に戻ったり、`:tag` コマンドで再びジャンプするときは、このタグジャンプ履歴に従って飛び回っていることになります。

ビジュアルモードでテキストを選択した状態で <kbd>CTRL-]</kbd> を入力すると、その選択範囲の文字列と一致する関数定義へタグジャンプできます。
例えば、`MyMouseListener` の中の `MouseListener` の部分だけをビジュアルモードで選択して <kbd>CTRL-]</kbd> とすれば、`MouseListener` の定義位置にタグジャンプすることができます（それはおそらく `MyMouseListener` の親クラスです）。


### 複数のタグジャンプ候補がある場合

`:tag MyFunc` でタグジャンプする場合も、<kbd>CTRL-]</kbd> でタグジャンプする場合も、ジャンプ先の候補が複数見つかったとしても、最初に見つかったところにしかジャンプしてくれません。

複数のジャンプ先が想定される場合は、`:tag` コマンドの代わりに、**`:tselect`** あるいは **`:tjump`** を使用します。

- **`:tselect MyFunc`**: タグジャンプ先の候補を表示して選択。
- **`:stselect MyFunc`**: 上記のウィンドウ分割版。
- **`:tjump MyFunc`**: タグジャンプ先の候補を表示して選択。候補が1つしかない場合は即ジャンプ
- **`:tsjump MyFunc`**: 上記のウィンドウ分割版。

これらのコマンドにも、カーソル下のキーワードをもとにジャンプするショートカットキーが割り当てられています。

| コマンド | カーソル下のキーワードを使用するショートカット |
| `:tag MyFunc` | <kbd>CTRL-]</kbd> |
| `:stag MyFunc` | <kbd>CTRL-W</kbd><kbd>CTRL-]</kbd> または <kbd>CTRL-W</kbd><kbd>]</kbd> |
| `:tselect MyFunc` | <kbd>g</kbd><kbd>]</kbd> |
| `:stselect MyFunc` | <kbd>CTRL-W</kbd><kbd>g</kbd><kbd>]</kbd> |
| `:tjump MyFunc` | <kbd>g</kbd><kbd>CTRL-]</kbd> |
| `:stjump MyFunc` | <kbd>CTRL-W</kbd><kbd>g</kbd><kbd>CTRL-]</kbd> |

たくさんありますが、**タグジャンプの基本的となる <kbd>CTRL-]</kbd> と、候補選択できる <kbd>g</kbd><kbd>CTRL-]</kbd> を覚えておけばよい**と思います。

タグジャンプ後に、複数のジャンプ先候補がある場合は、次のようなコマンドで順番に開いていくことができます。

- **`:tnext`**: 次の候補へジャンプ
- **`:tprevious`**: 前の候補へジャンプ
- **`:trewind`**: 最初の候補へジャンプ
- **`:tlast`**: 最後の候補へジャンプ


### 正規表現で検索

`:tag` コマンドや `:tjump` コマンドで単純にキーワードを入力してジャンプする場合は、そのキーワードが実在する関数名などと完全一致していなければいけません。
関数名の一部しか思い出せないような場合は、キーワードの先頭に `/` を付加することで、正規表現パターンを使って部分一致検索を行えます。

#### 関数名に FooBar を含む関数の定義へジャンプ

~~~
:tag /FooBar    "最初に見つかった定義へジャンプ
:tjump /FooBar  "複数候補があればリストから選択
~~~

関数名の先頭だけがわかっているのであれば、次のように検索できます。

#### getLast という名前で始まっている関数の定義へジャンプ

~~~
:tag /^getLast
:tjump /^getLast
~~~


### Vim 起動時にいきなりタグジャンプ

Vim を起動するときに、**`-t` オプション**を使用すると、指定した関数の定義部分をいきなり開くことができます。

#### MyFunc 関数の定義位置を Vim で開く

~~~
$ gvim -t MyFunc
~~~

**ファイル名すら指定する必要がない**ので、使い方によってはかなり便利です。
まぁ、Vim を起動したあとで `:tjump MyFunc` した方が、タグジャンプ先候補が複数ある場合にも対応できて実用性は高いのですが、これを人の前でやってみせると知らない人は驚いてくれます(笑)。

上記のように単純にキーワード指定すると、完全一致するキーワードでしか検索してくれませんが、**下記のように `/` で始まるように記述すれば、正規表現で検索してくれます**。

#### 関数名に MyFunc を含む関数の定義位置を Vim で開く（正規表現検索）

~~~
$ gvim -t /MyFunc
~~~


### マウスでタグジャンプ

Vim ユーザーであれば、マウスの使用頻度は低いかもしれませんが、マウス操作でもタグジャンプを行えるようになっています。

- <kbd>Ctrl + 左クリック</kbd>: カーソル下のキーワードでタグジャンプ（<kbd>CTRL-]</kbd> と同じ）
- <kbd>Ctrl + 右クリック</kbd>: タグジャンプ元へ戻る（<kbd>CTRL-T</kbd> と同じ）

ヘルプをブラウズするときや、プログラムのソースコードを探索しているときなどはマウスだけでどんどん読んでいけるので便利かもしれません。


コラム: タグジャンプはヘルプでも大活躍
----

`:help` コマンドで起動できるヘルプでは、各キーワードに対してあらかじめ `Ctrl-]` でタグジャンプできるようになっています。
あらかじめ `doc` ディレクトリ内に、ヘルプファイル用の `tags` ファイルが格納されているためタグジャンプが可能になっています。



応用: 指定したディレクトリに tags ファイルを出力する
----

`ctags` コマンドで `-f` オプションを使用すると、指定したパスに `tags` ファイルを出力することができます。
例えば、ホームディレクトリに `tags` ファイルを作成するには次のように実行します。

#### Windows の場合

~~~
$ ctags -f %HOME%/tags -R --languages=Java %CD%
~~~

#### Linux、macOS の場合

~~~
$ ctags -f $HOME/tags -R --languages=Java `pwd`
~~~

このように `tags` ファイルを決められたパスに置く場合は、タグファイル内のパス記述を絶対パスで出力しておかなければいけません。
上記の実行例の末尾で `%CD%` や `pwd` を指定しているのはそのためです。

既存の `tags` ファイルに追記する形で出力するには、`-a (--append)` オプションを使用します。

~~~
$ ctags -f %HOME%/tags -a -R --languages=Java %CD%
~~~

このように、固定されたパスに置かれた `tags` ファイルを利用するためには、下記のように Vim の `tags` オプションにそのパス情報を追加しておきます（末尾に `~/tags` と追加しています）。

#### ~/.vimrc

~~~
set tags=./tags,tags,~/tags
~~~

こうすることで、どのファイルを編集中であっても `~/tags` を利用したタグジャンプを行えるようになります。
とはいえ、多くの場合は上位ディレクトリの `tags` ファイルを検索するだけの設定 (`set tags=./tags;,tags;`) で十分です。

