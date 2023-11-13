package jade;

import org.lwjgl.Version;

public class Window {

    private int width, height;

    private static Window window = null;
    private String title;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();
    }

    public void init() {

    }

    public void loop() {
        
    }
}
