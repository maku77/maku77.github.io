---
title: "Python でキーボードからのユーザ入力を取得する (input, getpass)"
url: "p/bfev6xf/"
date: "2009-11-18"
tags: ["Python"]
aliases: /python/io/user-input.html
---

input 関数と getpass 関数
----

Python 組み込み関数の __`input`__ 関数を使用すると、画面上にプロンプトを表示して、キーボードからの入力を促すことができます。

```python
name = input("Please input your name: ")
print("Hello,", name)
```

パスワードを入力させるときなど、入力した文字を画面上に表示したくない場合は、`input` 関数の代わりに、`getpass` モジュールの __`getpass`__ 関数を使用します。

```python
from getpass import getpass

password = getpass("Password: ")
```


使用例: 処理を継続するかの確認
----

ファイルの削除など、危険なコマンドを実行する前に `Are you sure?` 的な確認メッセージを表示するサンプルコードです。
ユーザーが `y` あるいは `Y` 以外の文字を入力すると、そこで処理を打ち切ります。

{{< code lang="python" title="confirm.py" >}}
import sys

answer = input("Are you sure? (y/n): ")
if (answer.lower() != "y"):
    sys.exit(0)

print("処理を継続します")
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ python confirm.py
Are you sure? (y/n): Y
処理を継続します
{{< /code >}}


使用例: 入力したユーザー名とパスワードのチェック
----

下記のサンプルコードでは、ユーザに「ユーザ名」と「パスワード」のペアを入力させて、それらがあらかじめ登録されたペアと一致しているかをチェックしています。

{{< code lang="python" title="password.py" >}}
from getpass import getpass

user_db = (
    ("user1", "password1"),
    ("user2", "password2"),
    ("user3", "password3"),
)

user = input("User: ")
pw = getpass("Password: ")

if (user, pw) in user_db:
    print("Access granted")
else:
    print("Access denied")
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ python password.py
User: user1
Password:（password1 と入力）
Access granted
{{< /code >}}

