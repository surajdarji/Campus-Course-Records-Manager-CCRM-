@echo off
REM ===============================================
REM Auto-generate sources.txt, compile, and run
REM ===============================================

REM Go to Desktop
cd /d C:\Users\Aditya\Desktop

REM Enable delayed variable expansion
setlocal enabledelayedexpansion

REM Delete old sources.txt if exists
if exist sources.txt del sources.txt

REM Generate sources.txt with relative paths
echo Generating sources.txt...
for /r edu %%f in (*.java) do (
    set "filepath=%%f"
    set "relpath=!filepath:%CD%\=!"
    echo !relpath! >> sources.txt
)

REM Delete old .class files
echo Cleaning old .class files...
for /r %%f in (*.class) do del "%%f"

REM Compile all Java files
echo Compiling sources...
javac -d . @sources.txt
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b
)

REM Run the Main class
echo Running program...
java -cp . edu.ccrm.Main

pause
