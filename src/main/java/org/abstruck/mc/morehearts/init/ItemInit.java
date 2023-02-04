package org.abstruck.mc.morehearts.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.abstruck.mc.morehearts.common.item.heart.*;
import org.abstruck.mc.morehearts.utils.ModUtil;

public class ItemInit {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, ModUtil.MOD_ID);

    public static final RegistryObject<Item> ZOMBIE_HEART = REGISTER.register("zombie_heart", ZombieHeartItem::new);
    public static final RegistryObject<Item> BONE_HEART = REGISTER.register("bone_heart", BoneHeartItem::new);
    public static final RegistryObject<Item> SOUL_HEART = REGISTER.register("soul_heart", SoulHeartItem::new);
    public static final RegistryObject<Item> RED_HEART_CONTAINER = REGISTER.register("red_heart_container", RedHeartContainer::new);
    public static final RegistryObject<Item> PIG_HEART = REGISTER.register("pig_heart", PigHeartItem::new);
    public static final RegistryObject<Item> CREEPER_HEART = REGISTER.register("creeper_heart", CreeperHeartItem::new);
    public static final RegistryObject<Item> EVIL_HEART = REGISTER.register("evil_heart", EvilHeartItem::new);
}
