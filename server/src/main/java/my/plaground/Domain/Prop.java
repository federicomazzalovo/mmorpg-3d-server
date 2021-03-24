package my.plaground.Domain;

public abstract class Prop extends Target {


    public boolean isDestroyed() {
        return this.hp == 0;
    }


}
