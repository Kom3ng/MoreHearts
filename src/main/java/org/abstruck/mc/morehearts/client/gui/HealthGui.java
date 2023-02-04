package org.abstruck.mc.morehearts.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import javafx.util.Pair;
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
import java.util.Random;
import java.util.logging.Logger;

@OnlyIn(Dist.CLIENT)
public class HealthGui extends AbstractGui {
    private static final ResourceLocation HEART_ICON = new ResourceLocation(ModUtil.MOD_ID,"textures/gui/hearts.png");
    public static HealthGui INSTANCE = new HealthGui(Minecraft.getInstance().gui);

    public static int left_height = 39;
    public static int right_height = 39;
    private Minecraft minecraft;
    private IngameGui gui;
    public Random random = new Random();
    public List<Pair<Integer,Integer>> heartPos;

    public HealthGui(IngameGui gui){
        this.minecraft = Minecraft.getInstance();
        this.gui = gui;
        heartPos = new ArrayList<>();
    }

    public void render(@NotNull MatrixStack mStack){
        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = minecraft.getWindow().getGuiScaledHeight();

        PlayerEntity player = (PlayerEntity)this.minecraft.getCameraEntity();
        if (player == null) return;

        player.getCapability(ModCapability.PLAYER_CAP).ifPresent((cap) -> {
            RenderSystem.color4f(1.0f,1.0f,1.0f,1.0f);
            bindHeartIcon();

            left_height = 39;
            right_height = 39;

            //玩家当前血量
            int health = getHealth(player);

            //获取最大血量
            Float healthMax = getMaxHealth(player);
            if (healthMax == null) return;
            //获取伤害吸收增加的心
            float absorb = getAbsorb(player);

            //心的排数
            int healthRows = countHealthRows(healthMax, absorb);
            //每排的高度
            int rowHeight = countRowHeight(healthRows);

            //血量的最左边
            int left = countLeft(width);
            //血亮的最高处高度
            int top = countTop(height);
            //血量左侧的高度
            countLeftHeight(healthRows, rowHeight);

            //待分析
            if (rowHeight != 10) left_height += 10 - rowHeight;

            int regen = countRegen(player);
            //中毒和凋零效果导致的血量样子的改变
            int MARGIN = countMargin(player);

            List<ExtraHeart> extraHearts = getExtraHearts(cap);
            List<AttachHeart> attachHearts = getAttachHearts(cap);


            int extraHeartsRows = MathHelper.ceil(extraHearts.size() / 10.0F);
            int extraHeartRowHeight = countRowHeight(extraHeartsRows);
            int extraHeartsTop = top - rowHeight * healthRows;


            ListUtil.reverseForEachWithIndex(extraHearts, (index, extraHeart) -> {
                int row = getRow(index);
                int x = getX(left,index);
                int y = getY(extraHeartRowHeight, extraHeartsTop, row);

                blit(mStack,x,y,extraHeart.getUOffSet(),extraHeart.getVOffSet(),9,9,256,256);
            });

            blitAttachHearts(mStack, health, rowHeight, left, top, regen, attachHearts);

        });
    }

    private void blitAttachHearts(@NotNull MatrixStack mStack, int health,  int rowHeight, int left, int top, int regen, @NotNull List<AttachHeart> attachHearts) {
        //从血量的最大值渲染
        for (int heartIndex = attachHearts.size() - 1; heartIndex >= 0; --heartIndex) {

            int row = getRow(heartIndex);
            int x = getX(left, heartIndex);
            int y = getY(rowHeight, top, row);

            //低血量抖动
            y = lowHealthShake(health, y);
            y = ifRegen(regen, heartIndex, y);

            blit(mStack, x, y, attachHearts.get(heartIndex).getUOffSet(), attachHearts.get(heartIndex).getVOffSet(), 9, 9,256,256);
        }
    }

    private static boolean isHalfHeart(int health, int heartIndex) {
        return heartIndex * 2 + 1 == health;
    }

    private static boolean isOneHeart(int health, int heartIndex) {
        return heartIndex * 2 + 1 < health;
    }

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

    private int countRegen(@NotNull PlayerEntity player) {
        int regen = -1;
        if (player.hasEffect(Effects.REGENERATION)) {
            regen = gui.getGuiTicks() % 25;
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

    private void bindHeartIcon() {
        this.minecraft.getTextureManager().bind(HEART_ICON);
    }

    private static int getHealth(@NotNull PlayerEntity player) {
        return MathHelper.ceil(player.getHealth());
    }

    private static float getAbsorb(@NotNull PlayerEntity player) {
        return (float) MathHelper.ceil(player.getAbsorptionAmount());
    }

    @Nullable
    private static Float getMaxHealth(@NotNull PlayerEntity player) {
        ModifiableAttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        if (attrMaxHealth == null) return null;
        return (float)attrMaxHealth.getValue();
    }

    private static int ifRegen(int regen, int i, int y) {
        if (i == regen) y -= 2;
        return y;
    }

    private int lowHealthShake(int health, int y) {
        if (health <= 4) y +=  random.nextInt(2);
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
