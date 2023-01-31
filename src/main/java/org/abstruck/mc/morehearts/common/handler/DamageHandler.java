package org.abstruck.mc.morehearts.common.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import org.abstruck.mc.morehearts.common.capability.ModCapability;
import org.abstruck.mc.morehearts.common.capability.heart.AttachHeart;
import org.abstruck.mc.morehearts.common.capability.heart.ExtraHeart;
import org.abstruck.mc.morehearts.common.event.ExtraHeartChangeEvent;
import org.abstruck.mc.morehearts.common.event.ModEventBus;
import org.abstruck.mc.morehearts.utils.collection.list.ListUtil;
import org.abstruck.mc.morehearts.utils.heart.HeartUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber
public class DamageHandler {
    @SubscribeEvent
    public static void onHurt(@NotNull LivingHurtEvent event){
        if (!(event.getEntity() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntity();

        player.getCapability(ModCapability.PLAYER_CAP).ifPresent(cap -> {
            List<AttachHeart> attachHearts = cap.getAttachHearts();
            List<ExtraHeart> extraHearts = cap.getExtraHearts();
            AtomicReference<Float> damage = new AtomicReference<>(event.getAmount());

            //没有额外心就不处理
            if (!extraHearts.isEmpty()){
                //有额外心的逻辑
                if (damage.get() > cap.getAllExtraHealth()){
                    //伤害大于所有额外心加起来抵消的值时的逻辑

                    //将抵消伤害过后的事件重新计算
                    event.setAmount(event.getAmount() - cap.getAllExtraHealth());
                    //清空额外心
                    cap.setExtraHearts(new ArrayList<>());

                    //激活每一个额外心
                    extraHearts.forEach(eh -> eh.activate(event));

                    ModEventBus.postSynchronousEvent(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player),new ExtraHeartChangeEvent(true,cap.getExtraHearts()));
//                    onHurt(event);
                }else {
                    //额外心可以抵消所有伤害时的逻辑
                    ListUtil.reverseForEachWithIndex(extraHearts,((index, extraHeart) -> {
                        //伤害减免完时停止
                        if (damage.get()<0.0F) return;
                        //从末尾往前去除额外心
                        cap.removeExtraHeart(index);
                        //激活额外心
                        extraHeart.activate(event);
                        //将伤害减去一颗心低效的伤害
                        damage.set(damage.get()-2/extraHeart.getDecelerationMagnification());
                    }));
                    ModEventBus.postSynchronousEvent(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player),new ExtraHeartChangeEvent(true,cap.getExtraHearts()));
                    //将伤害设为0
                    event.setAmount(0.0F);

                    return;
                }
            }
            damage.set(event.getAmount());

            //没有附加心就不处理
            if (attachHearts.isEmpty()) return;
            //处理过后的伤害
            float damageAfterDeal = 0.0F;
            //剩余的未处理的伤害
            float damageLeft = damage.get();

            //玩家生命少于附加心的保护的生命值范围内
            if (player.getHealth() < attachHearts.size()*2){
                //获取生命值的最右端在哪个附加心内
                int index = HeartUtil.indexOfHeartLiesInWitchHeart(player.getHealth(), attachHearts);
                hurtAttachHeart(event, player, attachHearts, damageAfterDeal, damageLeft, index);
                return;
            }

            //伤害触及不到附加心保护的范围值呢就不处理
            if (player.getHealth() - attachHearts.size()*2 > damage.get()) return;

            //玩家的生命值大于附加心的保护且伤害回触及到保护范围内
            //此时最末端的触发的附加心索引就是最末端的附加心
            int index = attachHearts.size()-1;
            //没有被保护的心
            damageLeft -= player.getHealth() - attachHearts.size()*2;
            damageAfterDeal += player.getHealth() - attachHearts.size()*2;

            hurtAttachHeart(event, player, attachHearts, damageAfterDeal, damageLeft, index);
        });
    }

    private static void hurtAttachHeart(@NotNull LivingHurtEvent event, PlayerEntity player, List<AttachHeart> attachHearts, float damageAfterDeal, float damageLeft, int index) {
        while (index >= 0){
            float offset = 2.0F/attachHearts.get(index).getDecelerationMagnification();
            attachHearts.get(index).activate(event);
            if (damageLeft<offset){
                damageAfterDeal += damageLeft*attachHearts.get(index).getDecelerationMagnification();
                break;
            }
            damageLeft -= offset;
            damageAfterDeal += 2.0F;
            index--;
        }
        event.setAmount(damageAfterDeal);
    }
}
