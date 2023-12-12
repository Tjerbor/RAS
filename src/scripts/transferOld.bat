set /p "myprogram=Enter:"
Start /B nxjc %myprogram%.java
PING localhost -n 2 >NUL
Start /B nxjlink -o %myprogram%.nxj %myprogram%
PING localhost -n 2 >NUL
Start /B nxjupload -r %myprogram%.nxj