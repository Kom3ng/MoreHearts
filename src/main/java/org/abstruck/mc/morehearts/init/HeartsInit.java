package org.abstruck.mc.morehearts.init;

import org.abstruck.mc.morehearts.common.capability.heart.*;

public class HeartsInit {
    public static void init(){
        HeartRegister.INSTANCE.registerHeartFactory(BoneHeart::new);
        HeartRegister.INSTANCE.registerHeartFactory(ZombieHeart::new);
        HeartRegister.INSTANCE.registerHeartFactory(SoulHeart::new);
        HeartRegister.INSTANCE.registerHeartFactory(EvilHeart::new);
        HeartRegister.INSTANCE.registerHeartFactory(CreeperHeart::new);
    }
}
