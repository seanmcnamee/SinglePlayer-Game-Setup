package app.game.gamefield.movable.player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StateHandler {
    //The player can be in one of these four states.
    //Note that the change from RECHARGING to IDLE is handled internally
    public enum State {
        IDLE, FIGHTING, RECHARGING, DEAD
    }

    private State currentState;
    private int waitDelay;
    private ProjectileHandler projectiles;
    private ScheduledExecutorService executorService;

    public StateHandler(ProjectileHandler p, int waitDelay) {
        this.projectiles = p;
        this.waitDelay = waitDelay;
        this.currentState = State.IDLE;
    }

    public void setFighting() {
        if (this.currentState == State.RECHARGING || this.currentState == State.IDLE) {
            System.out.println("Setting fighting");
            this.currentState = State.FIGHTING;
            if (executorService != null) {
                executorService.shutdown();
            }
        }
    }

    public void setRecharging() {
        if (this.currentState == State.FIGHTING) {
            System.out.println("Setting recharging");
            this.currentState = State.RECHARGING;
            if (executorService == null || executorService.isShutdown()) {
                this.executorService = Executors.newScheduledThreadPool(2);
                executorService.scheduleWithFixedDelay(new Thread(() -> updateWait(this)), 
                            2*this.waitDelay, this.waitDelay, TimeUnit.MILLISECONDS);
            }
        }
    }

    public void setDead() {
        this.currentState = State.DEAD;
    }
   
    public void updateWait(StateHandler sH) {
        if (this.currentState == State.RECHARGING) {
            if (sH.projectiles.projectilesFull()) {
                sH.currentState = State.IDLE;
                executorService.shutdown();
            } else {
                sH.projectiles.rechargeProjectile();
            }
        //Otherwise, make sure the timer is off.
        } else {
            executorService.shutdown();
        }
    }

    public String getState() {
        return currentState.toString();
    }

    public boolean isDead() {
        return currentState == State.DEAD;
    }
}
