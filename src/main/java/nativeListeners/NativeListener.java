package nativeListeners;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.W32APIOptions;
import oshi.software.os.OSProcess;
import oshi.software.os.windows.WindowsOperatingSystem;

/**
 * Created by romariomkk on 02.10.2016.
 */
public class NativeListener {

    public static void main(String[] args) {
        Kernel32 kernel32 = (Kernel32) Native.loadLibrary(Kernel32.class, W32APIOptions.DEFAULT_OPTIONS);
        Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();
        WinNT.HANDLE processSnapshot =
                kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));

        try {
            while (kernel32.Process32Next(processSnapshot, processEntry)) {
                // looks for a specific process
                if (Native.toString(processEntry.szExeFile).equalsIgnoreCase("notepad.exe")) {
                    WindowsOperatingSystem sys = new WindowsOperatingSystem();
                    OSProcess process = sys.getProcess(processEntry.th32ProcessID.intValue());

                    System.out.println(processEntry.th32ProcessID + "\t" + Native.toString(processEntry.szExeFile) + "\t");
                    System.out.println(process.getResidentSetSize());
                }

            }
        } finally {
            kernel32.CloseHandle(processSnapshot);
        }



    }
}