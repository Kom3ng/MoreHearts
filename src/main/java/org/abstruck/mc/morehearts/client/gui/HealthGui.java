package org.abstruck.mc.morehearts.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.abstruck.mc.morehearts.common.capability.ModCapability;
import org.abstruck.mc.morehearts.common.capability.heart.AttachHeart;
import org.abstruck.mc.morehearts.common.capability.heart.ExtraHeart;
import org.abstruck.mc.morehearts.common.capability.player.IPlayerCapability;
import org.abstruck.mc.morehearts.utils.ModUtil;
import org.abstruck.mc.morehearts.utils.collection.list.ListUtil;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@OnlyIn(Dist.CLIENT)
public class HealthGui extends IngameGui {
    public static HealthGui INSTANCE = new HealthGui(Minecraft.getInstance());
    private static final ResourceLocation HEART_ICON = new ResourceLocation(ModUtil.MOD_ID,"textures/gui/hearts.png");

    public static int left_height = ForgeIngameGui.left_height;
    public static int right_height = ForgeIngameGui.right_height;

    private HealthGui(Minecraft mc){
        super(mc);
    }

    public void render(@NotNull MatrixStack mStack){
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();

        PlayerEntity player = (PlayerEntity)this.minecraft.getCameraEntity();
        if (player == null) return;

        player.getCapability(ModCapability.PLAYER_CAP).ifPresent((cap) -> {
            bindHeartIcon();

            RenderSystem.enableBlend();

            //玩家当前血量
            int health = getHealth(player);
            //是否高亮
//            boolean highlight = willHighlight();

            //这段代码在原版中已经有了，不应该再给变量重新赋值
//            if (health < this.lastHealth && player.invulnerableTime > 0) {
//                //扣血时高亮一段时间
//                this.lastHealthTime = Util.getMillis();
//                this.healthBlinkTime = (long)(this.tickCount + 20);
//            } else if (health > this.lastHealth && player.invulnerableTime > 0) {
//                //回血时高亮
//                this.lastHealthTime = Util.getMillis();
//                this.healthBlinkTime = (long)(this.tickCount + 10);
//            }
//
//            if (Util.getMillis() - this.lastHealthTime > 1000L) {
//                this.lastHealth = health;
//                this.displayHealth = health;
//                this.lastHealthTime = Util.getMillis();
//            }

//            this.lastHealth = health;
//            int healthLast = getHealthLast();

            //获取最大血量
            Float healthMax = getMaxHealth(player);
            if (healthMax == null) return;
            //获取伤害吸收增加的心
            float absorb = getAbsorb(player);

            //心的排数
            int healthRows = countHealthRows(healthMax, absorb);
            //每排的高度
            int rowHeight = countRowHeight(healthRows);

//            this.random.setSeed((long)(tickCount * 312871L));

            //血量的最左边
            int left = countLeft(width);
            //血亮的最高处高度
            int top = countTop(height);
            //血量左侧的高度
            countLeftHeight(healthRows, rowHeight);

            //待分析
            if (rowHeight != 10) left_height += 10 - rowHeight;

            int regen = countRegen(player);
//            //null check
//            final Integer TOP = countTOP();
//            if (TOP == null) return;
//            //高亮背景和普通背景之间的差值
//            final int BACKGROUND = (highlight ? 25 : 16);
            //中毒和凋零效果导致的血量样子的改变
            int MARGIN = countMargin(player);

            List<ExtraHeart> extraHearts = getExtraHearts(cap);
            List<AttachHeart> attachHearts = getAttachHearts(cap);


            int extraHeartsRows = MathHelper.ceil(extraHearts.size() / 10.0F);
            int extraHeartRowHeight = countRowHeight(extraHeartsRows);
            int extraHeartsTop = top - extraHeartsRows * extraHeartRowHeight;

            ListUtil.reverseForEachWithIndex(extraHearts, (index, extraHeart) -> {
                int row = getRow(index);
                int x = getX(left,index);
                int y = getY(extraHeartRowHeight, extraHeartsTop, row);

                blit(mStack,x,y,extraHeart.getUOffSet(),extraHeart.getVOffSet(),9,9);
            });

            blitAttachHearts(mStack, health, healthMax, absorb, rowHeight, left, top, regen, attachHearts);

            RenderSystem.disableBlend();
        });
//        PlayerEntity player = (PlayerEntity) minecraft.getCameraEntity();
//        if (player == null) return;
//        int health = MathHelper.ceil(player.getHealth());
//
//        float healthMax = player.getMaxHealth();
//        float absorb = MathHelper.ceil(player.getAbsorptionAmount());
//
//        boolean highlight = healthBlinkTime > (long)tickCount && (healthBlinkTime - (long)tickCount) / 3L %2L == 1L;
//
//        if (health < this.lastHealth && player.invulnerableTime > 0)
//        {
//            this.lastHealthTime = Util.getMillis();
//            this.healthBlinkTime = (long)(this.tickCount + 20);
//        }
//        else if (health > this.lastHealth && player.invulnerableTime > 0)
//        {
//            this.lastHealthTime = Util.getMillis();
//            this.healthBlinkTime = (long)(this.tickCount + 10);
//        }
//
//        if (Util.getMillis() - this.lastHealthTime > 1000L)
//        {
//            this.lastHealth = health;
//            this.displayHealth = health;
//            this.lastHealthTime = Util.getMillis();
//        }
//
//        int healthRows = MathHelper.ceil((healthMax + absorb) / 2.0F / 10.0F);
//        int rowHeight = Math.max(10 - (healthRows - 2), 3);
//        int left = width / 2 - 91;
//        int top = height - left_height;
//        left_height += (healthRows * rowHeight);
//        if (rowHeight != 10) left_height += 10 - rowHeight;
//
//        int regen = -1;
//        if (player.hasEffect(Effects.REGENERATION))
//        {
//            regen = tickCount % 25;
//        }
//
//        final int TOP =  9 * (minecraft.level.getLevelData().isHardcore() ? 5 : 0);
//        final int BACKGROUND = (highlight ? 25 : 16);
//        int MARGIN = 16;
//        if (player.hasEffect(Effects.POISON))      MARGIN += 36;
//        else if (player.hasEffect(Effects.WITHER)) MARGIN += 72;
//        float absorbRemaining = absorb;
//
//        for (int i = MathHelper.ceil((healthMax + absorb) / 2.0F) - 1; i >= 0; --i) {
//            //int b0 = (highlight ? 1 : 0);
//            int row = MathHelper.ceil((float)(i + 1) / 10.0F) - 1;
//            int x = left + i % 10 * 8;
//            int y = top - row * rowHeight;
//
//            if (health <= 4) y += random.nextInt(2);
//            if (i == regen) y -= 2;
//
//            blit(mStack, x, y, BACKGROUND, TOP, 9, 9);
//
//            if (highlight)
//            {
//                if (i * 2 + 1 < healthLast)
//                    blit(mStack, x, y, MARGIN + 54, TOP, 9, 9); //6
//                else if (i * 2 + 1 == healthLast)
//                    blit(mStack, x, y, MARGIN + 63, TOP, 9, 9); //7
//            }
//
//            if (absorbRemaining > 0.0F)
//            {
//                if (absorbRemaining == absorb && absorb % 2.0F == 1.0F)
//                {
//                    blit(mStack, x, y, MARGIN + 153, TOP, 9, 9); //17
//                    absorbRemaining -= 1.0F;
//                }
//                else
//                {
//                    blit(mStack, x, y, MARGIN + 144, TOP, 9, 9); //16
//                    absorbRemaining -= 2.0F;
//                }
//            }
//            else
//            {
//                if (i * 2 + 1 < health)
//                    blit(mStack, x, y, MARGIN + 36, TOP, 9, 9); //4
//                else if (i * 2 + 1 == health)
//                    blit(mStack, x, y, MARGIN + 45, TOP, 9, 9); //5
//            }
//        }
    }

    private void blitAttachHearts(@NotNull MatrixStack mStack, int health, Float healthMax, float absorb, int rowHeight, int left, int top, int regen, List<AttachHeart> attachHearts) {
        //从血量的最大值渲染
        for (int heartIndex = MathHelper.ceil((healthMax + absorb) / 2.0F) - 1; heartIndex >= 0; --heartIndex) {
            if (!(heartIndex < attachHearts.size())) continue;

            int row = getRow(heartIndex);
            int x = getX(left, heartIndex);
            int y = getY(rowHeight, top, row);

            //低血量抖动
            y = lowHealthShake(health, y);
            y = ifRegen(regen, heartIndex, y);

            blit(mStack, x, y, attachHearts.get(heartIndex).getUOffSet(), attachHearts.get(heartIndex).getVOffSet(), 9, 9);

//            if (highlight){
//                highLightBlit(mStack, healthLast, TOP, MARGIN, heartIndex, x, y);
//            }

//            if (hasAbsorb(absorbRemaining)) {
//                if (isHasHalfHeartLeft(absorb, absorbRemaining)) {
//                    absorbRemaining = blitHalfAbsorbHeart(mStack, TOP, MARGIN, absorbRemaining, x, y);
//                }
//                else {
//                    absorbRemaining = blitOneAbsorbHeart(mStack, TOP, MARGIN, absorbRemaining, x, y);
//                }
//            }
//            else {
//                if (isOneHeart(health, heartIndex))
//                    blit(mStack, x, y, MARGIN + 36, TOP, 9, 9); //4
//                else if (isHalfHeart(health, heartIndex))
//                    blit(mStack, x, y, MARGIN + 45, TOP, 9, 9); //5
//            }
        }
    }

    private static boolean isHalfHeart(int health, int heartIndex) {
        return heartIndex * 2 + 1 == health;
    }

    private static boolean isOneHeart(int health, int heartIndex) {
        return heartIndex * 2 + 1 < health;
    }

//    private static boolean hasAbsorb(float absorbRemaining) {
//        return absorbRemaining > 0.0F;
//    }
//
//    private static boolean isHasHalfHeartLeft(float absorb, float absorbRemaining) {
//        return absorbRemaining == absorb && absorb % 2.0F == 1.0F;
//    }
//
//    private float blitOneAbsorbHeart(@NotNull MatrixStack mStack, Integer TOP, int MARGIN, float absorbRemaining, int x, int y) {
//        blit(mStack, x, y, MARGIN + 144, TOP, 9, 9); //16
//        absorbRemaining -= 2.0F;
//        return absorbRemaining;
//    }
//
//    private float blitHalfAbsorbHeart(@NotNull MatrixStack mStack, Integer TOP, int MARGIN, float absorbRemaining, int x, int y) {
//        blit(mStack, x, y, MARGIN + 153, TOP, 9, 9); //17
//        absorbRemaining -= 1.0F;
//        return absorbRemaining;
//    }

    private static int getY(int rowHeight, int top, int row) {
        return top - row * rowHeight;
    }

    private static int getX(int left, int heartIndex) {
        return left + heartIndex % 10 * 8;
    }

    private static int getRow(int heartIndex) {
        return MathHelper.ceil((float)(heartIndex + 1) / 10.0F) - 1;
    }

    private static int countMargin(@NotNull PlayerEntity player) {
        int MARGIN = 16;
        if (player.hasEffect(Effects.POISON))      MARGIN += 36;
        else if (player.hasEffect(Effects.WITHER)) MARGIN += 72;
        return MARGIN;
    }

//    @Nullable
//    private Integer countTOP() {
//        if (minecraft.level == null) return null;
//        //极限模式的血量和普通的不一样，在贴图中的上下差了45个像素，这个top是用来偏移的
//        return 9 * (minecraft.level.getLevelData().isHardcore() ? 5 : 0);
//    }

    private int countRegen(@NotNull PlayerEntity player) {
        int regen = -1;
        if (player.hasEffect(Effects.REGENERATION)) {
            regen = tickCount % 25;
        }
        return regen;
    }

    private static void countLeftHeight(int healthRows, int rowHeight) {
        left_height += (healthRows * rowHeight);
    }

    private static int countTop(int height) {
        return height - left_height;
    }

    private static int countLeft(int width) {
        return width / 2 - 91;
    }

    private static int countRowHeight(int healthRows) {
        return Math.max(10 - (healthRows - 2), 3);
    }

    private static int countHealthRows(Float healthMax, float absorb) {
        return MathHelper.ceil((healthMax + absorb) / 2.0F / 10.0F);
    }

//    private int getHealthLast() {
//        return this.displayHealth;
//    }

    private void bindHeartIcon() {
        this.minecraft.getTextureManager().bind(HEART_ICON);
    }

    private static int getHealth(@NotNull PlayerEntity player) {
        return MathHelper.ceil(player.getHealth());
    }

//    private boolean willHighlight() {
//        return healthBlinkTime > (long)tickCount && (healthBlinkTime - (long)tickCount) / 3L %2L == 1L;
//    }

    private static float getAbsorb(@NotNull PlayerEntity player) {
        return (float) MathHelper.ceil(player.getAbsorptionAmount());
    }

    @Nullable
    private static Float getMaxHealth(@NotNull PlayerEntity player) {
        ModifiableAttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        if (attrMaxHealth == null) return null;
        return (float)attrMaxHealth.getValue();
    }

//    private void highLightBlit(@NotNull MatrixStack mStack, int healthLast, int TOP, int MARGIN, int heartIndex, int x, int y) {
//        if (isOneHeart(healthLast, heartIndex))
//            blit(mStack, x, y, MARGIN + 54, TOP, 9, 9); //6
//        else if (isHalfHeart(healthLast, heartIndex))
//            blit(mStack, x, y, MARGIN + 63, TOP, 9, 9); //7
//    }

    private static int ifRegen(int regen, int i, int y) {
        if (i == regen) y -= 2;
        return y;
    }

    private int lowHealthShake(int health, int y) {
        if (health <= 4) y += random.nextInt(2);
        return y;
    }

    @NotNull
    private static List<AttachHeart> getAttachHearts(@NotNull IPlayerCapability cap) {
        List<AttachHeart> attachHearts = new ArrayList<>();
        cap.getHearts().stream()
                .filter(h -> h instanceof AttachHeart)
                .forEach(h -> attachHearts.add((AttachHeart) h));
        return attachHearts;
    }

    @NotNull
    private static List<ExtraHeart> getExtraHearts(@NotNull IPlayerCapability cap) {
        List<ExtraHeart> extraHearts = new ArrayList<>();
        cap.getHearts().stream()
                .filter(h -> h instanceof ExtraHeart)
                .forEach(h -> extraHearts.add((ExtraHeart) h));
        return extraHearts;
    }
}
