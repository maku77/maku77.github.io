---
title: MetaTrader のターミナル情報を取得する
created: 2014-12-06
---

MetaTrader（ターミナル）の情報を取得するには、`TerminalInfoString()` や `TerminalInfoInteger()` を使用します。
得られる結果が文字列の場合は、`TerminalInfoString()` を使用し、整数が得られる場合は、`TerminalInfoInteger()` を使用します。

* [TerminalInfoString](http://docs.mql4.com/check/terminalinfostring)
* [TerminalInfoInteger](http://docs.mql4.com/check/terminalinfointeger)

取得したい内容に応じてパラメータを指定しますが、ここに指定できる定数値は、`ENUM_TERMINAL_INFO_INTEGER` や `ENUM_TERMINAL_INFO_INTEGER` に定義されています。

* [Client Terminal Properties](http://docs.mql4.com/constants/environment_state/terminalstatus)

#### 使用例
```mql
Print("Language=" + TerminalInfoString(TERMINAL_LANGUAGE));
Print("Company=" + TerminalInfoString(TERMINAL_COMPANY));
Print("Name=" + TerminalInfoString(TERMINAL_NAME));
Print("Path=" + TerminalInfoString(TERMINAL_PATH));
Print("DataPath=" + TerminalInfoString(TERMINAL_DATA_PATH));
Print("CommonDataPath=" + TerminalInfoString(TERMINAL_COMMONDATA_PATH));
```

#### 実行結果
```mql
Language=English
Company=Maku Maku company
Name=MT4
Path=C:\Program Files\MT4
DataPath=C:\Program Files\MT4
CommonDataPath=C:\users\maku\Application Data\MetaQuotes\Terminal\Common
```

