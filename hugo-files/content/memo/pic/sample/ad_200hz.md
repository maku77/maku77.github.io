---
title: "３軸加速度センサの入力を 200Hz でサンプリング＆シリアル通信するサンプルプログラム ─ PIC めもめも"
url: "p/w6pogd4/"
date: "2002-08-01"
tags: ["pic"]
aliases: ["/memo/pic/sample/ad_200hz.html"]
---


３軸加速度センサの入力を 200Hz で A/D 変換し、シリアルポートから 9600bps で 1byte ずつ送信（5msec ごとに 0x00, Ax, Ay, Az の計 4 byte を送信）するサンプルコードです。
最初にデリミタのための 0x00 を送信するようにしているので、0x00, Ax, Ay, Az の 4 byte が 5msec ごとにまとめて送信されます。

```asm
;==============================================================================
;
; File  : ad_200hz.asm
; Author: Masatoshi OHTA
; Update: 2002/11/18
;
;   デバイス      : PIC16F873
;   通信スピード  : 9600bps
;   データ長      : 8bit
;   ストップビット: 1bit
;   フロー制御    : なし
;
;==============================================================================
    LIST     P=PIC16F873
    INCLUDE  "P16F873.INC"
    __CONFIG _HS_OSC & _WDT_OFF
             ;ウォッチドッグ・タイマ OFF

;==============================================================================
; 変数定義
;==============================================================================
    CBLOCK   H'20'
    w_temp         ; variable used for context saving
    status_temp    ; variable used for context saving
    temp           ;
    wait_count     ; 遅延サブルーチン用の変数
    ENDC

;==============================================================================
; エントリ・ポイント
;==============================================================================
    ORG      0
    goto     Start
    ORG      4
    goto     Interrupt

;==============================================================================
; メインルーチン
;==============================================================================
Start
    bsf     STATUS, RP0         ; Bank1 に切り替え

;[TRISA] 入出力ポートの設定
    movlw   B'00000111'         ; RA0, RA1, RA2 は入力(センサから)
    movwf   TRISA               ;
;[ADCON1] アナログ入力にするポート、左詰右詰を決定
    movlw   B'00000010'         ; 左詰、AN0～AN4 全部アナログ入力
    movwf   ADCON1
;[TRISC] USART 用入出力ポートの設定
    bcf     TRISC, 6            ; PortC<6> (TX) is an output
;    bsf     TRISC, 7            ; PortC<7> (RX) is an input
;[TXSTA] USART 送信ステータスの設定
    movlw   B'00100000'         ; Async mode
    movwf   TXSTA               ;
;[SPBRG]USART ボーレートの設定
    movlw   0FH                 ;9600bps 10MHz BRGH=0(低速)
    movwf   SPBRG

    bcf     STATUS, RP0         ; Bank0 に戻す

;[RCSTA] USART 受信ステータスの設定
    movlw   B'10010000'         ;
    movwf   RCSTA               ;
;[ADCON0] AD 変換クロックを指定。AD 変換を有効に
    movlw   B'10000001'         ; Fosc/32, CH0, ADON=1
    movwf   ADCON0

;CCP1 のコンペアモード（SpecialEventTrigger）による割り込みの設定
;SetupTimer1
    movlw   B'00000001'
    movwf   T1CON
;SetupCCP1
    movlw   B'00001011'
    movwf   CCP1CON
;SetCCP1Counter
    movlw   H'30'       ;  0x61A8 = 25000 Count (10msec - 100Hz)
    movwf   CCPR1H      ;  0x30D4 = 12500 Count ( 5msec - 200Hz)
    movlw   H'D4'
    movwf   CCPR1L
;割り込み許可
    bsf     STATUS, RP0   ;Go to Bank1
    bsf     PIE1, CCP1IE  ;CCP1 割り込みイネーブル
    bcf     STATUS, RP0   ;Back to Bank0
    bsf     INTCON, PEIE  ;周辺機能割り込みイネーブル (CCP1は周辺機能)
    bsf     INTCON, GIE   ;全体の割り込みイネーブル

;******************************************************************************
; Idle Loop
;******************************************************************************
MainLoop
    ;Nothing to do.
    goto    MainLoop

;******************************************************************************
; 割り込み時の処理
;   フラグクリア
;   0 を送信
;   (アナログ入力ポート選択 → シリアル送信) x 3
;******************************************************************************
InterruptProcedure
;CCP1 割り込みフラグをクリア
    bcf     PIR1, CCP1IF
;デリミタとして 0x00 を送信する
    call    SendZero
;アナログ入力を RA0 に切り替え
    movf    ADCON0, W
    andlw   B'11000111'
    iorlw   B'00000000'
    movwf   ADCON0
    call    AdConvertAndSend
;アナログ入力を RA1 に切り替え
    movf    ADCON0, W
    andlw   B'11000111'
    iorlw   B'00001000'
    movwf   ADCON0
    call    AdConvertAndSend
;アナログ入力を RA2 に切り替え
    movf    ADCON0, W         ;ADCON0 -> W
    andlw   B'11000111'       ;W & 11000111 -> W
    iorlw   B'00010000'       ;W | 00010000 -> W
    movwf   ADCON0            ;W -> ADCON0
    call    AdConvertAndSend
    return

;******************************************************************************
; セレクトしたポートに対して A/D 変換を実行
; チャンネルをセレクトしてからCholdに電圧を蓄積するまで待機する必要がある
;    8bit 変換の時 … 12μsec
;   10bit 変換の時 … 20μsec
;******************************************************************************
AdConvertAndSend
    call    Wait20_10MHz        ;コンデンサの充電まち (20μsec, 10MHz)
    bsf     ADCON0, GO          ;A/D 変換スタート
WaitAdFinish
    btfsc   ADCON0, GO          ;GO ビットがクリアされたら A/D 変換終了
    goto    WaitAdFinish
    movf    ADRESH, W           ;A/D 変換の結果を取得 (上位8bit)
;A/D 変換結果をシリアルで送信
    call    SendUsart
    return

;******************************************************************************
; シリアル送信サブルーチン
; TXSTA<TRMT(1)> を監視し、W レジスタのデータ (8 bit) をシリアルで送信します。
; （TEMP を Bank0 の汎用レジスタに指定してあることを前提とします。
; 　このルーチンを呼び出す時は Bank0 に切り替えてから呼び出すこと）
;******************************************************************************
SendUsart
    movwf   temp                ;W レジスタを保存
    bsf     STATUS, RP0         ;Bank1 に切り替え
SendUsartLoop
    btfss   TXSTA, TRMT         ;TXSTA<TRMT(1)>  が 1 になるまで待機
    goto    SendUsartLoop
    bcf     STATUS, RP0         ;Bank0 に戻す
    movf    temp, W             ;W レジスタの復旧
    movwf   TXREG               ;TXREG レジスタにセット (実際の送信開始)
    return

;******************************************************************************
; Send 0x00 By RS232C
;******************************************************************************
SendZero
    clrw
    call    SendUsart
    return

;******************************************************************************
; 20μsec の遅延発生ルーチン (クロック = 10MHz)
;******************************************************************************
Wait20_10MHz
    movlw    D'14'
    movwf    wait_count
Wait20_10MHz_Loop
    decfsz   wait_count, F
    goto     Wait20_10MHz_Loop
    nop
    nop
    return

;******************************************************************************
; 割り込みルーチン
;******************************************************************************
Interrupt
    movwf    w_temp
    movf     STATUS, w
    bcf      STATUS, RP0
    movwf    status_temp

    call     InterruptProcedure

    bcf      STATUS, RP0
    movf     status_temp, w
    movwf    STATUS
    swapf    w_temp, f
    swapf    w_temp, w
    retfie

;==============================================================================
; Directive 'End of Program'
;==============================================================================
    END
``` 

