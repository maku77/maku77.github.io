---
title: Windows のユーザのパスワードの有効期限を無期限に設定する
date: "2013-03-29"
---

`cmd.exe` を「管理者として実行」して、以下のように入力することで、Windows ユーザのパスワードの有効期限を無期限にすることができます。

```
C:\> net accounts /maxpwage:unlimited
```

ちなみに、現在のパスワードの有効期限がどのように設定されているかは以下のように確認できます。

```
C:\> net accounts
```

あるいは、

```
C:\> net user <UserName>
```

