rem @echo off
set source=%~dp0\nbdist

echo source: [%source%]
rem del d:\reciter\QuranReciter.jar
rem RD /Q /S d:\reciter\lib
rem copy /y %~dp0\nbdist\*.* d:\reciter\


rem D:\Dropbox\QuranReciterDeploy\Windows\64\reciter\

rem del D:\Dropbox\QuranReciterDeploy\Windows\64\reciter\QuranReciter.jar
rem RD /Q /S D:\Dropbox\QuranReciterDeploy\Windows\64\reciter\lib
rem copy /y %~dp0\nbdist\*.* D:\Dropbox\QuranReciterDeploy\Windows\64\reciter\


rem D:\Dropbox\QuranReciterDeploy\Windows\32\reciter



rem D:\Dropbox\QuranReciterDeploy\Mac+Ubuntu\QuranReciter




rem D:\google\ReciterDeploy\QuranReciterDeploy\Mac+Ubuntu\QuranReciter




rem D:\google\ReciterDeploy\QuranReciterDeploy\Windows\64\reciter




rem D:\google\ReciterDeploy\QuranReciterDeploy\Windows\32\reciter


for %%x in (
d:\reciter, 
D:\Dropbox\QuranReciterDeploy\Windows\64\reciter, 
D:\Dropbox\QuranReciterDeploy\Windows\32\reciter, 
D:\Dropbox\QuranReciterDeploy\Mac_Ubuntu\QuranReciter,
D:\google\ReciterDeploy\QuranReciterDeploy\Windows\64\reciter,
D:\google\ReciterDeploy\QuranReciterDeploy\Windows\32\reciter,
D:\google\ReciterDeploy\QuranReciterDeploy\Mac_Ubuntu\QuranReciter
J:\reciter
) do (
   echo deploying on [%%x]
   rem del %source%\readme.txt
   rem xcopy /y /s %source%\*.* %%x
   copy /y %~dp0\nbdist\QuranReciter.jar %%x\QuranReciter.jar
   copy /y %~dp0\nbdist\lib\sound.jar %%x\lib\sound.jar
   copy /y %~dp0\nbdist\lib\commons.jar %%x\lib\commons.jar
)

pause