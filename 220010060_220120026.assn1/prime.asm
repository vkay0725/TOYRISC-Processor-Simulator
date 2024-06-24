	.data
a:
	19
	.text
main:
	load %x0, $a, %x3 
	addi %x0, 0, %x10
	addi %x0, 2, %x6
	blt %x3, 2, notprime
	beq %x3, 2, yesprime
loop:
	div %x3, %x6, %x4
	beq %x31, 0, notprime
	addi %x6, 1, %x6
	beq %x3, %x6, yesprime
	blt %x6, %x3, loop
notprime:
	subi %x0, 1, %x10
	end
yesprime:
	addi %x0, 1, %x10
	end