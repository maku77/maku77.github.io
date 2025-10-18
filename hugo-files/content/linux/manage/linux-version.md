---
title: "Linuxメモ: Linux カーネル／OS のバージョンを調べる"
url: "p/odgqrwa/"
date: "2010-06-01"
tags: ["linux"]
aliases: /linux/linux-version.html
---

Linux カーネルのバージョンを調べる
----

```
$ cat /proc/version
$ uname -a
```

Linux OS のバージョンを調べる
----

{{< code lang="console" title="Ubuntu の場合" >}}
$ cat /etc/lsb-release
DISTRIB_ID=Ubuntu
DISTRIB_RELEASE=13.10
DISTRIB_CODENAME=saucy
DISTRIB_DESCRIPTION="Ubuntu 13.10"

$ lsb_release -a
No LSB modules are available.
Distributor ID: Ubuntu
Description:    Ubuntu 13.10
Release:        13.10
Codename:       saucy
{{< /code >}}

{{< code lang="console" title="Debian の場合" >}}
$ cat /etc/debian_version
5.0
{{< /code >}}

{{< code lang="console" title="CentOS（RedHat系）の場合" >}}
$ cat /etc/redhat-release
CentOS release 5.5 (Final)
{{< /code >}}

{{< code lang="console" title="Fedora の場合" >}}
$ cat /etc/fedora-release
Fedora release 9 (Sulphur)
{{< /code >}}

