---
title: "Administrator で Windows にログオンできるようにする"
date: "2011-02-24"
---

Windows 7 のデフォルトでは Administrator でログオンできないようになっています。
以下のステップで、Administrator でのログオンを有効にできます。

### (1) コマンドプロンプトを管理者権限で起動する

スタートメニューの中の Command Prompt を右クリックして、`Run as administrator` を選択して起動します。

### (2) Administrator でのログオンを有効にする
```
C:\> net user administrator /active:yes
The command completed successfully.
```

### (3) Administrator のパスワードを設定する（デフォルトは空）
```
C:\> net user administrator *
Type a password for the user:
Retype the password to confirm:
The command completed successfully.
```

