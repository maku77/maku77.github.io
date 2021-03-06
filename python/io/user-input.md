---
title: "キーボードからのユーザ入力を取得する (input, getpass)"
date: "2009-11-18"
---

input 関数と getpass 関数
----
組み込み関数の `input()` を使用すると、画面上にプロンプトを表示して、キーボードからの入力を促すことができます。

```python
name = input('Please input your name: ')
print('Hello', name)
```

パスワードを入力させるときなど、入力した文字を画面上に表示したくない場合は、`getpass` モジュールが提供している `getpass` 関数を使用できます。

```python
from getpass import getpass
password = getpass('Password: ')
```


サンプルコード: 処理を継続するかの確認
----

ファイルの削除など、危険なコマンドを実行する前に `Are you sure?` 的な確認メッセージを表示するサンプルコードです。
ユーザーが `y` あるいは `Y` 以外の文字を入力すると、そこで処理を打ち切ります。

#### danger.py

```python
import sys

answer = input('Are you sure? (y/n): ')
if (answer != 'y' and answer != 'Y'):
    sys.exit(1)

print('処理を継続します')
```

#### 実行例

```
$ python danger.py
Are you sure? (y/n): Y
処理を継続します
```


サンプルコード: 入力したユーザー名とパスワードのチェック
----

下記のサンプルコードでは、ユーザに「ユーザ名」と「パスワード」のペアを入力させて、それらがあらかじめ登録されたペアと一致しているかをチェックしています。

#### password.py

```python
from getpass import getpass

user_db = (
    ('user1', 'password1'),
    ('user2', 'password2'),
    ('user3', 'password3'),
)

user = input('User: ')
pw = getpass('Password: ')

if (user, pw) in user_db:
    print("Access granted")
else:
    print("Access denied")
```

#### 実行例

```
$ python password.py
User: user1
Password:（password1 と入力）
Access granted
```

