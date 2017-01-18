package Keyboard;

import Keyboard.KeyboardManager;
import java.awt.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javax.swing.AbstractAction;

public class ReleaseKeyAction extends AbstractAction {

    private KeyboardManager keyboardManager;
    private KeyCode keyCode;
    
    ReleaseKeyAction(KeyboardManager keyboardManager, KeyCode keyCode) {
        this.keyboardManager = keyboardManager;
        this.keyCode = keyCode;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        keyboardManager.getKeysDown().replace(keyCode, false);
        keyboardManager.getKeysPressed().replace(keyCode, 0);
    }
    
}
