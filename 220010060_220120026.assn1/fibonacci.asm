	.data
n:
	10
	.text
main:
	load %x0, $n, %x3
	addi %x0, 1, %x5
	addi %x0, 0, %x4
	addi %x0, 65535, %x8
	blt %x5, %x3, first
	blt %x3, 2, exit
	end
first:
	store %x4, 0, %x8
	subi %x8, 1, %x8
	bgt %x3, 2, second
	end
second:
	store %x5, 0, %x8
	addi %x0, 1, %x7
	subi %x8, 1, %x8
loop:
	addi %x7, 1, %x7
	beq %x3 %x7, exit
	add %x4, %x5, %x6
	add %x0, %x5, %x4
	add %x6, %x0, %x5
	store %x6, 0, %x8
	subi %x8, 1, %x8
	jmp loop
exit:
	end