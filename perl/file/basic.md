---
title: "ファイルのオープン・クローズと読み書きの基本"
date: "2008-03-24"
---

ファイルのオープン
----

Perl でファイルを開くときには `open` 演算子を使用します。

### 読み取り用にオープン

~~~ perl
open LOG, 'logfile';    # read （ファイルがなければエラー）
open LOG, '<logfile';   # read （ファイルがなければエラー）
open LOG, '+<logfile';  # read & write （ファイルがなければエラー）
~~~

### 書き込み用にオープン

~~~ perl
open LOG, '>logfile';   # write （ファイルを新規作成）
open LOG, '+>logfile';  # write & read （ファイルを新規作成）
~~~

### 追加書き込み用にオープン

~~~ perl
open LOG, '>>logfile';  # append （ファイルがなければ新規作成）
~~~

ファイルを開くことにに失敗すると、`open` 演算子は偽の値を返します。
この値と、論理演算子の `or` を使って、次のように書くことがよくあります

~~~ perl
open LOG, '>>logfile'
    or die "Cannot create logfile: $!";
~~~

論理演算子の `||` は `or` よりも結合度が高いので、上記のように括弧をつけずに使用する場合は `or` を使わないといけません。


ファイルのクローズ
----

Perl プログラムが終了するときに、ファイルハンドルは自動的に `close` されます。
`close` 演算子を使って明示的にファイルハンドルを閉じることもできます。

~~~ perl
close LOG;
~~~

すでに `open` されているファイルハンドルを再び開こうとすると、いったん `close` されてから `open` されるという動作になります。


テキストファイルの読み書き
----

ファイルの内容は、標準入力 `<STDIN>` を読み込むのと同様に読み込むことができます。
以下のサンプルは、ファイルの内容に行番号を付けて表示します。

~~~ perl
open FILE, 'data.txt' or die $!;

my $count = 0;
while (<FILE>) {
    chomp;
    ++$count;
    printf "%04d: %s\n", $count, $_;
}

close FILE;
~~~

ファイルへ書き込みを行う時は、`print` や `printf` 関数にファイルハンドルを指定します。
ファイルハンドルと、書き込むテキストの間にカンマを入れてはいけません。

~~~ perl
open FILE, '>data.txt' or die $!;

print FILE "Hello!\n";
print FILE "I am Bob.\n";

close FILE;
~~~


IO::File モジュールを使用する方法
----

`IO::File` オブジェクトは、`open` 演算子でオープンする通常のファイルハンドルと同じように使用することができます。
オブジェクト指向っぽくコーディングしたいときはこちらを使用するとよいでしょう。

#### 例: IO::File オブジェクトによるファイル書き込み

~~~ perl
use IO::File;

my $fh = IO::File->new(">output.txt") or dir "Cannot create file: $!";
print $fh "$_\n" for 1..10;
$fh->close;
~~~

#### 例: IO::File オブジェクトによるファイル読み込み

~~~ perl
use IO::File;

my $fh = IO::File->new("/etc/passwd") or die "Cannot open file: $!";
while (<$fh>) {
    print "--- $fh";
}
$fh->close;
~~~

`$fh` オブジェクトが破棄されるときに自動的に `close` 処理が行われるので、上記のような単純なスクリプトであれば `$fh->close` は省略することができます。
`IO::File` オブジェクトだけを作成しておき、実際のファイルのオープンを後回しにすることもできます。

~~~ perl
use IO::File;

# IO::File オブジェクトを作成するだけ
my $fh = IO::File->new;

# ここで実際にオープン
open $fh "sample.dat" or die "Cannot open file: $!";

# 任意の時点で open されているかどうか確認できる
if ($fh->opened) {
    # ...
}

close $fh;
~~~

