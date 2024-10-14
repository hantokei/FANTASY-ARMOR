package net.kenddie.fantasyarmorlite.item.armor;

import net.kenddie.fantasyarmorlite.entity.client.armor.model.lib.FAArmorModel;
import net.kenddie.fantasyarmorlite.entity.client.armor.render.lib.FAArmorRenderer;
import net.kenddie.fantasyarmorlite.item.armor.lib.FAArmorItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.List;

public class WanderingWizardArmorItem extends FAArmorItem {
    public WanderingWizardArmorItem(Type type) {
        super(type);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected GeoArmorRenderer<? extends FAArmorItem> createArmorRenderer() {
        return new FAArmorRenderer<>(new FAArmorModel<>(
                "geo/wandering_wizard_armor.geo.json",
                "textures/armor/wandering_wizard_armor.png"
        ));
    }
}
