package Keyboard;

import PongGame.PongPanel;
import com.sun.glass.events.KeyEvent;
import java.util.HashMap;
import javafx.scene.input.KeyCode;
import javax.swing.KeyStroke;

public class KeyboardManager{
    
    private PongPanel pongPanel;
    private HashMap<KeyCode,Boolean> keysDown;
    private HashMap<KeyCode,Integer> keysPressed;
    private final int keyPressDefaultDelay = 3;
    
    public KeyboardManager(PongPanel pongPanel) {
        this.pongPanel = pongPanel;
        keysDown = new HashMap<>();
        keysPressed = new HashMap<>();
    }
    
    public HashMap<KeyCode,Boolean> getKeysDown() {
        return keysDown;
    }
    
    public HashMap<KeyCode,Integer> getKeysPressed() {
        return keysPressed;
    }
    
    public boolean keyDown(KeyCode keyCode) {
        if (!keysDown.containsKey(keyCode)) {
            addKey(keyCode);
        }
        return keysDown.get(keyCode);
    }
    
    public boolean keyPressed(KeyCode keyCode) {
        return keyPressed(keyCode, keyPressDefaultDelay);
    }
    
    public boolean keyPressed(KeyCode keyCode, int delay) {
        if (!keysDown.containsKey(keyCode)) {
            addKey(keyCode);
        }
        if (keyDown(keyCode)) {
            if (keysPressed.get(keyCode) >= 1) {
                keysPressed.replace(keyCode, -delay);
                return true;
            }
        }
        return false;
    }
    
    private void addKey(KeyCode keyCode) {
        keysDown.put(keyCode, false);
        keysPressed.put(keyCode, 0);
        
        String[] split = keyCode.toString().split(" ");
        String key = split[split.length-1];
        
        pongPanel.getInputMap(2).put(KeyStroke.getKeyStroke("pressed "+key), key+" press");
        pongPanel.getInputMap(2).put(KeyStroke.getKeyStroke("released "+key), key+" release");
        
        pongPanel.getActionMap().put(key+" press", new PressKeyAction(this, keyCode));
        pongPanel.getActionMap().put(key+" release", new ReleaseKeyAction(this, keyCode));
    }
}