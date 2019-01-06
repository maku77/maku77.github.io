---
title: "adb から Backup Manager を走らせる (bmgr)"
date: "2012-01-26"
---

各アプリケーションは **BackupAgent** を実装することで、設定値などを Google account に紐づけてバックアップすることができます。
例えば、Android システム設定のバックアップは SettingsProvider (com.android.providers.settings) の `SettingsBackupAgent` クラスで実装されています。

各アプリケーション内で、`BackupManager.dataChanged()` を読んでおくと、
ダーティフラグがセットされて、Android が適切なタイミングで Google のサーバーにバックアップするのですが、`adb shell` から `bmgr` コマンドを使うことで、バックアップ、リストアを任意のタイミングで実行できます。

#### 例: 設定値を Google サーバへバックアップ

```
adb> bmgr backup com.android.providers.settings
adb> bmgr run
```

#### 例: 設定値を Google サーバからリストア

```
adb> bmgr restore com.android.providers.settings
```

