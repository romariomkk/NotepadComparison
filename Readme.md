The program that opens two instances of notepad.exe.
On request the memory usage of these 2 processes is compared and the high=consuming app is closed.
Java implementation by using simple Runtime class functions and tasklist's rows parsing.

Another variant was attempted to be done using JNA, but author was unable to find a proper readProcessMemory() method.
JNA is used only to show process ID.