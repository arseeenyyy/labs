org 0x1a3 
start: 
cla 
st result
ld z
inc 
push
call $function
pop
dec
sub result
st result 
ld x
inc
push
call $function
pop
inc 
sub result
st result 
ld y
dec
push
call $function
pop
dec
sub result 
st result
hlt
z: word 0x0077
y: word 0xfd64
x: word 0x049b
result: word 0x00a4

org 0x695
function: 
ld &0x01
bmi exit_1
cmp A
beq exit_2
blt exit_2
exit_1:
add &0x01
add &0x01
add &0x01
add B
jump bimbim	 
exit_2:
ld A
bimbim: st &0x01
ret
A: word 0x0691
B: word 0xf015
