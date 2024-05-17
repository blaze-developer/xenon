nasm -felf64 $1.asm && ld $1.o -o $1
./$1
echo "Exited with code $?"