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
				createShaped(RecipeCategory.DECORATIONS, Blocks.CANDLE_TRAY.asItem())
						.pattern("ici")
						.pattern(" i ")
						.input('i', Items.IRON_INGOT)
						.input('c', Items.CHAIN)
						.group("multi_bench")
						.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
						.offerTo(recipeExporter);
				createShaped(RecipeCategory.DECORATIONS, Blocks.ITEM_STAND.asItem())
						.pattern("ici")
						.pattern(" i ")
						.pattern("iii")
						.input('i', Items.IRON_INGOT)
						.input('c', Items.CHAIN)
						.group("multi_bench")
						.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
						.offerTo(recipeExporter);
			}
		};
	}

	@Override
	public String getName() {
		return "";
	}
}
