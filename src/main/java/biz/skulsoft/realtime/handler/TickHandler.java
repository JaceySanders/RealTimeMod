package biz.skulsoft.realtime.handler;

import biz.skulsoft.realtime.info.ModInfo;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TickHandler {
    private int tickCount = 0;
    private int syncTick = 1200;
    private World worldObj;

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event) {
        this.worldObj = event.world;
        if(ModInfo.MOD_ENABLED && ModInfo.USE_SERVERTIME && this.tickCount == 0){
            this.syncTime();
        }
        if(!ModInfo.MOD_ENABLED || !ModInfo.USE_SERVERTIME){
            this.worldObj.getWorldInfo().getGameRulesInstance().addGameRule("doDaylightCycle", "true", GameRules.ValueType.BOOLEAN_VALUE);
        }
        if (ModInfo.MOD_ENABLED) {
            ++this.tickCount;
            if (ModInfo.USE_SERVERTIME && this.tickCount % this.syncTick == 0) {
                this.syncTime();
            }
        }
    }

    private void syncTime() {
        this.worldObj.getWorldInfo().getGameRulesInstance().addGameRule("doDaylightCycle", "false", GameRules.ValueType.BOOLEAN_VALUE);
        
        SimpleDateFormat dateFormatbaseyear = new SimpleDateFormat("yy");
        Date datebaseyear = ModInfo.INIT_DATE;
        String inityear = dateFormat.format(datebaseyear);
        
        SimpleDateFormat dateFormatbaseday = new SimpleDateFormat("DD");
        Date datebaseday = ModInfo.INIT_DATE;
        String initday = dateFormat.format(datebaseday);
        
        SimpleDateFormat dateFormatyear= new simpleDateFormat("yy");
        Date dateyear= new Date();
        String year = dateFormat.format(dateyear);
        
        SimpleDateFormat dateFormatday = new SimpleDateFormat("DD");
        Date dateday = new Date();
        String day = dateFormat.format(dateday);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        Date date = new Date();
        String hour = dateFormat.format(date);
        
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("mm");
        Date date1 = new Date();
        String minute = dateFormat1.format(date1);
        
        int offset = ModInfo.TIME_OFFSET;
        float offset_sec = offset * 60 * 60;
        float offset_goal = offset_sec / 86400.0f * 24000.0f;
        int inityear_int= Integer.parseInt(inityear);
        int initday_int= Integer.parseInt(initday);
        int year_int = Integer.parseInt(year);
        int day_int= Integer.parseInt(day);
        int difyear_int= inityear_int - year_int;
        int difday_int= initday_int - day_int;
        int hour_int = Integer.parseInt(hour);
        int minute_int = Integer.parseInt(minute);
        float total_sec = difyear_int * 365 * 24 * 60 * 60 + difday_int * 24 * 60 * 60 + hour_int * 60 * 60 + minute_int * 60;
        float goal_time = total_sec / 31536000.0f * 24000.0f;
        long value = Math.round(goal_time) + 18000 + Math.round(offset_goal);
        this.worldObj.setWorldTime(value);
    }
}

