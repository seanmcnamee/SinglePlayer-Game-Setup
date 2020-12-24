package app.game.gamefield.touchable;

import app.game.gamefield.map.Map;

public interface Destroyable {
    public boolean isDestroyed();
    public void gotHit(Touchable m, Map map);
}
