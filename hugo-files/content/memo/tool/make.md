---
title: "make を使いこなすためのメモ"
url: "p/3r6ds3r/"
date: "2007-06-29"
tags: ["memo"]
aliases: ["/memo/tool/make.html"]
---

make の種類あれこれ
----

一番よく使用されているのは GNU make ですが、いろいろな亜種があります。

- System V make
  - Stuart I. Feldman によって作成されたオリジナルの make です
- GNU make
  - Linux の世界で一般的に使用されている make です
  - Implemented by Richard Stallman and Roland McGrath.
  - Development since Version 3.76 has been handled by Paul D. Smith.
- Microsoft 版 nmake
  - Microsoft C コンパイラ ver. 6.0A に付属
- Borland 版 make
  - Borland Turbo C++ コンパイラ ver.2 に付属

参考: MAKE の達人 (1992)


Makefile に記述する rule のフォーマット
----

Makefile には、下記のようなフォーマットで rule を記述していきます。

```make
targets : prerequisites
	command
	...
```

あるいは、

```make
targets : prerequisites ; command
	command
	...
```

それぞれ、下記のような内容を記述していきます。

targets
: 更新（あるいは作成）する対象となるファイルを指定します。複数のファイル名を指定する場合はスペースで区切ります。ワイルドカードを使ってファイル名を指定することもできます。

prerequisites
: targets の更新（あるいは作成）に必要なファイル郡を指定します。複数のファイル名を指定する場合はスペースで区切ります。ワイルドカードを使ってファイル名を指定することもできます。

command
: targets を更新（あるいは作成）するためのコマンド郡を指定します。command 行は必ず TAB で始めること。複数のコマンドを指定する場合は、次の行に記述するか、セミコロン (`;`) で区切ります。

上記のような target の処理内容をまとめて **rule** と呼びます。
１つの target を複数の rule に分けて記述することも可能ですが、そのような書き方は見通しが悪くなるため、おすすめはできません。


make コマンドの振る舞い
----

```make
target ... : prerequisites ...
	command
	...
```

`make` コマンドを実行したとき、上記のように指定された command は以下のようなルールで実行されます。

1. target を指定せずに `make` を実行すると、Makefile の最初に出てくる target を処理する（ドット (`.`) で始まる target を除く）。この target のことを **default goal** と呼ぶ。
2. target に指定したファイルが存在しない場合、あるいは、target が prerequisites に指定したファイルより古い場合は command が実行される。prerequisites は空でもよく、その場合は、target と同じ名前のファイルがない場合だけ command が実行される。
3. prerequisites に指定したものと同じ名前の target がある場合は、その target を先に評価する。

{{< code lang="make" title="Makefile の例" >}}
app : main.o util.o
	cc -o app main.o util.o

main.o : main.c util.h
	cc -c main.c

util.o : util.c util.h
	cc -c util.c

.PHONY : clean
clean :
	rm app main.o util.o
{{< /code >}}


Variables（変数）を使用する
----

`Makefile` 内に同じ内容を何度も記述しなければいけない場合は、その内容を変数に入れておくことで簡潔に記述できるようになります。
例えば、オブジェクトファイル (`.o`) のリストはよく、`objects`、`OBJECTS`、`objs`、`OBJS`、`obj`、`OBJ` といった名前の変数で表現されます。

```make
OBJECTS = obj1.o obj2.o obj3.o
```

変数の内容を取り出すには、`$(OBJECTS)` のように参照します。

{{< code lang="make" title="Variables（変数）を使った Makefile の例" >}}
objects = main.o util.o

app : $(OBJECTS)
	cc -o app $(OBJECTS)

main.o : main.c util.h
	cc -c main.c

util.o : util.c util.h
	cc -c util.c

.PHONY : clean
clean :
	rm app $(OBJECTS)
{{< /code >}}


オブジェクトファイル (.o) を作る rule の省略記法
----

ソースファイル `util.c`、`util.h` をコンパイルして `util.o` ファイルを作る rule を記述する場合、**implicit rules（暗黙のルール）** を使用して rule 記述を省略することができます。
ここでは、prerequisites から `.c` ファイルの記述を省略し、command の記述を省略することができます。
例えば、

```make
util.o : util.c util.h
	cc -c util.c
```

このような rule は、implicit rules によって以下のように 1 行で記述することができます。

```make
util.o : util.h
```

{{< code lang="make" title="長めのサンプル" >}}
OBJECTS = main.o util.o

app : $(OBJECTS)
	cc -o app $(OBJECTS)

main.o : main.c util.h
	cc -c main.c

util.o : util.c util.h
	cc -c util.c
{{< /code >}}

これを、以下のように省略して記述することができます。

```make
OBJECTS = main.o util.o

app : $(OBJECTS)
	cc -o app $(OBJECTS)

main.o : util.h
util.o : util.h
```

`main.o` と `util.o` は同じ prerequisites（ここでは `util.h`）を持つので、一行にまとめて以下のようにも書けます。
ただし、このように target をまとめることを好まない人もいます。

```make
OBJECTS = main.o util.o

app : $(OBJECTS)
	cc -o app $(OBJECTS)

$(OBJECTS) : util.h
```


Makefile の名前
----

GNU make は、以下の順番で `Makefile` を探します。

1. GNUmakefile（GNU make 以外の make はこのファイルを検索しない）
2. makefile
3. Makefile（推奨）

GNU make のマニュアルでは、`Makefile` という名前にすることを推奨しています。
これは、ディレクトリ内のファイルリストを表示したときに、比較的前の方に表示され、`README` ファイルの近くに表示される可能性が高いからです。


別の Makefile を include する
----

### include の構文

下記のような構文を使用すると、Makefile の中から別のファイルをインクルードすることができます。

```
include filenames...
```

ファイル名にはシェルのファイル名パターンを使用することができます。

```
include *.mk
```

ファイル名の中で変数 (variable) や関数 (function) を参照すると、展開されてからファイルを検索します。

```
HOGE = foo.mk bar.mk
include $(HOGE)
```

### include される Makefile の検索パス

インクルードする Makefile を相対パスで指定した場合、以下のような順番で検索されます。

1. カレントディレクトリ
2. `-I` または `--include-dir` オプションで指定したディレクトリ
3. `<prefix>/include`（通常は `/usr/local/include`）
4. `/usr/gnu/include`
5. `/usr/local/include`
6. `/usr/include`


### インクルードする Makefile が見つからなかったときのエラーを抑制する

デフォルトでは、インクルードしようとしたファイルが見つからなかったときはエラーになります。
ファイルが見つからなかった場合にエラーを出さないようにするには、`include` の前にハイフンを付けて、`-include` と記述します。

```
-include filenames...
```

GNU make 以外の make 実装では、`-include` の代わりに `sinclude` を使用するものもあります。


どんなターゲット名にも一致する %
----

```make
all:
	@echo all

%:
	@echo %
```

上記のように、ターゲット名に `%` を指定すると、任意のターゲット名に一致するものとみなされます。

```console
$ make       ⇒ ターゲット all を実行
$ make all   ⇒ ターゲット all を実行
$ make hoge  ⇒ ターゲット % を実行
```


make の２フェイズ処理（変数の展開順序について）
----

`make` コマンドが `Makefile` を処理するとき、次のように 2 フェイズに分けて処理されます。

1. **Read-in phase (1st phase):** make を実行すると、最初に Makefile の内容をすべて読み込み（インクルードしたファイルもすべて）、各ルールの依存関係や、変数の値などを内部に保持します。
2. **Target-update phase (2nd phase):** 1st phase で構築された内部構造を用い、各ルールを処理します。

この知識は、Makefile 内で変数がどのような順序で展開されるかを理解するために必要になってきます。
例えば、以下のような `Makefile` を実行すると、

```make
FOO = 100
all:
	@echo $(FOO)
FOO = 200
```

Read-in phase では `$(FOO)` の値は展開されず、`echo` コマンドが実行される Target-update phase で `$(FOO)` の値が展開されるため、`echo` される値は `200` となります。

変数の展開タイミングは、Makefile 内のどの位置で変数が使われているかによって変わってきます。
以下は、Makefile 内で記述できるフォーマットを羅列したものですが、この中の `<immediate>` と書かれた部分は、1st phase で展開される（その時点で格納されている値が使用される）ことを表しています。
`<deferred>` と書かれた部分は、1st phase では展開されない（Makefile を最後まで読んで再帰的に展開される）ことを表しています。

```
<immediate> = <deferred>
<immediate> ?= <deferred>
<immediate> := <immediate>
<immediate> += <deferred> or <immediate>
              （右辺に指定した変数が := で作成されたものなら <immediate> となる）

define <immediate>
	<deferred>
endif

<immediate> : <immediate> ; <deferred>
	<deferred>
```


ワイルドカードを使う
----

### rule の定義にワイルドカードを使う

target や prerequisite の指定には、Bourne シェルのワイルドカードを使用することができます。
command でワイルドカードを使うと、そのコマンドを実行するシェルによって展開されます。

- `*` ... 任意の文字列
- `?` ... 任意の 1 文字
- `~` ... ホームディレクトリ（Windows の場合は `HOME` 環境変数の値）
- `~john` ... john のホームディレクトリ

{{< code lang="make" title="ワイルドカードを使った Makefile の例" >}}
clean:
	rm -f *.o
{{< /code >}}

### 変数の定義にワイルドカードを使う

変数を定義するときにワイルドカードを展開したい場合は、**`wildcard`** 関数を使用します。
例えば、

```make
OBJECTS = *.o
```

とすると、`OBJECTS` の値は `*.o` のままになります。
この `*.o` を展開したものを `OBJECTS` に代入したい場合は、次のように記述する必要があります。

```make
OBJECTS := $(wildcard *.o)
```

右辺の関数やワイルドカードを代入時に展開するために、代入演算子として `=` ではなく、`:=` を使用している点に注意してください。


### ワイルドカードのエスケープ

ファイル名の中に `*` を含むものを指定したい場合は、バックスラッシュ (`\`) でエスケープします。

```
foo\*.c  ==> 'foo*.c' というファイル名を示す
```

このエスケープ処理は、Windows のパス指定でバックスラッシュ (`/`) を含む場合にしばしば問題になります。
例えば、`C:\foo` ディレクトリの下の `.cpp` ファイルをワイルドカードで示したい場合に、

```
C:\foo\*.cpp
```

のように記述すると、アスタリスク (`*`) をエスケープしようとして、`C:\foo*.cpp` というファイルを表すことになってしまいます。
この問題を避けるためには、Windows のディレクトリ・セパレータとして、Unix と同様にスラッシュ (`/`) を使用するようにします。

```
C:/foo/*.cpp
```


Prerequisites の検索パスを追加する (VPATH, vpath)
----

### VPATH 変数

target や prerequisites の検索パスを追加したい時は、**`VPATH`** 変数にディレクトリのパスを設定します。
複数のパスを指定したい場合は、コロン (`:`) かスペースで区切って指定します（Windows の場合はセミコロン (`;`) で区切ります）。
カレントディレクトリはデフォルトで検索するので、指定する必要はありません。

```make
VPATH = src:../headers
```

上記のように設定すると、

```make
foo.o : foo.c
```

というルールを処理するとき、カレントディレクトリで foo.c が見つからない場合、`src/foo.c` が検索され、さらに見つからない場合に `../headers/foo.c` が検索されるようになります。


### ファイルの種類ごとに検索パスを追加する vpath ディレクティブ

`vpath` ディレクティブを使用すると、ファイル名に応じて別々の検索パスを設定することができます。
例えば、

```
vpath %.h   ../headers
vpath %.cpp src
vpath %     hoge
```

このようにすると、拡張子が `.h` のファイルの検索パスとして `../headers` ディレクトリが追加され、拡張子が `.cpp` のファイルの検索パスとして `src` ディレクトリが追加されます。
さらに、すべてのファイルの検索パスとして `hoge` ディレクトリが追加されます。

ディレクトリパスは `VPATH` の場合と同様に、コロンで区切って複数指定することができます。
検索の順序は `vpath` ディレクティブの呼び出し順序に従います。
例えば、

```
vpath %.cpp foo:bar
vpath %.cpp hoge
```

と指定すると、`.cpp` ファイルが、カレントディレクトリ ⇒ `foo` ⇒ `bar` ⇒ `hoge` の順番で検索されます。

検索パスをクリアしたい場合は、ディレクトリパスを指定せずに `vpath` ディレクティブを呼び出します。

```
vpath %       # すべてのファイルの検索パスを削除
vpath %.cpp   # .cpp ファイルの検索パスを削除
```


prerequisites で指定したファイルを command 行で参照するマクロ ($^, $<)
----

command 行で **`$^`** を使用すると、prerequisites で指定したすべてのファイル名に展開されます。

```make
app : a.cpp b.cpp c.cpp
	g++ $(CXXFLAGS) $^ -o $@
```

上記のように記載すると、下記のように展開されます。

- **`$^`** ⇒ `a.cpp b.cpp c.cpp`
- **`$@`** ⇒ `app`

command 行で **`$<`** を使用すると、prerequisites で指定した最初のファイル名に展開されます。

```make
foo.o : foo.cpp foo.h defs.h
	g++ -c $(CXXFLAGS) $< -o $@
```

- **`$<`** ⇒ `foo.cpp`
- **`$@`** ⇒ `foo.o`

`$^` の `^` という記号が上を指す矢印、`$<` の `<` という記号が左を指す矢印だと考えると覚えやすいです。


リンクライブラリの検索パス
----

prerequisistes に `-l<name>` という形式でリンクライブラリを指定しておくと、`lib<name>.so`、あるいは `lib<name>.a` が検索されます。
例えば、

```make
foo : foo.c -lcurses
	cc $^ -o $@
```

とすると、以下の順番で `libcurses.so` ファイルが検索されます。

1. カレントディレクトリ
1. `vpath` に設定したディレクトリ
1. `VPATH` に設定したディレクトリ
1. `/lib`
1. `/usr/lib`
1. `<prefix>/lib` （通常は `/usr/local/lib`）

`libcurses.so` ファイルが見つからなかった場合は、上記の順で `libcurses.a` ファイルが検索されます。

prerequisites に `-l<name>` と指定した場合に、どんな名前のファイルを検索するかは、**`.LIBPATTERNS`** 変数に設定されたパターンによって決められます。
デフォルトでは次のような値に設定されています。

```make
.LIBPATTERNS = lib%.so lib%.a
```

よって、`-lcurses` と指定したときに、`libcurses.so` と `libcurses.a` ファイルが検索されることになります。


.PHONY ターゲットの役割
----

```make
clean:
	rm *.o temp
```

というルールが設定されているとき、通常は

```console
$ make clean
```

と実行すると、`rm` コマンドが実行されます。
ただし、上記のルールは、基本的には `clean` というファイルを作成するためのルールとみなされるので、カレントディレクトリに `clean` というファイルが存在する場合は `rm` コマンドが実行されなくなってしまいます。

カレントディレクトリに `clean` というファイルが存在する場合でも `rm` コマンドを実行するようにするには、**`.PHONY`** ターゲットの prerequisites に `clean` を指定するようにします。

```make
.PHONY: clean
clean:
	rm *.o temp
```

`.PHONY` ターゲットに記述しておくことで、ファイル検索にかかる時間を省略できるというメリットもあります。
「ターゲット名＝ファイル名」でないときは、`.PHONY` ターゲットに指定しておくとよいです。

`.PHONY` ターゲットが使用できない make 実装の場合は、空のターゲット **`FORCE`** を用意して以下のようにするのが慣例となっています。

```make
clean: FORCE
	rm $(OBJECTS)
FORCE:
```


make コマンドのパラメータで make 変数を定義する
----

`make` コマンドを実行するときのパラメータで、make 変数を定義することができます。
次のサンプルは、`HOGE` という make 変数の値を、コマンドラインパラメータで指定する例です。

{{< code lang="make" title="Makefile" >}}
HOGE=
all:
	@echo $(HOGE)
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ make HOGE=aaa
aaa
{{< /code >}}

`Makefile` 内で定義された変数の値は、`make` コマンドのパラメータで上書きされます。

{{< code lang="make" title="Makefile" >}}
HOGE=100
all:
	@echo $(HOGE)
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ make
100

$ make HOGE=200
200
{{< /code >}}


Static Pattern Rules: target 名から prerequisites を自動構成するためのルール
----

### Static Pattern Rules の構文

```make
targets ...: target-pattern: prereq-patterns ...
	commands
       ...
```

targets では、通常のルールと同様にワイルドカードを使用することができます。
target-pattern には、一つの `%` を含めることができ、targets に指定したファイル名の一部にマッチするように指定します。

{{< code lang="make" title="Static Pattern Rules の例" >}}
OBJECTS = foo.o bar.o

all: $(OBJECTS)

$(OBJECTS): %.o: %.c
	$(CC) -c $(CFLAGS) $< -o $@
{{< /code >}}

上記の例では、`foo.o` がターゲットになったとき、target-pattern の `%.o` に一致します。
この場合 `%` に一致する部分文字列は `foo` であり、自動的に `foo.c` という prerequisite が構成されます。
この `%` に一致した部分文字列のことを **stem** と呼びます。

この Static Pattern Rule により、自動的に以下のような `foo.o`、`bar.o` の構築ルールが生成されます。

```make
foo.o: foo.c
	$(CC) -c $(CFLAGS) foo.c -o foo.o
bar.o: bar.c
	$(CC) -c $(CFLAGS) bar.c -o bar.o
```

### Static Pattern Rules の command で stem を参照する

```make
bigoutput littleoutput: %output: text.g
	generate text.g -$* > $@
```

command 行で stem（`%` に一致した部分文字列）を参照するには `$*` を使用します。
上記の Static Pattern Rule では、`$*` の部分に `big`, `little` という文字列が展開されるので、結果として以下のように解釈されます。

```make
bigoutput: text.g
	generate text.g -big > bigoutput
littleoutput: text.g
	generate text.g -little > littleoutput
```


command 行のあれこれ
----

### コマンド行の解釈に使用されるシェル

コマンドの解釈はデフォルトでは `/bin/sh` が使用されます。

```make
all:
	@echo $$SHELL    # shell 変数
	@echo $(SHELL)   # make 変数
```

{{< code lang="console" title="実行例" >}}
$ make
/bin/sh
/bin/sh
{{< /code >}}

コマンドの解釈に使用するシェルを変更するには、make 変数の `SHELL` を設定します。

```make
SHELL = /bin/bash
all:
	@echo $$SHELL    # shell 変数
	@echo $(SHELL)   # make 変数
```

{{< code lang="console" title="実行例" >}}
$ make
/bin/bash
/bin/bash
{{< /code >}}

### MS-DOS, Windows の場合の make 変数 SHELL の解釈について

MS-DOS, Windows 版の `make` ではデフォルトのシェルとして **`COMSPEC`** 環境変数に設定されているものが使用されます。
`SHELL` 変数に Unix 形式のパスでシェルを指定すると、以下のようにシェルの実行ファイルが検索されます。

1. 指定されたパス通りのファイル（`SHELL = /bin/sh` とした場合、カレントドライブが `C:` の場合は `C:\bin\sh`）
2. カレントディレクトリのファイル
3. `PATH` 環境変数に指定されたディレクトリにあるファイル

完全にファイル名が一致するファイルがない場合、実行可能な形式のファイルが検索されます（`.exe`, `.com`, `.bat`, `.btm`, `.sh`）。
以上のようなルールにより、`SHELL = /bin/sh` と指定しておけば、`PATH` の通ったディレクトリに、`sh.exe` があればそれが使用されます。

### command 行間の空白行、コメントについて

2 つのコマンド行の間の空白行や、make のコメント行は無視されます。

```make
all:
	@echo 100

	@echo 200
# Comment for make.
	@echo 300
```

上記の `Makefile` を使って `make` コマンドを実行すると、以下のように表示されます。

```console
$ make
100
200
300
```

行頭がタブ文字で始まっている場合は、その後ろが空白であったとしても、必ずシェルによって処理されます。
例えば、

```
all:
	@echo 100
[TAB]
	@echo 200
	# Comment for shell.
	@echo 300
```

とすると、シェルに空白行と、コメント行が渡されることになります。
ただし、`/bin/sh` は渡された空白行とコメント行を無視するので、実際には何も実行されていないかのように見え、先の例と結果は同じになります。

```console
$ make
100
200
300
```

### コマンド行での make 変数の展開について

コマンド行の変数は `Makefile` を全て読んだ後に展開されます。
ただし、リビルド対象となったターゲットのコマンドに出てくる変数のみ展開されます。

```make
HOGE = aaa
all:
	@echo $(HOGE)
HOGE = bbb
```

```console
$ make
bbb
```

### コマンド行でのシェル変数の展開について

コマンド行でシェル変数を使用するときは、make 変数を参照するときの `$` 記号と区別するために、変数名の前に `$$` を付けるようにします（`$$` を２つ並べることで、`$` がエスケープ処理されて、シェルコマンド本来の `$` 記号の振る舞いをするようになります）。

```make
LIST = one two three
all:
	for i in $(LIST); do \
         echo $$i; \
	done
```

command 行は各行ごとにサブシェルで実行されるので、上記のような for ループは、必ずバックスラッシュ (`\`) で各行を連結させる必要があります。

### コマンド行は各行ごとにサブシェルで実行される

コマンド行は各行ごとにサブシェルか起動されて実行されます。
たとえ `cd` コマンドでカレントディレクトリを変更したとしても、次のコマンド行にはそのカレントディレクトリを引き継ぐことができません。

```make
all:
	cd hoge
	pwd       # hoge ディレクトリではない
```

この問題に対処するには、`&&` 演算子でコマンドを繋いでひとつのコマンド行にまとめてやる必要があります。

```make
all:
	cd hoge && pwd    # pwd コマンドは hoge ディレクトリで実行される
```

注）MS-DOS ではカレントディレクトリがグローバルに保持されるため、上記のようにコマンドをまとめなくても `cd` コマンドの結果が次のコマンド行にも反映されます。

### コマンドの実行でエラーが出ても処理を継続する

command の実行でエラー（終了ステータスが 0 以外）が発生した場合、make はそれ以降の command を実行しないで終了します。
command の実行でエラーが発生しても処理を継続するには、command の前に `-` を付けます（この `-` はシェルに command 行が渡される時に削除されます）。

例えば、`mkdir` コマンドは、既にディレクトリが存在する場合にはエラーで終了しますが、このエラーによって make の処理を終了しないようにするには以下のように記述します。

```make
all:
	-mkdir hoge
	@echo Hello
```

`rm` コマンドなども同様です。

```make
clean:
	-rm -f *.o
```

すべてのコマンドのエラーを無視するようにするには、`make` を実行するときのパラメータに `-i` (`--ignore-erros`) を指定するか、スペシャルターゲットの `.IGNORE` を prerequisites を指定せずに `Makefile` の中に記述します。
ただし、それぞれのコマンドの振る舞いを明確にするために `-` を使うことが推奨されています。

複数の prerequisites を処理している最中に、command の実行エラーが発生した場合に、残りの prerequisites の target の処理を継続させるには `-k` (`--keep-going`) オプションを指定して `make` を実行します。


再帰 make (recursive make)
----

### 再起 make とは

コマンド行で `make` コマンドを実行することで、再帰的に `make` を実行することができます。
例えば、大きなプロジェクトでは、ディレクトリごとに `Makefile` を置き、サブシステムごとのビルド方法を定義します。

```make
subsystem:
	cd subdir && $(MAKE)
```

あるいは

```make
subsystem:
	$(MAKE) -C subdir
```

`MAKE` 変数は、トップレベルの `Makefile` を読み出した `make` のパスに置き換えられます。

`make` コマンドを `-t` (`--touch`) オプションを付けて起動した場合は、通常 command 行は処理されませんが、command 行に、上記のように `$(MAKE)` が含まれている場合、例外として command 行が処理されることになっています。
この例外的処理のおかげで、`make -t` を実行した場合でも、再帰的な `make` 呼び出しを正しく実行することができます。
ちなみに、`$(MAKE)` のない command 行でも、先頭に `+` 記号を付けることで処理のスキップを避けることができます。

つまり、再帰 make において、`make` コマンドを実行するときは、以下のような理由で `$(MAKE)` を指定するべきだと言えます。

- すべての make 処理で同一の `make` コマンドを利用できる（トップレベルで起動した `make`）。
- `make` のオプションで `-t`、`-n`、`-q` を指定した場合にも、再帰 make 呼び出しを正しく実行することができる。

### 再帰 make で sub-make に変数の値を渡す

デフォルトでは、呼び出し側の `Makefile` で定義した変数の値は、sub-make 側に渡されません。
sub-make に対して変数の値を公開するには、`export` ディレクティブを使用します（各 command への `export` ではなく、あくまで sub-make への `export` です）。

```
export <variable> ...
```

`export` ディレクティブでは、値の代入を同時に行うことができます。

{{< code lang="make" title="Makefile" >}}
export HOGE = 100
all:
	$(MAKE) -f sub.mk
{{< /code >}}

{{< code lang="make" title="sub.mk" >}}
all:
	@echo $(HOGE)
{{< /code >}}

`export` の処理は、変数の定義と同様に `Makefile` の呼び出し時に行われます。
よって、command 行よりも後ろに `export` が記述されている場合も、その定義は有効になります。

すべての変数を sub-make 側に渡すには、パラメータなしで `export` を実行します。
このとき、特定の変数の内容だけ渡さないようにするには `unexport` ディレクティブを使用します。

```make
export
unexport FOO BAR
```

`make` 実行時に、コマンドライン・パラメータとして指定した変数は、デフォルトで sub-make にも引き渡されます。
これらのパラメータは、内部的に `MAKEFLAGS` という変数に保持されています。
例えば、

```console
$ make -k HOGE=100
```

のように実行すると、`$(MAKEFLAGS)` の値は `k -- HOGE=100` のような感じで、make が内部的に理解しやすい形に変換されて保持されます。
オプションの性質上、`MAKEFLAGS` 変数に保持されないパラメータもあります（`-C`、`-f`、`-o`、`-W` など）。

sub-make に対して、コマンドライン・パラメータを渡さないようにするには、以下のように `MAKEFLAGS` の値を空にします。

```make
subsystem:
	cd subdir && $(MAKE) MAKEFLAGS=
```

似たような変数に、コマンドラインで指定したオプションのみを参照する `MFLAGS` 変数や、コマンドラインで指定した変数定義のみを参照する `MAKEOVERRIDES` 変数などがありますが、これらは互換性のために残されており、`MAKEFLAGS` 変数の使用が推奨されています。
`Makefile` の中で、`MAKEFLAGS` 変数にオプションを追加して make の振る舞いを変更することもできますが、大きく動作が変わるようなオプションを指定することは避けるべきです。

### top-level の make か sub-make かを調べる

再帰 make において、何階層目の sub-make かを調べたいときは、`MAKELEVEL` 変数を参照します。
トップレベルの make では、この値は 0 になります。

```
all:
ifeq ($(MAKELEVEL), 0)
	@echo top-level make.
else
	@echo not top-level make.
endif
```


複数の command 呼び出しをまとめて使いまわす (Canned Command Sequences)
----

`define - endef` ディレクティブを使用すると、複数のコマンド呼び出しをひとつにまとめることができます (Canned Command Sequence)。

ここで定義する command 行は、TAB 文字で初まっている必要はありません。
command 行の中で使用した変数は、実際に command が実行されるときに展開されます。
定義した Canned Command Sequence は、複数のターゲットで使いまわすことができます。

{{< code title="Makefile" >}}
define greet-and-print-target
  echo Hello.
  @echo Target is $@.
endef

all:
	$(greet-and-print-target)
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ make
echo Hello.
Hello.
Target is all.
{{< /code >}}

Canned Command Sequence の呼び出し時に、`@` などのプレフィックスを付けた場合、すべての command 行にプレフィックスを付けた場合と同様の効果が現れます。

```make
all:
	@($greet-and-print-target)
```

```console
$ make
Hello.
Target is all.
```


Makefile 内での条件分岐 (Condtional Parts)
----

`Makefile` の中で **`ifeq`** ディレクティブや **`ifneq`** ディレクティブを使用すると、変数同士の比較や、変数と文字列の比較を行って処理を分岐させることができます。

```
HOGE = aaa

all:
ifeq ($(HOGE), aaa)
	@echo matched.
else
	@echo not matched.
endif
```

これらの条件分岐は、もちろん command 行以外でも使用できます。

```
ifeq ($(DEBUG), 1)
  LIBS = $(LIBS_FOR_DEBUG)
else
  LIBS = $(LIBS_FOR_RELEASE)
endif
```

上記の例では、変数定義部で分かりやすいようにインデントしています。
command 行以外でのインデントには、**TAB 文字ではなく、半角スペースを使う** ようにしてください。
TAB 文字を使用すると、command 行だとみなされてしまいます。

`ifeq` の構文としては、下記のいずれかを使用することができます。

```
ifeq (arg1, arg2)
ifeq 'arg1' 'arg2'
ifeq "arg1" "arg2"
ifeq 'arg1' "arg2"
ifeq "arg2" 'arg2'
```

`ifeq` の代わりに、**`ifneq`** を使用すると、2 つの引数が一致していないことを調べることができます。

```
ifneq ($(HOGE), aaa)
	@echo Not matched.
else
	@echo Matched.
endif
```

変数の内容が空かどうかを調べるには以下のようにします。
この例では、`strip` 関数により、空白文字を削除してから変数の値が空かどうかをチェックしています。

```
ifeq ($(strip $(HOGE)),)
	@echo Empty.
else
	@echo Not empty.
endif
```

条件チェックは `Makefile` を読み込むときに行うので、変数の定義のタイミングによって動作が変化します。

{{< code title="Makefile" >}}
HOGE = 1

all:
ifeq ($(HOGE), 1)
	@echo Hoge is one.
endif

HOGE = 2
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make
Hoge is one.
{{< /code >}}

### 条件文での OR や AND

バージョン 3.81（2006/04 リリース）からは、AND 条件や OR 条件を表現するための、**`$(and)`** や **`$(or)`** が使えるようになったようです。
それ以前のバージョンだと、`ifeq` や `ifdef` をネストしてがんばります。

- 参考: [2006-09-03 - bopperjp の日記](https://bopperjp.hatenablog.com/entries/2006/09/03)

GNU Make document (Ver. 3.81) を読むと、

```
条件
  ...
else 条件
  ...
else
  ...
endif
```

のように `else if` できるように書いてありますが、少なくとも Ver. 3.79.1 ではうまく動きませんでした。
`else` の後ろにさらに条件を書こうとすると、

```console
$ make
Makefile:5: Extraneous text after `else' directive
Makefile:7: *** only one `else' per conditional.  Stop.
```

みたいなエラーになってしまいます。


変数の詳細
----

### 基本

GNU make 以外の make では、変数のことをマクロと呼んでいるものもあります。
変数の値を参照するときは、**`$(foo)`** あるいは、**`${foo}`** のように記述します。
変数展開は `Makefile` の読み込み時に行われますが、command 行に記述された変数は、command が実行される段階で展開されます。

変数名は C 言語と同様、大文字／小文字が区別されます。
変数名は伝統的に大文字だけで構成されることが多いのですが、GNU make のマニュアルでは、ユーザ定義の変数は小文字だけで構成することを推奨しています。これは、implicit rule で使用される変数や、コマンドのオプションを定義するために用意されている変数と区別しやすくするためです。

{{< code lang="make" title="例: 変数のデフォルト値を確認してみる" >}}
.PHONY: all
all:
	@echo CC = $(CC)
	@echo CFLAGS = $(CFLAGS)
	@echo CXX = $(CXX)
	@echo CXXFLAGS = $(CXXFLAGS)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make
CC = cc
CFLAGS =
CXX = g++
CXXFLAGS =
{{< /code >}}

### 変数の定義

変数を定義するときに他の変数の値を参照する場合、その展開方法が大きく分けて２種類あります。
展開方法は変数代入時に使用する演算子などで決定されます。

1 つ目の展開方法は、**Recursively expanded variable（再帰展開変数）** です。
`=` 演算子や、`define` を使って変数を定義する場合、右辺の変数は再帰的に展開が行われます。
つまり、`Makefile` を最後まで処理して、右辺の変数の値が最終的にどのような値になるかが決定されます。

{{< code lang="make" title="Makefile" >}}
a = $(b)
b = $(c)
c = foo
c = bar

all:; @echo $(a)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make
bar
{{< /code >}}

変数が再帰的に展開されるため、次のように自分自身を参照しようとするとエラーになります。

{{< code lang="make" title="Makefile" >}}
a = foo
a = $(a) bar

all:; @echo $(a)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make
Makefile:4: *** Recursive variable `a' references itself (eventually).  Stop.
{{< /code >}}

2 つ目の展開方法は、**Simply expanded variables（単純展開変数）** です。
`:=` 演算子を使用して変数を定義すると、代入時に格納されている値が、参照結果として使用されます。
一般的な手続き型のプログラム言語の変数定義ような感覚で使用することができます。

{{< code lang="make" title="Makefile" >}}
a := foo
b := $(a)
a := bar

all:; @echo $(b)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make
foo
{{< /code >}}

当然、まだ定義されていない変数を参照すると、その中身は空っぽです。

{{< code lang="make" title="Makefile" >}}
a := $(b)

all:; @echo $(a)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make

{{< /code >}}

### 変数が未定義のときのみ定義する Conditional variable assignment (?=)

**`?=`** 演算子を使うと、その変数がまだ未定義のときのみ値を代入（定義）することができます。

{{< code lang="make" title="例1: 変数がすでに定義されているケース" >}}
a = 100
a ?= 200
all:; @echo $(a)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make
100
{{< /code >}}

{{< code lang="make" title="例2: 変数が未定義のケース" >}}
a ?= 200
all:; @echo $(a)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make
200
{{< /code >}}

`?=` による代入は、以下のような条件文と同様の効果を持ちます。

```
ifeq ($(origin FOO), undefined)
  FOO = bar
endif
```

変数に空文字が代入されている時は、その変数は未定義であるとはみなされません。

{{< code lang="make" title="Makefile" >}}
a =
a ?= 100
all:; @echo $(a)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make
100
{{< /code >}}

### 変数のサフィックスを置換して展開する (Substitution References)

```make
$(var:aaa=bbb)
```

という書式で変数を参照すると、変数中の文字列の `aaa` が `bbb` に置換されて展開されます。
ただし、変換されるのは、文字列の末尾、あるいは空白文字の直前の `aaa` だけです。

{{< code lang="make" title="例" >}}
foo := a.o b.o c.o.o
bar := $(foo:.o=.c)

all:; @echo $(bar)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make
a.c b.c c.o.c
{{< /code >}}

`c.o.o` の末尾の `.o` だけが `.c` に変換されているところに注目です。

### 変数の値の最後の空白は strip されない

変数を `=` や `:=` で定義するとき、`=` の直後の空白は無視されますが、行末の空白スペースは変数の値として格納されます。

```make
hoge = aaa   # 3 spaces
```

とすると、`$(hoge)` の値は、`aaa___`（`_`はスペース）となります。
行末に意図的にスペースを入れたい場合は、上記のように行末にコメントを入れることで、見た目でスペースがあることを判断できるようになります。

### 変数の値を追加する場合は += 演算子を使うべし

{{< code lang="make" title="Makefile" >}}
hoge = aaa bbb
hoge += ccc

all:
	@echo $(hoge)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make
aaa bbb ccc
{{< /code >}}

上記のように `+=` 演算子を使用すると、現在の変数の値を追加することができます。
追加する値の前には、１つのスペースが入ります。

`+=` 演算子で値を追加しようとした場合に、その変数がまだ定義されていない場合は、`=` 演算子を使ったのと同じ振る舞いをします。

Simply expand の行われる `:=` 演算子を使い、以下のように変数の値にテキストを追加することもできます。

```make
hoge := aaa bbb
hoge := $(hoge) ccc    ※ $(hoge) → "aaa bbb ccc"
```

ただし、このような自己参照によって値を追加する方法は、Recursive expand（再帰展開）の行われる `=` 演算子と組み合わせた場合に若干問題となることがあります。
例えば、

```make
CFLAGS = $(includes) -O
CFLAGS := $(CFLAGS) -pg    ※(1)
includes = header

all:
	echo $(CFLAGS)    ※ $(CFALGS) → "-O -pg"
```

のようにすると、(1) の代入部分で `CFLAGS` の値を参照すると、`$(includes) -O` となっていますが、この時点で `CFLAGS` は Recursive expanded variable として定義されているので、まだ `$(includes)` の内容は空の状態です（Makefile を最後まで読まないと、Recursive expanded variable はちゃんと展開されない）。
にもかかわらず、`:=` 演算子を使った時点で Simply expand による展開（定義時の値決定）をしようとするため、最終的に `CFLAGS` 変数の値に `includes` 変数の値は含まれず、`-O -pg` となってしまいます。

このようなケースでは、以下のように `+=` 演算子を使用すれば、すべての代入が Recursive expand（再帰展開）されるので、意図通りの結果を得ることができます。

```make
CFLAGS = $(includes) -O
CFLAGS += -pg
includes = header

all:
	echo $(CFLAGS)    ※ $(CFALGS) → "header -O -pg"
```

### make のコマンドライン・パラメータで指定した値を上書きする

`make` のコマンドライン引数で変数値を指定すると、通常は `Makefile` 内での定義よりも優先されます。

{{< code lang="make" title="Makefile" >}}
hoge = 100
all:; echo $(hoge)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make hoge=200
200
{{< /code >}}

この優先度を変えて、`Makefile` 内の変数定義を有効にしたい場合は `override` ディレクティブを使用します。

{{< code title="Makefile" >}}
override hoge = 100
all:; echo $(hoge)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make hoge=200
100
{{< /code >}}

この優先度は、`+=` 演算子による値の追加でも同様で、コマンドライン引数で変数値が指定されていると、その変数への `+=` での追加は通常無視されます。

{{< code lang="make" title="Makefile" >}}
hoge = 100
hoge += 200
all:; echo $(hoge)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make hoge=aaa
aaa
{{< /code >}}

{{< code title="Makefile" >}}
hoge = 100
override hoge += 200
all:; echo $(hoge)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ make hoge=aaa
aaa 200
{{< /code >}}

### 変数の優先順位

変数定義の優先順位は、高い順に以下のようになっています。

1. `Makefile` 内の変数定義（`override` ディレクティブ付き）
2. コマンドライン引数で指定した変数定義
3. `Makefile` 内の変数定義（`override` ディレクティブなし）
4. 環境変数

環境変数の優先度が低くなっているおかげで、make 以外の用途で設定した環境変数によって make の振る舞いを大きく変えてしまうような危険性を軽減しています。
例えば、make 内のコマンド解釈に使用するシェルを、`Makefile` 内の `SHELL` 変数定義で安心して変更することができます。

### ターゲット内だけで有効な変数を定義する (Target-specific Variable)

```make
target ... : variable-assignment
```

上記のような構文で変数を定義すると、そのターゲット内でのみ有効な変数を定義することができます。
例えば、特定のターゲットでのみコマンドのオプションを変更したい場合などに有効です。

{{< code lang="make" title="例" >}}
prog: CFLAGS = -g
	...
{{< /code >}}

Target-specific Variable は、そのターゲットの prerequisites の構築に対しても有効です。
例えば、

```make
prog: CFLAGS = -g
prog: prog.o foo.o bar.o
```

のようにすると、`CFLAGS` 変数は `prog.o`、`foo.o`、`bar.o` ターゲットの構築においても有効です。

ターゲットにパターンを使用した場合は、特に Pattern-specific Variable と呼びます。

{{< code lang="make" title="例" >}}
%.o: CFLAGS = -O
{{< /code >}}

