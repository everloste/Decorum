package dev.bebebea_loste.decorum.client.generators;

import dev.bebebea_loste.decorum.registries.Blocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {
	public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
		return new RecipeGenerator(wrapperLookup, recipeExporter) {
			@Override
			public void generate() {
				RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
				createShaped(RecipeCategory.DECORATIONS, Blocks.BAMBOO_FLOWER_POT.asItem())
						.pattern("b b")
						.pattern(" b ")
						.input('b', Items.BAMBOO)
						.group("multi_bench")
						.criterion(hasItem(Items.BAMBOO), conditionsFromItem(Items.BAMBOO))
						.offerTo(recipeExporter);
				createShaped(RecipeCategory.DECORATIONS, Blocks.TORCH_HOLDER.asItem())
						.pattern(" c ")
						.pattern("iii")
						.pattern(" c ")
						.input('i', Items.IRON_NUGGET)
						.input('c', Items.CHAIN)
						.group("multi_bench")
						.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
						.offerTo(recipeExporter);
				createShaped(RecipeCategory.DECORATIONS, Blocks.ITEM_STAND.asItem())
						.pattern("ici")
						.pattern(" i ")
						.pattern("iii")
						.input('i', Items.IRON_NUGGET)
						.input('c', Items.CHAIN)
						.group("multi_bench")
						.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
						.offerTo(recipeExporter);
//				createShaped(RecipeCategory.DECORATIONS, Blocks.CANDLE_HOLDER.asItem())
//						.pattern("ici")
//						.pattern(" i ")
//						.input('i', Items.IRON_NUGGET)
//						.input('c', Items.IRON_INGOT)
//						.group("multi_bench")
//						.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
//						.offerTo(recipeExporter);

				createShaped(RecipeCategory.DECORATIONS, Blocks.FIREFLY_LANTERN.asItem())
						.pattern("iii")
						.pattern("ici")
						.pattern("iii")
						.input('i', Items.IRON_NUGGET)
						.input('c', Items.FIREFLY_BUSH)
						.group("multi_bench")
						.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
						.offerTo(recipeExporter);

				createShapeless(RecipeCategory.DECORATIONS, Blocks.PRIDE_CANDLE.asItem())
						.input(Items.CANDLE)
						.input(Items.RED_DYE)
						.input(Items.YELLOW_DYE)
						.input(Items.BLUE_DYE)
						.group("multi_bench")
						.criterion(hasItem(Items.CANDLE), conditionsFromItem(Items.CANDLE))
						.offerTo(recipeExporter);
				createShapeless(RecipeCategory.DECORATIONS, Blocks.TRANS_PRIDE_CANDLE.asItem())
						.input(Items.CANDLE)
						.input(Items.PINK_DYE)
						.input(Items.LIGHT_BLUE_DYE)
						.input(Items.WHITE_DYE)
						.group("multi_bench")
						.criterion(hasItem(Items.CANDLE), conditionsFromItem(Items.CANDLE))
						.offerTo(recipeExporter);
				createShapeless(RecipeCategory.DECORATIONS, Blocks.LESBIAN_PRIDE_CANDLE.asItem())
						.input(Items.CANDLE)
						.input(Items.MAGENTA_DYE)
						.input(Items.RED_DYE)
						.input(Items.WHITE_DYE)
						.group("multi_bench")
						.criterion(hasItem(Items.CANDLE), conditionsFromItem(Items.CANDLE))
						.offerTo(recipeExporter);
			}
		};
	}

	@Override
	public String getName() {
		return "";
	}
}
