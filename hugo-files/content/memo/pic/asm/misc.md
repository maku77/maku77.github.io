---
title: "汎用テクニック ─ PIC めもめも"
url: "p/p83uim3/"
date: "2002-08-01"
tags: ["pic"]
aliases: ["/memo/pic/asm/misc.html"]
---

どの Page にあるか分からないサブルーチンにジャンプする
----

```asm
;
; どのページにあるか分からないサブルーチンにジャンプする
;

    ...
    MOVLW   HIGH(SUB)  ; SUB の上位アドレス
    MOVWF   PCLATH     ; を PCLATH にセット
    CALL    SUB
    ...

SUB
    ...
    RETURN
```


特定のマスクで 2 つの値を比較
----

```asm
;
; 特定のマスクで比較 (A と B を 01110000 のビットマスクで比較)
;

    MOVF    A, W
    XORWF   B, W
    ANDLW   B'01110000'
    BTFSC   STATUS, Z
    GOTO    AB_IS_SAME          ; 同じだった (→ AB_IS_SAME へ)
    GOTO    AB_IS_NOT_SAME      ; 違った     (→ AB_IS_NOT_SAME へ)

AB_IS_SAME
    ...
    GOTO xxx

AB_IS_NOT_SAME
    ...
    GOTO xxx
```


W レジスタの値 (0～F) を 7 セグメント LED 用の値に変換する
----

```asm
;==============================================================================
; W の値を 7 セグメント LED 用の値に変換します。
;
; 使い方:
;  (W に 0～9 の値が入っている時に)
;  CALL TO_7SEG
;==============================================================================
TO_7SEG
    ADDWF   PCL, F              ; プログラムカウンタに W の値を加える
    RETLW   B'01111110'         ; 0 の表示用
    RETLW   B'00001100'         ; 1 の表示用
    RETLW   B'10110110'         ; 2 の表示用
    RETLW   B'10011110'         ; 3 の表示用
    RETLW   B'11001100'         ; 4 の表示用
    RETLW   B'11011010'         ; 5 の表示用
    RETLW   B'11111010'         ; 6 の表示用
    RETLW   B'00001110'         ; 7 の表示用
    RETLW   B'11111110'         ; 8 の表示用
    RETLW   B'11001110'         ; 9 の表示用
``` 

