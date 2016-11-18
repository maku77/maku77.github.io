---
title: Linux カーネル／OS のバージョンを調べる
created: 2010-06-01
---

Linux カーネルのバージョンを調べる
----

```
$ cat /proc/version
$ uname -a
```

Linux OS のバージョンを調べる
----

### Ubuntu の場合

```
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
```

### Debian の場合

```
$ cat /etc/debian_version
5.0
```

### CentOS（RedHat系）の場合

```
$ cat /etc/redhat-release
CentOS release 5.5 (Final)
```

### Fedora の場合

```
$ cat /etc/fedora-release
Fedora release 9 (Sulphur)
```

