---
title: "プロセス・ハンドルについて"
date: "2005-02-22"
---

プロセス・ハンドルとプロセス ID
----

**プロセス ID** (DWORD 値) は、システム内でプロセスを識別するための固有の ID を表します。

**プロセス・ハンドル**は、対象のプロセスを Windows API 経由で操作するために指定します。プロセス・ハンドルを取得すれば、そのプロセスを操作できることになります。プロセス・ハンドルは、プロセス ID とは違ってシステム内で一意ではありません。
プロセス・ハンドルがシステム内で一意でないのは、おそらくそれがメモリ上のアドレスを示すものだからだと思われます。あるプロセスから別のプロセスを操作するために、OpenProcess() などでプロセス・ハンドルを取得すると、そのプロセスの操作用にメモリが割り当てられ、そのアドレスを保持するためのものがプロセス・ハンドルなのでしょう。だから、プロセス・ハンドルはシステム内で一意にすることはできず、そのメモリ空間、つまり、そのプロセス内だけで一意な値になるのだと思われます。


プロセス ID からプロセス・ハンドルを取得する
----

プロセス ID からプロセス・ハンドルを取得するには、`OpenProcess` 関数を使用します。

```cpp
HANDLE OpenProcess(
  DWORD dwDesiredAccess,  // アクセスフラグ
  BOOL bInheritHandle,    // ハンドルの継承オプション
  DWORD dwProcessId       // プロセス識別子
);
```

#### 使用例

```cpp
HANDLE hProc = ::OpenProcess(PROCESS_ALL_ACCESS, FALSE, dwProcId);
```

ハンドルが不要になったときは、`CloseHandle` 関数を使って閉じる必要があります。

```cpp
BOOL CloseHandle(
  HANDLE hObject   // オブジェクトのハンドル
);
```


プロセス・ハンドルからプロセス ID を取得する
----

```cpp
DWORD WINAPI GetProcessId(HANDLE Process);
```


ウィンドウ・ハンドルからプロセス ID を取得する
----

指定されたウィンドウを作成したスレッドの ID を取得します。

```cpp
DWORD GetWindowThreadProcessId(
  HWND hWnd,             // ウィンドウのハンドル
  LPDWORD lpdwProcessId  // プロセス ID
);
```


現在のプロセス ID を取得する
----

この API を呼び出したプロセスの ID を取得します。

```cpp
DWORD WINAPI GetCurrentProcessId(void);
```


プロセスが終了するまで待機する
----

あるプロセスが終了するまで待機するには、`WaitForSingleObject()` を使用します。
例えば、プロセス・ハンドルがシグナル状態になるまでずっと待機するには、下記のように実行します。

```cpp
::WaitForSingleObject(hProcess, INFINIT);
```

