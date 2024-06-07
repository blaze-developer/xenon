nasm -felf64 test.asm && ld test.o -o test
./test
echo "Exited with code $?"
rm test.o 'test'$''
