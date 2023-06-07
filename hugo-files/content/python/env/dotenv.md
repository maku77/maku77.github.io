---
title: ".env ファイルで環境変数を設定する (python-dotenv)"
date: "2020-05-27"
url: "p/gzo8d7y/"
tags: ["Python"]
aliases: /python/env/dotenv.html
---

python-dotenv モジュールの概要
----

[python-dotenv モジュール](https://pypi.org/project/python-dotenv/) を使用すると、Python プログラム実行時に次のような __`.env`__ ファイルを読み込んで、環境変数を設定することができます。

{{< code lang="ini" title=".env" >}}
MYAPP_USER=maku
MYAPP_PASS=makupass
MYAPP_CONFIG=${HOME}/${MYAPP_USER}/config
{{< /code >}}

上記の例のように、`${HOME}` という形で環境変数の値を展開することもできます。

特定のプログラムを実行するために、いちいち OS の環境変数を設定するのを嫌がるユーザーはたくさんいます。
あなたのプログラムが何らかの環境変数を必要としているのであれば、`.env` ファイルによる設定手段も提供しておくと親切です。
`.env` の仕組みは Python に限ったものではなく、いろいろな言語で採用されています。

- 参考: [Node.js プログラムで .env ファイルを使用する](/nodejs/env/dotenv.html)


python-dotenv モジュールのインストール
----

`python-dotenv` モジュールは `pip` コマンドでインストールできます。

```console
$ python3 -m pip install python-dotenv
```

システム全体の Python 実行環境を汚したくない場合は、次のように [仮想環境を作成してインストール](/p/wozpogm/) しましょう。

```
$ python3 -m venv ~/venv
$ source ~/venv/bin/activate
(venv) $ python3 -m pip install python-dotenv
```

仮想環境は次のように抜けられます。

```
(venv) $ deactivate
```


python-dotenv の使い方
----

### .env ファイルの内容を環境変数に反映する

`.env` ファイルを読み込むのはとても簡単で、__`load_dotenv()`__ を呼び出すだけです。
次の Python スクリプトでは、同じディレクトリ（あるいは、より上位のディレクトリ）に置かれた `.env` ファイルの内容を読み込み、環境変数に反映しています。

{{< code lang="python" title="config.py" >}}
# .env ファイルをロードして環境変数へ反映
from dotenv import load_dotenv
load_dotenv()

# 環境変数を参照
import os
MYAPP_USER = os.getenv("MYAPP_USER")
MYAPP_PASS = os.getenv("MYAPP_PASS")
{{< /code >}}

仮に、`.env` ファイルが存在しなくても、`load_dotenv()` はエラーにならないので、上記のようなコードは安心して実行することができます。
ちなみに、上記の `config.py` モジュールは、次のような感じで使用します。

{{< code lang="python" title="main.py" >}}
import config

print(config.MYAPP_USER)
print(config.MYAPP_PASS)
{{< /code >}}

### 環境変数の値を上書きする (override)

OS の環境変数設定で、すでに同じ名前の変数が定義されている場合は、そちらが優先して使われるようになっています。
`.env` ファイルで設定した値を優先して使いたい場合は、次のように __`override`__ オプションを指定します。

```python
from dotenv import load_dotenv
load_dotenv(override=True)
```


.env ファイルの扱い方
----

### .env ファイルはどこに置くべきか？

ある Python スクリプトから `dotenv.load_dotenv()` 関数を呼び出すと、そのスクリプトファイルがあるディレクトリを起点に、`.env` ファイルが見つかるまで親ディレクトリを遡りながら検索します。
なので、あるプロジェクトで使用する __`.env` ファイルは、そのプロジェクトのルートディレクトリに配置__ しておけば、まずロードに失敗することはありません。

### .env ファイルは Git にコミットする？

`.env` ファイルは、ユーザー固有の設定を記述するものです。
例えば、Web サービスのアクセストークンなどを記述するので、 __`.env` ファイルは Git にコミットしてはいけません__。
間違えてコミットしないように、`.gitignore` ファイルに `.env` のエントリを登録しておきましょう。
Git にコミットしない `.env` ファイルであることを明確にするために、`.env.local` というファイル名を使うこともあります。

