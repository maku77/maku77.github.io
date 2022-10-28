---
title: "adb から Backup Manager を走らせる (bmgr backup, bmgr restore)"
url: "p/x9xhyhz/"
date: "2012-01-26"
tags: ["Android"]
aliases: /android/backup-manager.html
---

Android アプリケーションで __BackupAgent__ を実装しておくと、設定値などを Google アカウントに紐づけてバックアップすることができます。
例えば、Android システム設定のバックアップは `SettingsProvider` (`com.android.providers.settings`) の `SettingsBackupAgent` クラスで実装されています。

アプリケーションの実装で、`BackupManager.dataChanged()` を読んでおくと、ダーティフラグがセットされて、Android が適切なタイミングで Google のサーバーにバックアップするのですが、`adb shell` から __`bmgr`__ コマンドを使うことで、バックアップ、リストアを任意のタイミングで実行できます。

{{< code title="設定を Google サーバへバックアップ" >}}
adb> bmgr backup com.android.providers.settings
adb> bmgr run
{{< /code >}}

{{< code title="設定を Google サーバからリストア" >}}
adb> bmgr restore com.android.providers.settings
{{< /code >}}

- 参考: [BackupAgent  |  Android Developers](https://developer.android.com/reference/android/app/backup/BackupAgent)
- 参考: [Android Backup Service を使用して Key-Value ペアをバックアップする](https://developer.android.com/guide/topics/data/keyvaluebackup)

