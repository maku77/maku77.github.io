---
title: "Python の argparse モジュールでコマンライン引数を扱う"
url: "p/o6q8p6m/"
date: "2023-06-01"
tags: ["Python"]
---

Python が標準ライブラリとして提供している [argparse モジュール](https://docs.python.org/ja/3/library/argparse.html) を使用すると、複雑なコマンドライン引数を扱うスクリプトをシンプルに実装することができます。
単純に `sys.argv` を使ってコマンドライン引数を扱う方法と比べ、`argparse` を使うと次のような利点があります。

- Linux スタイルの短いオプション (`-o`) や長いオプション (`--output`) を簡単に定義できます
- ユーザーが入力した値を直感的なプロパティとして参照できます（例: `args.output`）。
- ヘルプメッセージを自動で構築してくれます（`-h (--help)` オプションはデフォルトで生成してくれます）
- 各オプションの型を定義できます（数値型オプションに文字列が指定されたときに自動でエラーにしてくれます）
- [サブコマンド](https://docs.python.org/ja/3/library/argparse.html#sub-commands)を簡単に実装できます（例: `sample.py <サブコマンド> <引数1> <引数2>`）


ArgumentParser の基本
----

`argparse` モジュールは、次のような流れで使用します。

1. __`ArgumentParser`__ クラスのインスタンスを生成する
2. __`add_argument`__ メソッドでコマンドライン引数の定義を追加していく
3. __`parse_args`__ メソッドでパースして `Namespace` オブジェクトを取得する
4. `Namespace` オジェクトのプロパティでユーザー入力を参照する

次のスクリプトでは、`-i (--input)` オプションと、`-o (--output)` オプションを定義しています。

{{< code lang="python" title="main.py" >}}
import argparse

# コマンドライン引数の仕様を定義してユーザー入力をパースする
parser = argparse.ArgumentParser()
parser.add_argument("-i", "--input", help="input file")
parser.add_argument("-o", "--output", help="output file", default="out.txt")
args = parser.parse_args()  # => argparse.Namespace

# ユーザーが入力した値を表示する
print(f"Input file: {args.input}")
print(f"Output file: {args.output}")
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ python main.py
Input file: None
Output file: out.txt

$ python main.py -i input.txt -o output.txt
Input file: input.txt
Output file: output.txt
{{< /code >}}

ヘルプ表示用のオプション `-h (--help)` は自動で生成してくれます。

{{< code lang="console" title="自動生成されたヘルプ" >}}
$ python main.py -h
usage: main.py [-h] [-i INPUT] [-o OUTPUT]

options:
  -h, --help            show this help message and exit
  -i INPUT, --input INPUT
                        input file
  -o OUTPUT, --output OUTPUT
                        output file
{{< /code >}}

ユーザーが間違った使い方をしたときは、Usage を表示して終了してくれます。

```console
$ python main.py hoge
usage: main.py [-h] [-i INPUT] [-o OUTPUT]
main.py: error: unrecognized arguments: hoge

$ python main.py -i
usage: main.py [-h] [-i INPUT] [-o OUTPUT]
main.py: error: argument -i/--input: expected one argument
```


いろいろなコマンドライン引数を定義する
----

`ArgumentParser#add_argument` メソッドの呼び出し方によって、様々な仕様のコマンドライン引数を定義することができます。

### 位置引数 (positional argument)

```python
parser.add_argument("name", type=str, help="your name")
parser.add_argument("age", type=int, help="your age")
# ...
print(f"name={args.name}, age={args.age}")
```

```console
$ python main.py Maku 14
name=Maku, age=14
```

順序通り指定しなければいけない単純なコマンドライン引数です。
引数を `-` や `--` で始まらない名前にすると、この位置引数 (positional argument) として扱われます。
位置引数はデフォルトでユーザーによる入力が必須となりますが、__`nargs="?"`__ 指定によりオプショナル扱いとなり、さらに __`default=...`__ 指定によりデフォルト値を設定できます。
デフォルト値は、`help` パラメーターの中で、__`%(default)s`__ で参照できます。

```python
parser.add_argument(
    "output", nargs="?", default="out.txt",
    help="output file (default: %(default)s)"
)
```

### 必須の引数 (required=True)

```python
parser.add_argument("-i", "--input", help="input file", required=True)
# ...
print(f"Input file: {args.input}")
```

`-` や `--` で始まる名前の引数は、デフォルトで省略可能なオプショナル扱いとなりますが、__`required=True`__ フラグをつけると、ユーザーによる指定が必須になります。
上記の例であれば、ユーザーは `-i (--input)` オプションを必ず指定する必要があり、`args.input` には必ず値が入っていることが保証されます。
この `required` よりも、次の `default` を使えないかを先に検討してください。

### デフォルト値 (default=...)

```python
parser.add_argument(
    "-c", "--config", default="app.toml", help="config file (default: %(default)s)"
)
# ...
print(f"Config file: {args.config}")
```

__`default=...`__ パラメーターを指定すると、ユーザーがそのオプションを指定しなかったときのデフォルト値を設定できます。
上記の例であれば、ユーザーが `-c (--config)` オプションを省略した場合は `app.toml` を指定したものとみなされ、`args.config` には必ず値が入っていることが保証されます。
デフォルト値は、`help` パラメーターの中で、__`%(default)s`__ で参照できます。
ユーザーの引数指定を必須にする `required=True` よりも、まずはこの `default` パラメーターを使うことを検討すべきです。

### on/off フラグ (action="store_true")

```python
parser.add_argument(
    "-v", "--verbose", action="store_true", help="increase output verbosity"
)
# ...
print(args.verbose)
```

```console
$ python main.py
False

$ python main.py --verbose
True
```

__`action="store_true"`__ の指定により、`True` あるいは `False` の 2 値を表現するオプションを定義できます。
ユーザーが `-v (--verbose)` オプションを指定したときに、`args.verbose` の値が `True` になります。
`action="store_true"` の代わりに __`action="store_false"`__ とすると、`True` と `False` の扱いが反転します（`-v` オプションを指定したときに `args.verbose` が `False` になります）。

### 選択肢 (choices=[...])

```python
parser.add_argument(
    "-t", "--type", choices=["json", "yaml", "toml"], default="json",
    help="file format (default %(default)s)"
)
# ...
print(args.type)
```

```console
$ python main.py
json

$ python main.py -t yaml
yaml

$ python main.py -t hoge
usage: main.py [-h] [-t {json,yaml,toml}]
main.py: error: argument -t/--type: invalid choice: 'hoge' (choose from 'json', 'yaml', 'toml')
```

__`choices=[...]`__ で選択肢を列挙することによって、ユーザーは制約された選択肢の中からのみ値を指定することができるようになります。
ユーザーが何も入力しなかったときの値は `None` となりますが、__`default="json"`__ のようにデフォルト値を設定しておくこともできます。

上記の例では文字列の選択肢を定義していますが、数値の選択肢も同様に定義できます。

```python
parser.add_argument("--level", type=int, choices=[1, 2, 3, 4, 5])
parser.add_argument("--level", type=int, choices=range(1, 6)) # 同上
```

### 同一オプションが何回指定されたかを取得する (action="count")

```python
parser.add_argument("-v", action="count", default=0, help="increase output verbosity")
# ...
print(args.v)
```

```console
$ python main.py
0

$ python main.py -v
1

$ python main.py -vvv
3
```

__`action="count"`__ とすると、そのオプション自体がユーザーによって何度指定されたかをカウントしてくれます。
冗長レベルを制御するための `-v`、`-vv`、`-vvv` といったオプションはたまに見かけますね。

### 排他的にしか指定できないオプション (add_mutually_exclusive_group)

```python
group = parser.add_mutually_exclusive_group()
group.add_argument("-v", "--verbose", action="store_true")
group.add_argument("-q", "--quiet", action="store_true")
```

```console
% python main.py -v -q
usage: main.py [-h] [-v | -q]
main.py: error: argument -q/--quiet: not allowed with argument -v/--verbose
```

同時に指定すると意味的に矛盾が生じるようなオプション項目は、排他グループを作成してそこに登録しておきます。
上記の例では、`-v (--verbose)` オプションと `-q (--quiet)` オプションは同じ排他グループに属しているため、どちらかしか指定できません。

### ヘルプ表示のラベル名を変更する (metavar)

```python
parser.add_argument("input", metavar="INPUT_FILE", help="input file path")
parser.add_argument("output", metavar="OUTPUT_FILE", help="output file path")
```

```console
$ python main.py -h
usage: main.py [-h] INPUT_FILE OUTPUT_FILE
...
```

__`matavar`__ パラメーターで、ヘルプに表示されるプレースホルダー名を変更できます。

### 参照時のプロパティ名を変更する (dest)

```python
parser.add_argument(
    "-v", dest="verbose", action="count", default=0, help="increase output verbosity"
)
# ...
print(args.verbose)
```

通常、定義したオプション名はそのまま `Namespace` オブジェクトのプロパティ名となりますが、__`dest`__ パラメーターでプロパティ名を変更できます。
上記の例では、`-v` オプションを定義しているので、通常は `args.v` で参照するところを、`args.verbose` で参照できるようにしています。


アプリケーションの説明文
----

`ArgumentParser` コンストラクタのパラメーターで、ヘルプメッセージで表示する詳細説明を追加できます。

{{< code lang="python" title="main.py" hl_lines="4 5" >}}
import argparse

parser = argparse.ArgumentParser(
    description="ヘルプの最初に表示されるプログラムの説明",
    epilog="ヘルプの最後に表示されるプログラムの説明",
)
args = parser.parse_args()
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ python main.py --help
usage: main.py [-h]

ヘルプの最初に表示されるプログラムの説明

options:
  -h, --help  show this help message and exit

ヘルプの最後に表示されるプログラムの説明
{{< /code >}}

長いメッセージは、ターミナルの横幅に応じて適切な位置で改行して表示してくれるので、Python コード上ではどこで改行するかを気にせずに記述できます（ただし、単語の区切りがない日本語はうまくいきません）。

```python
parser = argparse.ArgumentParser(
    description="""
        Long long long long long long long
        long long long long long long long
        long long long long long description
        """
)
```

整形済みのテキストとして、Python コード上での改行位置を保って出力するには、__`formatter_class`__ として `RawDescriptionHelpFormatter` を指定します。

```python
parser = argparse.ArgumentParser(
    formatter_class=argparse.RawDescriptionHelpFormatter,
    description=textwrap.dedent(
        """
        Please do not mess up this text!
        --------------------------------
            I have indented it
            exactly the way
            I want it
        """
    ),
)
```


（おまけ）コマンドライン引数を扱うモジュールを切り出す
----

多くの機能を含む Python スクリプトは、コマンドライン引数の定義も複雑になりがちです。
`argparse` まわりの処理をモジュールと切り出しておくと、メインスクリプトのコードをシンプルに保つことができます。

{{< code lang="python" title="args.py（コマンドライン引数を処理するモジュール）" >}}
import argparse

def get_args() -> argparse.Namespace:
    """コマンドライン引数の内容をパースして取得します。"""

    # スクリプトの説明を定義
    parser = argparse.ArgumentParser(
        description="このスクリプトの説明をここに書く",
        epilog="このスクリプトの補足説明をここに書く",
    )

    # スクリプトの引数を定義
    parser.add_argument("input", help="input file")
    parser.add_argument("-o", "--output", help="output file", default="out.txt")

    return parser.parse_args()
{{< /code >}}

上記のように切り出したモジュールは、メインスクリプトから次のように使用できます。

{{< code lang="python" title="main.py" >}}
import args

if __name__ == "__main__":
    args = args.get_args()
    print(args.input)
    print(args.output)
{{< /code >}}

すっきり ٩(๑❛ᴗ❛๑)۶

