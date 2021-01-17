package my.plaground;

public abstract class Prop extends Target {


    public boolean isDestroyed() {
        return this.hp == 0;
    }


}
