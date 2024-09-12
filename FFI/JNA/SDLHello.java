import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class SDLHello {
    public interface SDLLibrary extends Library {
        SDLLibrary INSTANCE = (SDLLibrary)Native.loadLibrary( "SDL2", SDLLibrary.class);

        final int SDL_INIT_VIDEO = 0x00000020;
        int SDL_Init(int flags);
        
        // int SDL_ShowSimpleMessageBox(UInt32 flags, const char* title, const char* message, SDL_Window* window)
        int SDL_ShowSimpleMessageBox(int flags, String title, String message, Object window);

        final int SDL_MESSAGEBOX_INFORMATION = 0x00000040;
      }
        
    public static void main(String... args) {
        SDLLibrary sdl = SDLLibrary.INSTANCE;

        int result = sdl.SDL_Init(SDLHello.SDLLibrary.SDL_INIT_VIDEO);
        if (result != 0)
            System.out.println("Some kind of SDL error in init: " + result);
        result = sdl.SDL_ShowSimpleMessageBox(
            SDLLibrary.SDL_MESSAGEBOX_INFORMATION, "From Java!", "Hello, World!", 0);
        if (result != 0)
            System.out.println("Some kind of SDL error on message box: " + result);
    }
}
