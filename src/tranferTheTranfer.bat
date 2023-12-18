:loop
set myprogram=Transfer
Start nxjc %myprogram%.java
PING localhost -n 2 >NUL
Start nxjlink -o %myprogram%.nxj %myprogram%
PING localhost -n 2 >NUL
Start nxjupload -r %myprogram%.nxj
PING localhost -n 5 >NUL
call getCmdPID
set "current_pid=%errorlevel%"
for /f "skip=3 tokens=2 delims= " %%a in ('tasklist /fi "imagename eq cmd.exe"') do (
    if "%%a" neq "%current_pid%" (
        TASKKILL /PID %%a /f >nul 2>nul
    )
)
del /f getCmdPID.exe %myprogram%.nxj %myprogram%.class
del /S /f *.class
pause
goto loop