section .data
    newline db 10


section .text
    global _start
_start:
    push 0
    mov rax, 6
    mov [rsp + 0], rax
    mov rax, 60
    mov rdi, [rsp + 0]
    syscall
    mov rax, 60
    mov rdi, 0
    syscall