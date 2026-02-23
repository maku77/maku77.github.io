---
title: "Pythonメモ: Python から外部プログラムを起動する (subprocess.run)"
url: "p/gfpprmw/"
date: "2012-09-28"
tags: ["python"]
aliases: ["/python/env/call-external-program.html"]
---

## 単純に実行するだけの場合

Python で外部プログラムを起動するには `subprocess.run` を使用します（Python 3.4 以前は `subprocess.call` を使用します）。
プログラム名と引数は、下記のようにタプルやリストでひとつずつ分けて渡します。

```python
import subprocess
result = subprocess.run(('ls', '-l'))
```

`subprocess.run` の `shell` パラメータを `True` に指定すると、渡したコマンドがシェル上で実行されるようになります。
例えば、Windows の `dir` コマンドなど、シェルに組み込まれているコマンドを実行したい場合や、シェル変数の展開、glob パターンの展開といったシェルの機能を使いたい場合は、`shell` パラメータを `True` に指定して実行する必要があります。
この場合は、プログラム名と引数を分けずにひとつの文字列として指定します。

{{< code lang="python" title="例: Windows の dir コマンドを実行する" >}}
import subprocess
result = subprocess.run('dir *.txt', shell=True)
{{< /code >}}

{{< code lang="python" title="例: Linux のシェル変数の展開を行う" >}}
import subprocess
result = subprocess.run('echo $HOME', shell=True)
{{< /code >}}

また、現在のシェルで `PATH` を通しているプログラムを実行したい場合なども、上記のように `shell=True` と指定するとうまく実行できます。
外部のプログラムを実行するには、以下のように `os.system()` を使う方法もありますが、`os.system()` は非推奨 (deprecated) となっていますので、今後は `subprocess.run` の方を使用しましょう。

{{< code lang="python" title="os.system は非推奨" >}}
import os
retcode = os.system('echo Hello')
{{< /code >}}


## 外部プログラムの実行に失敗したときのエラー処理

外部プログラムの実行が成功したかどうかを調べるには、戻り値として返される `CompletedProcess` オブジェクトの `returncode` を確認します（正常に実行が完了していれば 0 が格納されています）。

```python
import subprocess

result = subprocess.run('mycommand', shell=True)
if result.returncode != 0:
    # エラー時の処理
```

あるいは、`subprocess.run` 実行時に `check` パラメータを `True` に設定しておくと、外部プログラムの実行に失敗したとき（リターンコードが 0 以外のとき）に `subprocess.CalledProcessError` 例外をスローするようになるので、これを捕捉 (`except`) するという方法もあります。

```python
import subprocess
import sys

try:
    subprocess.run('mycommand', shell=True, check=True)
except subprocess.CalledProcessError:
    print('外部プログラムの実行に失敗しました', file=sys.stderr)
```

実際にエラー処理をしっかりと記述しなければいけないようなケースでは、外部プログラムが出力するエラーメッセージを Python プログラム内でハンドルしたいかもしれません。
そのような場合は、下記の「標準出力／エラー出力を取得する」で説明しているように、stdout や stderr への出力を Python プログラム側でハンドルするように記述してください。


## 外部プログラムの標準出力／エラー出力を取得する

`subprocess.run` の実行時に、`stdout` パラメータや `stderr` パラメータを `subprocess.PIPE` に設定しておくと、外部プログラムの出力を Python コード内でハンドルできるようになります。
出力がテキストの場合は、`universal_newlines` オプションも `True` に設定しておきます（デフォルトではバイナリデータとして取得されるため）。

{{< code lang="python" title="例: Windows の dir コマンドの出力をハンドルする" >}}
import subprocess
import sys

try:
    result = subprocess.run('dir *.txt', shell=True, check=True,
            stdout=subprocess.PIPE, stderr=subprocess.PIPE, universal_newlines=True)
    for line in result.stdout.splitlines():
        print('>>> ' + line)
except subprocess.CalledProcessError:
    print('外部プログラムの実行に失敗しました', file=sys.stderr)
{{< /code >}}

以下のようなユーティリティ関数 (`command`) を作っておくと、外部コマンドの出力を一行ずつ取得するコードを簡単に記述できて便利です。

```python
import subprocess
import sys

def command(cmd):
    try:
        result = subprocess.run(cmd, shell=True, check=True,
                stdout=subprocess.PIPE, stderr=subprocess.PIPE,
                universal_newlines=True)
        for line in result.stdout.splitlines():
            yield line
    except subprocess.CalledProcessError:
        print('外部プログラムの実行に失敗しました [' + cmd + ']', file=sys.stderr)
        sys.exit(1)

# 使用例
for line in command('p4 info'):
    print('===', line)
```


## 外部プログラムの標準入力に入力する

`subprocess.Popen()` の `stdin` パラメータに `subprocess.PIPE` を指定すると、以下のように外部プロセスの標準入力へ書き込めるようになります。

{{< code lang="python" title="例: sort コマンドの標準入力に書き込む" >}}
import subprocess
from subprocess import PIPE

with subprocess.Popen('sort', shell=True, stdin=PIPE, stdout=PIPE, stderr=PIPE,
        universal_newlines=True) as pipe:

    # sort コマンドへテキスト入力して、結果を受け取る
    out, err = pipe.communicate('One\nTwo\nThree\nFour')
    for line in out.splitlines():
        print('===', line)
{{< /code >}}

{{< code title="実行結果" >}}
=== Four
=== One
=== Three
=== Two
{{< /code >}}


## 外部プログラムの出力を、別の外部プログラムへ入力する

下記のサンプルでは、`dir` コマンドの標準出力を、`sort` コマンドの標準入力へ接続しています。

```python
import subprocess
from subprocess import PIPE

dir_result = subprocess.run('dir', shell=True, check=True,
        stdout=PIPE, universal_newlines=True)
sort_result = subprocess.run('sort', shell=True, check=True,
        input=dir_result.stdout, stdout=PIPE, universal_newlines=True)
for line in sort_result.stdout.splitlines():
    print('===', line)
```


## テキストファイルの内容を外部プログラムの標準入力へ入力する

`open()` 関数で開いたファイルオブジェクトを、`subprocess.run()` の `stdin` パラメータにそのまま渡すことができます。

{{< code lang="python" title="例: input.txt ファイルの内容を sort コマンドに渡す" >}}
import subprocess

with open('input.txt', encoding='utf-8') as file:
    proc = subprocess.run('sort', shell=True, check=True,
            stdin=file, stdout=subprocess.PIPE, universal_newlines=True)
    for line in proc.stdout.splitlines():
        print('===', line)
{{< /code >}}

ただし、これをやるのであれば、`sort input.txt` を実行してしまった方が早いですね。
