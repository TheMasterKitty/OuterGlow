package themasterkitty.outerglow;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.controllers.BooleanController;
import dev.isxander.yacl3.gui.controllers.ColorController;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;

import java.awt.*;

public class Main implements ModMenuApi, ClientModInitializer {
    @SerialEntry
    public static boolean enabled = true;
    @SerialEntry
    public static Color color = new Color(0xFFFFFF);

    @Override
    public void onInitializeClient() {
        CONFIG.load();
    }

    public static final ConfigClassHandler<Main> CONFIG = ConfigClassHandler.createBuilder(Main.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("outerglow.json"))
                    .build())
            .build();
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent ->
                YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) ->
                        builder
                                .save(CONFIG::save)
                                .title(Text.literal("OuterGlow Settings"))
                                .category(ConfigCategory.createBuilder()
                                        .name(Text.literal("Settings"))
                                        .option(Option.<Boolean>createBuilder()
                                                .name(Text.literal("Enabled"))
                                                .description(OptionDescription.of(Text.literal("Enable the OuterGlow mod / outlines")))
                                                .binding(enabled, () -> enabled, value -> enabled = value)
                                                .customController(opt -> new BooleanController(opt, true))
                                                .build())
                                        .option(Option.<Color>createBuilder()
                                                .name(Text.literal("Glow Color"))
                                                .description(OptionDescription.of(Text.literal("Change the color shown around players' heads")))
                                                .binding(color, () -> color, value -> color = value)
                                                .customController(opt -> new ColorController(opt, true))
                                                .build())
                                        .build())
                )).generateScreen(parent);
    }
}
