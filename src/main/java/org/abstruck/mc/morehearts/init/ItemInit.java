package org.abstruck.mc.morehearts.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.abstruck.mc.morehearts.common.item.heart.BoneHeartItem;
import org.abstruck.mc.morehearts.common.item.heart.SoulHeartItem;
import org.abstruck.mc.morehearts.common.item.heart.ZombieHeartItem;
import org.abstruck.mc.morehearts.utils.ModUtil;

public class ItemInit {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, ModUtil.MOD_ID);

    public static final RegistryObject<Item> ZOMBIE_HEART = REGISTER.register("zombie_heart", ZombieHeartItem::new);
    public static final RegistryObject<Item> BONE_HEART = REGISTER.register("bone_heart", BoneHeartItem::new);
    public static final RegistryObject<Item> SOUL_HEART = REGISTER.register("soul_heart", SoulHeartItem::new);
}
