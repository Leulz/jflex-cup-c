TRUE:
  .long 1
FALSE:
  .zero 4
calc:
  push rbp
  mov rbp, rsp
  mov DWORD PTR [rbp-4], edi
  mov DWORD PTR [rbp-8], esi
  mov eax, edx
  mov BYTE PTR [rbp-12], al
  movsx eax, BYTE PTR [rbp-12]
  sub eax, 38
  cmp eax, 86
  ja .L2
  mov eax, eax
  mov rax, QWORD PTR .L4[0+rax*8]
  jmp rax
.L4:
  .quad .L3
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L5
  .quad .L6
  .quad .L2
  .quad .L7
  .quad .L5
  .quad .L8
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L23
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L10
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L10
  .quad .L2
  .quad .L2
  .quad .L2
  .quad .L11
.L11:
  cmp DWORD PTR [rbp-4], 0
  jne .L12
  cmp DWORD PTR [rbp-8], 0
  je .L13
.L12:
  mov eax, 1
  jmp .L15
.L13:
  mov eax, 0
  jmp .L15
.L3:
  cmp DWORD PTR [rbp-4], 0
  je .L16
  cmp DWORD PTR [rbp-8], 0
  je .L16
  mov eax, 1
  jmp .L15
.L16:
  mov eax, 0
  jmp .L15
.L10:
  cmp DWORD PTR [rbp-4], 0
  jne .L18
  cmp DWORD PTR [rbp-8], 0
  je .L19
.L18:
  cmp DWORD PTR [rbp-4], 0
  je .L20
  cmp DWORD PTR [rbp-8], 0
  jne .L19
.L20:
  mov eax, 1
  jmp .L15
.L19:
  mov eax, 0
  jmp .L15
.L6:
  mov edx, DWORD PTR [rbp-4]
  mov eax, DWORD PTR [rbp-8]
  add eax, edx
  jmp .L15
.L7:
  mov eax, DWORD PTR [rbp-8]
  sub DWORD PTR [rbp-4], eax
  mov eax, DWORD PTR [rbp-4]
  jmp .L15
.L5:
  mov eax, DWORD PTR [rbp-4]
  imul eax, DWORD PTR [rbp-8]
  jmp .L15
.L8:
  mov eax, DWORD PTR [rbp-4]
  cdq
  idiv DWORD PTR [rbp-8]
  mov DWORD PTR [rbp-4], eax
  mov eax, DWORD PTR [rbp-4]
  jmp .L15
.L2:
  mov eax, 0
  jmp .L15
.L23:
  nop
  mov eax, 1
.L15:
  pop rbp
  ret
turnFloatToInt:
  push rbp
  mov rbp, rsp
  movss DWORD PTR [rbp-4], xmm0
  movss xmm0, DWORD PTR [rbp-4]
  cvttss2si eax, xmm0
  pop rbp
  ret
turnStringToInt:
  push rbp
  mov rbp, rsp
  mov QWORD PTR [rbp-8], rdi
  mov eax, 1
  pop rbp
  ret
.LC0:
  .string "oi"
main:
  push rbp
  mov rbp, rsp
  mov ecx, 0
  mov eax, 1
  mov edx, 124
  mov esi, ecx
  mov edi, eax
  call calc
  mov ecx, 0
  mov eax, 1
  mov edx, 38
  mov esi, ecx
  mov edi, eax
  call calc
  mov edi, OFFSET FLAT:.LC0
  call turnStringToInt
  mov ecx, eax
  mov eax, 1
  mov edx, 94
  mov esi, ecx
  mov edi, eax
  call calc
  mov ecx, 0
  mov eax, 1
  mov edx, 120
  mov esi, ecx
  mov edi, eax
  call calc
  mov ecx, 0
  mov eax, 1
  mov edx, 62
  mov esi, ecx
  mov edi, eax
  call calc
  mov edx, 78
  mov esi, 17
  mov edi, 0
  call calc
  mov edx, 43
  mov esi, 3
  mov edi, 2
  call calc
  mov edx, 45
  mov esi, 444
  mov edi, 3
  call calc
  movss xmm0, DWORD PTR .LC1[rip]
  call turnFloatToInt
  mov edx, 47
  mov esi, 1349
  mov edi, eax
  call calc
  mov edx, 42
  mov esi, 23
  mov edi, 5
  call calc
  mov eax, 0
  pop rbp
  ret
.LC1:
  .long 1222181984