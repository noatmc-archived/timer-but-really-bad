package vevo.codex.codexhack.features.modules.player;

import net.minecraft.network.play.client.CPacketPlayer;
import vevo.codex.codexhack.event.events.PacketEvent;
import vevo.codex.codexhack.features.modules.Module;
import vevo.codex.codexhack.features.setting.Setting;
import vevo.codex.codexhack.util.EntityUtil;

/*
* Credits
* - Codex#4562: [rudimentary shit code]
* - Doogie13: [explaining codex tickshift]
* - noat (me): [fix codex's code to make it works]
* - hausemasterissue [cleaning up and fixing the code]
*/
public class TickShift extends Module {
    
    public TickShift() {
        super("TickShift", "funny exploit on top", Category.PLAYER, true, false, false);
    }
    
    Setting<Integer> ticksVal = this.register(new Setting<>("Ticks", 18, 1, 100));
    Setting<Float> timer = this.register(new Setting<>("Timer", 1.8f, 1f, 3f));
    Setting<Integer> disableTicks = this.register(new Setting<>("DisableTicks", 17, 1, 100));
    
    private int ticksPassed = 0;

    public void onEnable() {
        canTimer = false;
        tick = 0;
    }

    boolean canTimer = false;
    int tick = 0;

    public void onUpdate() {
        if (tick <= 0)  {
            tick = 0; 
            canTimer = false; 
            mc.timer.tickLength = 50f;
        }
        
        if (tick > 0 && EntityUtil.isEntityMoving(mc.player)) {
            tick--;
            mc.timer.tickLength = 50f / timer.getValue();
            ticksPassed++;
        }
        
        if (!EntityUtil.isEntityMoving(mc.player)) {
            tick++;   
        }
        
        if (tick >= ticksVal.getValue()) {
            tick = ticksVal.getValue();
        }
        
        if(ticksPassed >= disableTicks.getValue()) {
            disable();   
        }
    }

    @Override
    public String getDisplayInfo() {
        return String.valueOf(tick);
    }
    
    public void onDisable() {
        mc.timer.tickLength = 50f;
    }
}
