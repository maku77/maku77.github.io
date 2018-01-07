---
title: 現在使用中の Windows OS のバージョンを調べる
date: "2005-08-22"
---

~~~ cpp
OSVERSIONINFO ver;

ver.dwOSVersionInfoSize = sizeof(OSVERSIONINFO);
if (!GetVersionEx(&ver)) {
    return FALSE;
}

if (ver.dwPlatformId == VER_PLATFORM_WIN32_NT) {
    // Windows NT/2000 の場合
} else if (ver.dwPlatformId == VER_PLATFORM_WIN32_WINDOWS) {
    // Windows 9x の場合
}
~~~

