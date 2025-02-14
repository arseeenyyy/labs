ORG 0x0 
V0:	WORD $default, 0x180 	 
V1: WORD $int1, 0x180  	
V2: WORD $int2, 0x180 
V3: WORD $int3, 0x180  
V4: WORD $default, 0x180
V5: WORD $default, 0x180
V6: WORD $default, 0x180
V7: WORD $default, 0x180

default: IRET 

ORG 0x20 
X: WORD 0x0000 
Y: WORD 0x0000 
Z: WORD 0x0000 


START:	DI 
		CLA 
		OUT 0x1 ; запрет прерываний на ВУ-0
		OUT 0xB ; запрет прерываний на ВУ-4
		OUT 0xE ; запрет прерываний на ВУ-5 
		OUT 0x12 ; запрет прерываний на ВУ-6
		OUT 0x16 ; запрет прерываний на ВУ-7
		OUT 0x1A ; запрет прерываний на ВУ-8
		OUT 0x1E ; запрет прерываний на ВУ-9
		LD #0x9 ; загрузить в аккумулятор MR(1000|0001=1001)
		OUT 0x3	; разрешить прерывания на ВУ-1
		LD #0xA ; загрузить в аккумулятор MR(1000|0010=1010)
		OUT 0x5 ; разрешить прерывания на ВУ-2
		LD #0xB ; загрузить в аккумулятор MR(1000|0011=1011)
		OUT 0x7 ; разрешить прерывания на ВУ-3
		LD #0xE
		OUT 0x12
		EI

;основная программа, осуществляет безусловных переход сама в себя, ожидает прерывание
main:	DI 
		EI 
		JUMP main 
;прерывание на ВУ-1
int1:	CALL respawn ; вызов подпрограммы с очисткой индикатора
		CLA 
main1:	DI ; запрет прерываний 
		OUT 0x2 ; сбрасываем готовность ВУ-1
		LD $X 
		INC
		ST $X
		OUT 0x14 ; выводим текущее значение ячейки X 
		EI ; разрешаем прерывания
		JUMP main1 ; безусловный переход, будет прерван, когда возникнет новое прерывание
		IRET 
;прерывание на ВУ-2
int2:	CALL respawn ; вызов подпрограммы с очисткой индикатора
		CLA 
main2:	DI ; запрет прерываний
		IN 0x4 ; сбрасываем готовность ВУ-2
		LD $Y
		INC 
		INC 
		ST $Y 
		OUT 0x14 ; выводим текущее значение ячейки Y
		EI ; разрешаем прерывания
		JUMP main2 ; безусловный переход, будет прерван, когда возникнет новое прерывание
		IRET 
;прерывание на ВУ-3
int3:	CALL respawn ; вызов подпрограммы с очисткой индикатора
		CLA 
main3:	DI ; запрет прерываний
		OUT 0x6 ; сбрасываем готовность ВУ-3
		LD $Z 
		INC
		INC
		INC 
		ST $Z 
		OUT 0x14 ; выводим текущее значение ячейки Z
		EI ; разрешаем прерывания
		JUMP main3 ; безусловный переход, будет прерван, когда возникнет новое прерывание
		IRET 

;подпрограмма, которая очищает поле семисегментного индикатора
respawn:	LD #0x1F
			OUT 0x14 
			LD #0x2F 
			OUT 0x14 
			LD #0x3F 
			OUT 0x14 
			LD #0x4F 
			OUT 0x14 
			LD #0x5F 
			OUT 0x14 
			LD #0x6F 
			OUT 0x14
			RET
			 

