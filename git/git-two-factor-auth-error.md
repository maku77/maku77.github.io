---
title: "二要素認証を設定した後に git コマンドが Authentication failed になる場合"
date: "2015-06-24"
---

二要素認証を設定した後に、従来通りに git clone や git push コマンドを実行しようとすると、下記のように Authentication failed になってしまいます。

```
C:\> git clone https://github.com/xxx/yyy.git
Cloning into 'yyy'...
Username for 'https://github.com': ojisancancode
Password for 'https://ojisancancode@github.com': *****
fatal: Authentication failed
```

この認証エラーが出た場合は、Personal settings の [Personal access tokens のページ](https://github.com/settings/tokens) から生成できる 40 桁のアクセストークンを、上記のパスワードとして入力すれば認証に成功します。

毎回アクセストークンを入力するのが面倒な場合は、`$HOME/.netrc`（Windows の場合は `%HOME%/_netrc`）に下記のように記述しておくと、ユーザ名とアクセストークンの入力を省略できます。

#### %HOME%/_netrc

```
machine github.com
login ojisancancode
password e3397d96801a11c6b6a3663d1dc719867836aa56
```

