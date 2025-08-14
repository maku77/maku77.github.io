---
title: "Androidベンダー向けメモ: Android デバイスのパーティション構成概要"
url: "p/ks84q3k/"
date: "2013-11-07"
tags: ["android"]
aliases: [/android/partitions.html]
---

パーティション構成
----

Android のパーティション構成は、およそ下記のようになっています。

- **`/boot`** ... Android kernel と ramdisk
- **`/system`** ... Android OS およびプリインアプリなど
- **`/recovery`** ... リカバリ時に `/boot` パーティションの代わりに起動されるリカバリ OS
- **`/data`** ... GooglePlay からインストールしたアプリやユーザデータ（書き込み可能）
- **`/cache`** ... アプリ用キャッシュ（リカバリイメージのダウンロードもここに入る）


Nexus7 (2013) の情報を見てみる
----

{{< code title="マウント情報の表示" >}}
$ mount  （あるいは cat /proc/mounts）
rootfs / rootfs ro,relatime 0 0
tmpfs /dev tmpfs rw,seclabel,nosuid,relatime,mode=755 0 0
devpts /dev/pts devpts rw,seclabel,relatime,mode=600 0 0
proc /proc proc rw,relatime 0 0
sysfs /sys sysfs rw,seclabel,relatime 0 0
selinuxfs /sys/fs/selinux selinuxfs rw,relatime 0 0
debugfs /sys/kernel/debug debugfs rw,relatime 0 0
none /acct cgroup rw,relatime,cpuacct 0 0
tmpfs /mnt/secure tmpfs rw,seclabel,relatime,mode=700 0 0
tmpfs /mnt/asec tmpfs rw,seclabel,relatime,mode=755,gid=1000 0 0
tmpfs /mnt/obb tmpfs rw,seclabel,relatime,mode=755,gid=1000 0 0
none /dev/cpuctl cgroup rw,relatime,cpu 0 0
/dev/block/platform/msm_sdcc.1/by-name/system /system ext4 ro,seclabel,relatime,data=ordered 0 0
/dev/block/platform/msm_sdcc.1/by-name/cache /cache ext4 rw,seclabel,nosuid,nodev,noatime,nomblk_io_submit,errors=panic,data=ordered 0 0
/dev/block/platform/msm_sdcc.1/by-name/userdata /data ext4 rw,seclabel,nosuid,nodev,noatime,nomblk_io_submit,errors=panic,data=ordered 0 0
/dev/block/platform/msm_sdcc.1/by-name/persist /persist ext4 rw,seclabel,nosuid,nodev,relatime,nodelalloc,data=ordered 0 0
/dev/fuse /mnt/shell/emulated fuse rw,nosuid,nodev,relatime,user_id=1023,group_id=1023,default_permissions,allow_other 0 0
{{< /code >}}

{{< code title="ファイルシステム情報の表示" >}}
$ df
Filesystem               Size     Used     Free   Blksize
/dev                   911.4M    48.0K   911.3M   4096
/mnt/secure            911.4M     0.0K   911.4M   4096
/mnt/asec              911.4M     0.0K   911.4M   4096
/mnt/obb               911.4M     0.0K   911.4M   4096
/system                827.8M   589.1M   238.7M   4096
/cache                 551.7M     9.8M   541.9M   4096
/data                   26.1G     2.7G    23.4G   4096
/persist                14.5M     4.2M    10.2M   4096
/mnt/shell/emulated     26.1G     2.7G    23.4G   4096
{{< /code >}}

