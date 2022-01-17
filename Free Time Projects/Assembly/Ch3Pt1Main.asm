.386
.model flat, stdcall
.stack 4096

ExitProcess PROTO, dwExitCode:DWORD

.data

A_Val DWORD 4
B_Val DWORD 3
C_Val DWORD 2
D_Val DWORD 1

.code
main PROC

	mov eax,A_Val  ; Assigns A_Val to eax register
	mov ebx,B_Val  ; Assigns B_Val to ebx register
	mov ecx,C_Val  ; Assigns C_Val to ecx register
	mov edx,D_Val  ; Assigns D_Val to edx register

	add eax,ebx    ; Adds value in ebx to eax
	add ecx,edx    ; Adds value in edx to ecx
	sub eax,ecx    ; Subtracts ecx from eax

  INVOKE ExitProcess, 0
main ENDP

END main        ;specify the program's entry point