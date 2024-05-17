section .data
    newline db 10
    string3 db "Hello, World!"
    stringlen3 equ $-string3


section .text
    global _start
_start:
    mov rax, 10
    push rax
    mov rax, 20
    push rax
    mov rax, [rsp + 0]
    push rax
    mov rax, 1
    mov rdi, 1
    mov rsi, string3
    mov rdx, stringlen3
    syscall
    mov rax, 1
    mov rdi, 1
    mov rsi, newline
    mov rdx, 1
    syscall
    mov rax, 60
    mov rdi, [rsp + 0]
    syscall
    mov rax, 60
    mov rdi, 0
    syscall