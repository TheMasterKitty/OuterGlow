package themasterkitty.outerglow;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class IMixinPlugin implements IMixinConfigPlugin {
    private static boolean feather, lunar;
    private enum MinecraftVersion {
        MC1202,
        MC1211,
        MC1213
    }
    private static MinecraftVersion mcver;

    @Override
    public void onLoad(String mixinPackage) {
        FabricLoader loader = FabricLoader.getInstance();

        feather = loader.getModContainer("feather").isPresent() || loader.getModContainer("feather-loader").isPresent();

        try {
            Class.forName("com.moonsworth.client.javafx.MicrosoftAuthApp");
            lunar = true;
        }
        catch (Exception e) {
            lunar = false;
        }

        try {
            Version ver = loader.getModContainer("minecraft").orElseThrow().getMetadata().getVersion();
            if (ver.compareTo(Version.parse("1.21.3")) >= 0) mcver = MinecraftVersion.MC1213;
            else if (ver.compareTo(Version.parse("1.21.1")) >= 0) mcver = MinecraftVersion.MC1211;
            else if (ver.compareTo(Version.parse("1.20.2")) >= 0) mcver = MinecraftVersion.MC1202;
        }
        catch (Exception ignored) { }
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("NoLF")) return !feather && !lunar;

        if (mixinClassName.contains("1213")) return mcver == MinecraftVersion.MC1213;
        if (mixinClassName.contains("1211")) return mcver == MinecraftVersion.MC1211;
        if (mixinClassName.contains("1202")) return mcver == MinecraftVersion.MC1202;

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
}