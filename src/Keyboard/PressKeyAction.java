package Keyboard;

import Keyboard.KeyboardManager;
import java.awt.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javax.swing.AbstractAction;

public class PressKeyAction extends AbstractAction {

    private KeyboardManager keyboardManager;
    private KeyCode keyCode;
    
    PressKeyAction(KeyboardManager keyboardManager, KeyCode keyCode) {
        this.keyboardManager = keyboardManager;
        this.keyCode = keyCode;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        keyboardManager.getKeysDown().replace(keyCode, true);
        keyboardManager.getKeysPressed().replace(keyCode, keyboardManager.getKeysPressed().get(keyCode)+1);
    }
    
}
