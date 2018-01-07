---
title: "MPLAB によるプロジェクト作成 ─ PIC めもめも"
date: "2002-08-01"
---

MPLAB の設定（デベロップメント・モードの設定）
----

MPLAB を起動し、次のように設定します。

1. 「Options」 → 「Development Mode」 → 「Tools」タブ
2. "Development Mode" で MPLAB-SIM Simulator にチェック
3. "Processor" を使用するデバイスに変更


新規プロジェクトの作成
----


1. 「Project」 → 「New Project」
2. "File Name" にプロジェクト名 (例えば "test.prj") を入力
3. "Directories" には空白を含まないパス (例えば "D:\PIC_ASM\test") を選択して「OK」

「OK」 ボタンを押すと、Edit Project ダイアログが開きます。


新規ソースの追加（Edit Project ダイアログ）
----

1. "Development Mode" が MPLAB-SIM、正しいデバイス名になっているか確認
2. "Project Files" の 「Add Node」 ボタンをクリック
3. ASM ファイル名 (例えば "test.asm") を入力して 「OK」
4. "Project Files" の "test [.hex]" を選択して 「Node Properties...」 ボタンをクリック
    1. Hex Format ･･･ INHX8M
    2. Warning level ･･･ all
    3. Case sensitivity … OFF (大文字と小文字を区別しない)
    4. Tab size ･･･ 4（タブ使わないかもしれないけどいちおう）
5. 「OK」

**この作業では、実際に "test.asm" は作成されない**ので、エクプローラで新規作成するか、「File」 → 「New」 で新規作成して、Ctrl-S で "test.asm" として保存してください。


プログラムの作成
----

1. 「Window」 → 「Project」
2. リストから開きたいソースファイル（例えば "test.asm"）をダブルクリック

エディタが開くので、コードを自由に記述してください。
ただし、MPLAB 付属のエディタは日本語が入力できないので、日本語のコメントを記述したい場合は、別の日本語対応のエディタを使ってください。
動作をテストしたい場合は、とりあえず次のように記述してみてください（END の前に空白を入れるのを忘れずに）。

#### test.asm（これだけでもビルドは可能）

~~~
    END
~~~


ビルド
----

1. 「Project」 → 「Make Project」

"Build completed successfully." と出ればビルド成功です。
エラーが出た場合は、そのエラー表示行をダブルクリックすれば、該当行に移動できます。
Bank0 以外のデータ RAM にアクセスすると、"Message" という表示が出ることがありますが、エラーではないのでビルドは成功しています。

ビルドに成功すると、HEX ファイルがそのプロジェクトのディレクトリに作成されます。
HEX ファイルができたら、次は MPLAB-SIM を使ってデバッグするか、PIC ライタで実際に PIC に書き込んで実機テストに入ります。

