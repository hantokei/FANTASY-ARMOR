package net.kenddie.fantasyarmorlite.entity.client.armor.render.lib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kenddie.fantasyarmorlite.item.armor.lib.FAArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class FAArmorRenderer<T extends FAArmorItem> extends GeoArmorRenderer<T> {
    protected GeoBone cape = null;
    protected GeoBone leftLegCloth = null;
    protected GeoBone rightLegCloth = null;

    public FAArmorRenderer(GeoModel<T> model) {
        super(model);
    }

    @Nullable
    public GeoBone getCapeBone(GeoModel<T> model) {
        return model.getBone("armorCape").orElse(null);
    }

    @Nullable
    public GeoBone getLeftLegClothBone(GeoModel<T> model) {
        return model.getBone("armorLeftLegCloth").orElse(null);
    }

    @Nullable
    public GeoBone getRightLegClothBone(GeoModel<T> model) {
        return model.getBone("armorRightLegCloth").orElse(null);
    }

    @Override
    protected void grabRelevantBones(BakedGeoModel bakedModel) {
        super.grabRelevantBones(bakedModel);

        GeoModel<T> model = getGeoModel();
        cape = getCapeBone(model);
        leftLegCloth = getLeftLegClothBone(model);
        rightLegCloth = getRightLegClothBone(model);
    }

    @Override
    protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
        super.applyBoneVisibilityBySlot(currentSlot);

        if(currentSlot == EquipmentSlot.CHEST) {
            setBoneVisible(cape, true);
            setBoneVisible(leftLegCloth, true);
            setBoneVisible(rightLegCloth, true);
        }
    }

    public void applyBoneVisibilityByPart(EquipmentSlot currentSlot, ModelPart currentPart, HumanoidModel<?> model) {
        super.applyBoneVisibilityByPart(currentSlot, currentPart, model);

        if(currentPart == model.body) {
            cape.setHidden(false);
            leftLegCloth.setHidden(false);
            rightLegCloth.setHidden(false);
        }
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        if(cape == null) {
            return;
        }

        if(currentEntity instanceof Player player) {
            FARenderUtils.applyCapeRotation(player, cape, partialTick);
        } else {
            cape.updateRotation((float) -Math.toRadians(5.0F), 0.0F, 0.0F);
        }
    }

    @Override
    protected void applyBaseTransformations(HumanoidModel<?> baseModel) {
        super.applyBaseTransformations(baseModel);

        if(cape != null) {
            ModelPart bodyPart = baseModel.body;

            cape.updatePosition(bodyPart.x, 1 - bodyPart.y, bodyPart.z);
        }

        if(leftLegCloth != null) {
            ModelPart leftLegPart = baseModel.leftLeg;

            RenderUtils.matchModelPartRot(leftLegPart, leftLegCloth);
            leftLegCloth.updatePosition(leftLegPart.x - 2, 12 - leftLegPart.y, leftLegPart.z);
        }

        if(rightLegCloth != null) {
            ModelPart rightLegPart = baseModel.rightLeg;

            RenderUtils.matchModelPartRot(rightLegPart, rightLegCloth);
            rightLegCloth.updatePosition(rightLegPart.x + 2, 12 - rightLegPart.y, rightLegPart.z);
        }
    }

    @Override
    public void setAllVisible(boolean pVisible) {
        super.setAllVisible(pVisible);

        setBoneVisible(cape, pVisible);
        setBoneVisible(leftLegCloth, pVisible);
        setBoneVisible(rightLegCloth, pVisible);
    }
}