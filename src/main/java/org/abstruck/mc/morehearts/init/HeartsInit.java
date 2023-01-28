package org.abstruck.mc.morehearts.init;

import org.abstruck.mc.morehearts.common.capability.heart.BoneHeart;
import org.abstruck.mc.morehearts.common.capability.heart.HeartRegister;
import org.abstruck.mc.morehearts.common.capability.heart.ZombieHeart;

public class HeartsInit {
    public static void init(){
        HeartRegister.INSTANCE.registerHeartFactory(BoneHeart::new);
        HeartRegister.INSTANCE.registerHeartFactory(ZombieHeart::new);
    }
}
