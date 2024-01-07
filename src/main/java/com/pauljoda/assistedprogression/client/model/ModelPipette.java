package com.pauljoda.assistedprogression.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import com.pauljoda.assistedprogression.lib.Reference;
import com.pauljoda.nucleus.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.resources.model.ModelBakery.ModelBakerImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.RenderTypeGroup;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.model.*;
import net.neoforged.neoforge.client.model.geometry.*;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static net.neoforged.neoforge.client.model.DynamicFluidContainerModel.getLayerRenderTypes;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/6/2022
 */
@SuppressWarnings("ALL")
public class ModelPipette implements IUnbakedGeometry<ModelPipette> {

    /*******************************************************************************************************************
     * Variables                                                                                                       *
     *******************************************************************************************************************/

    private static final Transformation FLUID_TRANSFORM = new Transformation(new Vector3f(), new Quaternionf(), new Vector3f(1.0F, 1.0F, 1.002F), new Quaternionf());
    private static final Transformation COVER_TRANSFORM = new Transformation(new Vector3f(), new Quaternionf(), new Vector3f(1.0F, 1.0F, 1.004F), new Quaternionf());

    // Global
    // Reference to model location, used to get and wrap the default model
    public static final ModelResourceLocation LOCATION =
            new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "pipette"), "inventory");

    public static final ResourceLocation baseLocation
            = new ResourceLocation(Reference.MOD_ID, "item/pipette");

    // Reference to the texture for the mask/cover
    public static final ResourceLocation maskLocation
            = new ResourceLocation(Reference.MOD_ID, "item/pipette_mask");

    // Cache of generated models, no need to re-bake each cycle
    private static final Map<String, BakedModel> cache = new HashMap<>();

    // Local
    // Reference to stored fluid
    private Fluid fluid;

    // The base pipette model generated at load-time, does not contain mask or fluid
    private BakedModel baseModel;

    /**
     * Creates a model wrapped around the baked model, allows rendering of fluids, be sure to set the base after
     * creation
     *
     * @param fluid The fluid
     */
    public ModelPipette(@Nullable Fluid fluid) {
        this.fluid = fluid == null ? Fluids.EMPTY : fluid;
    }

    /*******************************************************************************************************************
     * IUnbakedModel                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Bakes the model with all relevant information, this is the "live" model
     *
     * @param bakery       Minecraft model bakery
     * @param spriteGetter Function to get textures
     *
     * @return A model baked with all info present
     */
    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker,
                           Function<Material, TextureAtlasSprite> spriteGetter,
                           ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
        // Setup Render Material
        Material baseMaterial =
                new Material(RenderUtils.MC_BLOCKS_RESOURCE_LOCATION, baseLocation);
        Material maskMaterial =
                new Material(RenderUtils.MC_BLOCKS_RESOURCE_LOCATION, maskLocation);

        // Sprites and quads initialization
        TextureAtlasSprite fluidSprite = fluid != Fluids.EMPTY ?
                spriteGetter.apply(ClientHooks.getBlockMaterial(IClientFluidTypeExtensions.of(fluid).getStillTexture())) :
                null;
        TextureAtlasSprite baseSprite = spriteGetter.apply(baseMaterial);
        TextureAtlasSprite particleSprite = spriteGetter.apply(maskMaterial);


        StandaloneGeometryBakingContext itemContext = StandaloneGeometryBakingContext.builder(context).withGui3d(false).withUseBlockLight(false).build(modelLocation);
        CompositeModel.Baked.Builder modelBuilder = CompositeModel.Baked.builder(itemContext, particleSprite, new PipetteOverrideList(baker, itemContext), context.getTransforms());
        RenderTypeGroup normalRenderTypes = getLayerRenderTypes(false);

        List<BlockElement> unbaked = UnbakedGeometryHelper.createUnbakedItemElements(0, baseSprite);
        List<BakedQuad> quads = UnbakedGeometryHelper.bakeElements(unbaked, ($) -> {
            return baseSprite;
        }, (ModelState)modelState, modelLocation);
        modelBuilder.addQuads(normalRenderTypes, quads);

        // We are going to use the mask as a stencil for the liquid
        TextureAtlasSprite liquidMask = spriteGetter.apply(maskMaterial);

        SimpleModelState transformedState;
        var sprite = (TextureAtlasSprite)spriteGetter.apply(maskMaterial);
        // Draw fluid
        if (fluidSprite != null && sprite != null) {
            transformedState = new SimpleModelState(((ModelState)modelState).getRotation().compose(FLUID_TRANSFORM), ((ModelState)modelState).isUvLocked());
            unbaked = UnbakedGeometryHelper.createUnbakedItemMaskElements(1, sprite);
            quads = UnbakedGeometryHelper.bakeElements(unbaked, ($) -> {
                return fluidSprite;
            }, transformedState, modelLocation);
            boolean emissive = this.fluid.getFluidType().getLightLevel() > 0;
            RenderTypeGroup renderTypes = getLayerRenderTypes(emissive);
            if (emissive) {
                QuadTransformers.settingMaxEmissivity().processInPlace(quads);
            }

            QuadTransformers.applyingColor(IClientFluidTypeExtensions.of(fluid).getTintColor()).processInPlace(quads);

            modelBuilder.addQuads(renderTypes, quads);
        }

        if(sprite != null) {
            // Draw mask
            // Draw rest of pipette, the clear cover over the fluid, do this by making an item with texture of mask
            List<BlockElement> unbakedMask = UnbakedGeometryHelper.createUnbakedItemElements(2, sprite);
            List<BakedQuad> quadsMask = UnbakedGeometryHelper.bakeElements(unbaked, ($) -> {
                return sprite;
            }, (ModelState) modelState, modelLocation);
            modelBuilder.addQuads(normalRenderTypes, quads);
        }

        // The fully processed model
        modelBuilder.setParticle(particleSprite);
        return modelBuilder.build();
    }

    /**
     * Set the base model to the provided, we use this to wrap the vanilla model
     *
     * @param base The vanilla model, no fluid or cover texture
     */
    public void setBaseModel(BakedModel base) {
        this.baseModel = base;
    }

    /**
     * Process the model, used when adding data to the model
     *
     * @param customData Custom info
     *
     * @return An instance of the model with custom data applied
     */
    public ModelPipette withFluid(ImmutableMap<String, String> customData) {
        Fluid fluid = BuiltInRegistries.FLUID.get(new ResourceLocation(customData.get("fluid")));

        if (fluid == null) fluid = this.fluid;

        // create new model with correct liquid
        return new ModelPipette(fluid);
    }

    /*******************************************************************************************************************
     * Override Handler                                                                                                *
     *******************************************************************************************************************/

    /**
     * Class to define model data to bake at runtime. This will take the actual stack, and build a new model based on
     * what fluid is inside
     */
    public static final class PipetteOverrideList extends ItemOverrides {

        // Public reference to an instance, we don't need a new one every time
        public static PipetteOverrideList INSTANCE;

        // Reference to the minecraft model bakery
        public final ModelBaker bakery;
        private final IGeometryBakingContext owner;

        /**
         * Creates the handler
         *
         * @param bakery The instance of the minecraft model bakery
         */
        public PipetteOverrideList(ModelBaker bakery, IGeometryBakingContext own) {
            this.bakery = bakery;
            this.owner = own;
        }

        /**
         * Gets a model with context info
         *
         * @param originalModel The orginal model, the one Minecraft is loading before you substitute
         * @param stack         The itemstack for the model
         * @param world         World
         * @param entity        Entity holding
         *
         * @return The new model to render instead, we will wrap the original add fluid info
         */
        @Override
        public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel world,
                                  @Nullable LivingEntity entity, int seed) {

            return FluidUtil.getFluidContained(stack)
                    .map(fluidStack -> {
                        Fluid fluid = fluidStack.getFluid();
                        String name = BuiltInRegistries.FLUID.getKey(fluid).toString();

                        if (!cache.containsKey(name)) {
                            ModelPipette parent = new ModelPipette(null).withFluid(ImmutableMap.of("fluid", name));
                            parent.setBaseModel(originalModel);

                            BakedModel bakedModel
                                    = parent.bake(owner, bakery,
                                    Material::sprite, BlockModelRotation.X0_Y0, this, LOCATION);
                            cache.put(name, bakedModel);
                            return bakedModel;
                        }

                        return cache.get(name);
                    })
                    // not a fluid item apparently
                    .orElse(originalModel); // empty pipette
        }
    }
    /*******************************************************************************************************************
     * Baked Model                                                                                                     *
     *******************************************************************************************************************/

    /**
     * The baked instance of this model
     * <p>
     * This will take in the original model created by Minecraft on load, and wrap around it. This is the model
     * to register in the loading stage, also has constructor that generates models at runtime based on info present
     */
    public static class PipetteDynamicModel extends BakedModelWrapper implements IDynamicBakedModel {

        // Model Bakery to pass into override
        private ModelBakery bakery;

        // Minecraft generated model
        public BakedModel baseModel;

        public IGeometryBakingContext owner;

        /**
         * Creates a simple wrapper around the vanilla model to reflect into our dynamic model
         *
         * @param modelBakery The instance of the model bakery
         * @param parent      The model made by Minecraft
         */
        public PipetteDynamicModel(ModelBakery modelBakery, BakedModel parent, IGeometryBakingContext owner) {
            super(parent);
            this.bakery = modelBakery;
            this.owner = owner;
        }

        /**
         * Used to define the override list, since we don't want just the vanilla one
         *
         * @return The instance of the override handler, creates it if not loaded
         */
        @Override
        @Nonnull
        public ItemOverrides getOverrides() {
            if (PipetteOverrideList.INSTANCE == null ||
                    PipetteOverrideList.INSTANCE.bakery == null) {
                BiFunction<ResourceLocation, Material, TextureAtlasSprite> spriteBiFunction = ((resourceLocation, material) ->
                        Minecraft.getInstance().getTextureAtlas(RenderUtils.MC_BLOCKS_RESOURCE_LOCATION).apply(resourceLocation));

                PipetteOverrideList.INSTANCE =
                        new PipetteOverrideList(
                                bakery.new ModelBakerImpl(spriteBiFunction, LOCATION), owner);
            }
            return PipetteOverrideList.INSTANCE;
        }
    }
}