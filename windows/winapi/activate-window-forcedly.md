---
title: "指定したウィンドウ (HWND) を確実にアクティブにする"
date: "2009-01-30"
---

~~~ cpp
void AcitivateWindowForcedly(HWND hWnd) {
    DWORD dwForeground = ::GetWindowThreadProcessId(::GetForegroundWindow(), NULL);
    DWORD dwTarget = ::GetCurrentThreadId();
    if (dwTarget != dwForeground) {
        ::AttachThreadInput(dwTarget, dwForeground, TRUE);
    }

    DWORD dwTime = 0;
    ::SystemParametersInfo(SPI_GETFOREGROUNDLOCKTIMEOUT, 0, &dwTime, 0);
    ::SystemParametersInfo(SPI_SETFOREGROUNDLOCKTIMEOUT, 0, (LPVOID) 0, 0);
    ::SetForegroundWindow(hwnd);
    ::SystemParametersInfo(SPI_SETFOREGROUNDLOCKTIMEOUT, 0, &dwTime, 0);

    if (dwTarget != dwForeground) {
        ::AttachThreadInput(dwTarget, dwForeground, FALSE);
    }
}
~~~

