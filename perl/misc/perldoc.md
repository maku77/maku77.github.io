---
title: "Perl のマニュアルページを表示する (perldoc)"
date: "2008-04-30"
---

Perl のマニュアルページを表示する (perldoc)
----

例えば、`perlvar` のマニュアルページを見たい場合は、**`perldoc`** コマンドの引数で次のように指定します。

```
$ perldoc perlvar
```

`perldoc` コマンドは、**Pod (plain-old document) 形式のドキュメント**を適切にフォーマットして、ページャ（`more` コマンドなど）に渡して出力してくれます。


perldoc コマンドの使い方を調べる
----

`perldoc` コマンドの使い方そのものも、`perldoc` コマンドで調べられます。

```
$ perldoc perldoc
```


Perl のマニュアルページにどのようなものがあるかを調べる
----

下記のように実行すると、`perldoc` で読めるドキュメントの一覧を表示することができます。

```
$ perldoc perl
```


Pod 形式のドキュメントファイルの格納場所
----

`perlfunc.pod` などの Pod フォーマットのドキュメントは以下のようなパスから検索されます。

- `PERL5LIB` 環境変数に設定したパス
- `PERLLIB` 環境変数に設定したパス（`PERL5LIB` が設定されていない場合）
- `PATH` 環境変数に設定したパス

例えば Windows に ActivePerl を `C:\usr` にインストールした場合、`perlfunc.pod` などの主要なドキュメントは、`C:\usr\lib\pods` ディレクトリにインストールされています。

`perldoc` コマンドで実際にドキュメントがどこから検索されたかを調べるには、`perldoc` コマンドに **`-v`** オプションを付けます。

```
$ perldoc -v perlre
...
Found as C:\usr\lib\pods\perlre.pod
...
```

`perldoc` コマンドに **`-l`** オプションを付けると、検索したディレクトリは表示せずに、実際に参照するファイル名が1つだけ表示されます。

```
$ perldoc -l perlre
C:\usr\lib\pods\perlre.pod
```


組み込み関数の説明を見る
----

`perldoc` コマンドに、**`-f`** オプションで関数名を渡すと、`perlfunc` マニュアルページを検索して、指定した組み込み関数の説明を表示してくれます。

```
$ perldoc -f <FuncName>
```


いろいろなマニュアルページ
----

#### 正規表現のマニュアル

```
$ perldoc perlre
$ perldoc perlretut
$ perldoc perlrequick
```

#### Pod (plain-old documentation) フォーマットのマニュアル

```
$ perldoc perlpod
```

#### モジュールのインストールに関するマニュアル (CPAN)

```
$ perldoc perlmodinstall
```

#### モジュールの書き方に関するマニュアル

```
$ perldoc perlmod
$ perldoc perlmodlib
```

#### プラグマ (pragma) の一覧を調べる

```
$ perldoc perlmodlib
```

#### 組み込み変数に関するマニュアル

```
$ perldoc perlvar
```

#### 組み込み演算子、組み込み関数に関するマニュアル

```
$ perldoc perlop
$ perldoc perlfunc
```

#### perl のコマンドラインオプションに関するマニュアル

```
$ perldoc perlrun
```

