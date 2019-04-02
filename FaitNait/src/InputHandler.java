import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{

    public InputHandler(Game Game){
        Game.addKeyListener(this);
    }

    public class Key {
        public boolean pressed = false;
        public void toggle(boolean isPressed){
            pressed = isPressed;
        }
        public boolean isPressed(){
            return pressed;
        }
    }

    public Key attack = new Key();
    public Key superAttack = new Key();
    public Key potion = new Key();
    public Key surrender = new Key();
    public Key training = new Key();
    public Key stores = new Key();
    public Key progress = new Key();
    public Key arena = new Key();
    public Key exit = new Key();
    public Key yes = new Key();
    public Key no = new Key();
    public Key newgame = new Key();
    public Key loadgame = new Key();
    public Key opt_1 = new Key();
    public Key opt_2 = new Key();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);

    }

    public void toggleKey(int keyCode, boolean isPressed){
        if(keyCode == KeyEvent.VK_L){
            loadgame.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_K){
            newgame.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_A){
            attack.toggle(isPressed);
        }

        if(keyCode == KeyEvent.VK_S){
            superAttack.toggle(isPressed);
        }

        if(keyCode == KeyEvent.VK_D){
            potion.toggle(isPressed);
        }

        if(keyCode == KeyEvent.VK_F){
            surrender.toggle(isPressed);
        }

        if(keyCode == KeyEvent.VK_Z){
            training.toggle(isPressed);
        }

        if(keyCode == KeyEvent.VK_X){
            stores.toggle(isPressed);
        }

        if(keyCode == KeyEvent.VK_C){
            progress.toggle(isPressed);
        }

        if(keyCode == KeyEvent.VK_V){
            arena.toggle(isPressed);
        }

        if(keyCode == KeyEvent.VK_ESCAPE){
            exit.toggle(isPressed);
        }

        if(keyCode == KeyEvent.VK_Y){
            yes.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_N){
            no.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_1){
            opt_1.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_2){
            opt_2.toggle(isPressed);
        }
    }
}

