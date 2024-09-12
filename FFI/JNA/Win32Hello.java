import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class Win32Hello {
    public interface User32Library extends Library {
        User32Library INSTANCE = (User32Library)
          Native.loadLibrary( (Platform.isWindows() ? "user32" : "cant-run-anywhere-else"),
                              User32Library.class);
        
        // MessageBoxA([in, optional] HWND hWnd, [in, optional] LPCTSTR lpText, 
        //             [in, optional] LPCTSTR lpCaption, [in] UINT uType)
        int MessageBoxA(int hWnd, String lpText, String lpCaption, int uType);
      }
        
    public static void main(String... args) {
        User32Library.INSTANCE.MessageBoxA(0, "From Java!", "Hello, World!", 0);
    }
}
