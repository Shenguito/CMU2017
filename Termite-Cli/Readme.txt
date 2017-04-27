open: Termite-Cli\etc\platform\windows\backends.conf
configure: SDK & VMI

cmd:
set TERMITE_CLI_PATH=$env$\Termite-Cli
set TERMITE_PLATFORM=windows

termite.bat

newdevice A		//create devices
newdevide B

list devices	//see devices

assignaddr e1	//assign address to e1

list emus		//see emulator

binddevice A e1	//bind device to emulator

list devices	//see what changed

ping			//click "WiFi On" on emulator

move A (B)
list neighbors
commit

**Error: KO: unknown command, try 'help**
erase c:\Users\<current_user>\.emulator_console_auth_token

**There is Scenario 1 in $env$\Termite-Cli\scripts**


END