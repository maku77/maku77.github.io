---
title: Windows API ですべてのウィンドウを列挙し、特定の条件にマッチするウィンドウを取得する
created: 2005-08-22
---

`EnumWindows` API を使用すると、すべてのウィンドウを順番に処理することができます。
各ウィンドウの `HWND` がコールバック関数に渡されて呼び出されるので、特定の条件にマッチするウィンドウを検索することができます。

次の例では、ウィンドウクラス名でウィンドウを検索しています。

~~~ cpp
// 列挙に使用するコールバック関数
BOOL CALLBACK EnumWindowsProc(HWND hWnd, LPARAM lParam) {
    const char* search = (const char*) lParam;  // 渡された "EDIT"
    char className[MAX_PATH];

    ::GetClassName(hWnd, className, MAX_PATH);
    if (stricmp(className, search) == 0) {
        // 見つかった！
        return FALSE;
    }

    return TRUE;  // To continue enumerating windows
}

int main() {
    // ウィンドウクラス名が "EDIT" であるものを検索
    ::EnumWindows(EnumWindowsProc, (LPARAM) "EDIT");
    return 0;
}
~~~

