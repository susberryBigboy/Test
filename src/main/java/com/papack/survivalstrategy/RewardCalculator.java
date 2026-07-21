package com.papack.survivalstrategy;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.Optional;

public class RewardCalculator {

    public static double calculateRewardPoints(LivingEntity mob) {
        if (mob == null) {
            return 0.0;
        }

        // healthValue. mobの体力 / 20 (最大体力)
        double healthValue = mob.getMaxHealth() / 20.0;

        // base. 定数 3
        double base = 3.0;

        // メインハンドのアイテムを取得
        ItemStack mainHandItem = mob.getMainHandItem();

        // weaponValue. 武器の基礎攻撃力
        double weaponValue = 0.0;
        if (!mainHandItem.isEmpty()) {
            if (mainHandItem.getItem() instanceof ProjectileWeaponItem) {
                weaponValue = 6.0;
            } else {
                weaponValue = getBaseItemAttackDamage(mainHandItem);
            }
        }

        // enchantValue. エンチャントレベルに応じた加算 (0.15 * レベル)
        int sharpnessLevel = getEnchantmentLevel(mob, mainHandItem, Enchantments.SHARPNESS);
        int powerLevel = getEnchantmentLevel(mob, mainHandItem, Enchantments.POWER);
        int totalEnchantLevel = sharpnessLevel + powerLevel;
        double enchantValue = 0.15 * totalEnchantLevel;

        // armorValue. Mobの合計防御力を取得（装備＋Mob独自の属性値）
        double armorValue = mob.getAttributeValue(Attributes.ARMOR);

        // 防御力ボーナス（例: 防御力 10 あたり +0.3 相当の加算など調整可能）
        double armorBonus = armorValue / 10.0;

        // 計算式: healthValue * (1 + (weaponValue / base + enchantValue + armorBonus) * 0.3)
        return healthValue * (1 + (weaponValue / base + enchantValue + armorBonus) * 0.3);
    }

    /**
     * アイテム単体の基礎攻撃力（Attributes.ATTACK_DAMAGE）を取得
     */
    private static double getBaseItemAttackDamage(ItemStack stack) {
        if (stack.isEmpty()) return 0.0;

        // DataComponents からデフォルトの属性修飾子リストを取得
        ItemAttributeModifiers attributeModifiers = stack.getItem()
                .components()
                .getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);

        for (ItemAttributeModifiers.Entry entry : attributeModifiers.modifiers()) {
            // entry.attribute() (Holder<Attribute>) と Attributes.ATTACK_DAMAGE (Holder<Attribute>) を直接比較
            // または unwrapKey() で ResourceKey を検証
            if (entry.attribute().equals(Attributes.ATTACK_DAMAGE) ||
                    entry.attribute().unwrapKey().filter(key -> key.equals(Attributes.ATTACK_DAMAGE.unwrapKey().orElse(null))).isPresent()) {
                return entry.modifier().amount();
            }
        }
        return 0.0;
    }

    /**
     * コンポーネントデータからエンチャントレベルを取得するヘルパー
     */
    private static int getEnchantmentLevel(LivingEntity entity, ItemStack stack, net.minecraft.resources.ResourceKey<Enchantment> enchantmentKey) {
        if (stack.isEmpty()) return 0;

        // RegistryAccess から ENCHANTMENT レジストリを取得
        Registry<Enchantment> enchantmentRegistry = entity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        // ResourceKey から Holder.Reference<Enchantment> を取得
        Optional<Holder.Reference<Enchantment>> enchantmentHolder = enchantmentRegistry.get(enchantmentKey);

        if (enchantmentHolder.isPresent()) {
            ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
            return enchantments.getLevel(enchantmentHolder.get());
        }
        return 0;
    }
}