---
title: "Windowsメモ: プロセス ID とプロセスハンドル"
url: "p/vt4pow4/"
date: "2005-02-22"
tags: ["windows"]
aliases: /windows/winapi/process-handle.html
---

プロセス ID とプロセス・ハンドル
----

**プロセス ID** (DWORD 値) は、システム内でプロセスを識別するための固有の ID を表します。

**プロセス・ハンドル**は、対象のプロセスを Windows API 経由で操作するために指定します。
プロセス・ハンドルを取得すれば、そのプロセスを操作できることになります。
プロセス・ハンドルはシステム全体で一意でなく、**取得したプロセス内でのみ有効**です。

{{% note %}}
プロセス・ハンドルは、プロセス ID とは違ってシステム全体で一意ではありません。
プロセス・ハンドルは、カーネルが**プロセスごとに管理するハンドルテーブル**へのインデックスです。
`OpenProcess()` を呼び出すと、カーネルは呼び出し元プロセスのハンドルテーブルに新しいエントリを作成し、そのインデックスをハンドル値として返します。
ハンドルテーブルはプロセスごとに独立しているため、同じ数値のハンドルが異なるプロセスでは別のリソースを指します。
{{% /note %}}


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

{{< code lang="cpp" title="使用例" >}}
HANDLE hProc = ::OpenProcess(PROCESS_ALL_ACCESS, FALSE, dwProcId);
{{< /code >}}

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
::WaitForSingleObject(hProcess, INFINITE);
```
