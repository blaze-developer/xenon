section .data
    newline db 10


section .text
    global _start
_start:

    mov rax, 60
    mov rdi, 2
    syscall