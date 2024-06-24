	.data
a:
	10
	.text
main:
	load %x0, $a, %x3
	divi %x3, 2, %x4
	addi %x0, 0, %x10
	beq %x31, %x0, even
	addi %x10, 1, %x10
	end
even:
	subi %x10, 1, %x10
	end