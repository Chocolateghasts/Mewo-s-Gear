package com.mewo.mewosgear.dataGen;

import com.mewo.mewosgear.Main;
import com.mewo.mewosgear.content.modifiers.IModifier;
import com.mewo.mewosgear.content.registry.ModModifiers;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AutoModelTextureProvider implements DataProvider {
    private final PackOutput output;

    public AutoModelTextureProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (Map.Entry<String, IModifier> entry : ModModifiers.modifiers.entrySet()) {
            String name = entry.getKey();
            IModifier modifier = entry.getValue();
            int color = modifier.getColor();
            BufferedImage image = generateModifierTexture(color);
            Path root = Paths.get("").toAbsolutePath().getParent();
            Path tempFile = null;
            Path targetDir = root.resolve("src/main/resources/assets/" + Main.MOD_ID + "/textures/item/modifiers/");
            try {
                if (!Files.exists(targetDir)) {
                    Files.createDirectories(targetDir);
                }

                tempFile = Files.createTempFile(targetDir, "tmp", ".png");
                ImageIO.write(image, "png", tempFile.toFile());

                Path finalPath = targetDir.resolve(name+ ".png");
                Files.move(tempFile, finalPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                tempFile = null;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (tempFile != null && Files.exists(tempFile)) {
                    try {
                        Files.deleteIfExists(tempFile);
                    } catch (IOException ignored) {}
                }
            }
        }



        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    public static BufferedImage generateModifierTexture(int color) {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(color));
        g.fillRect(0, 0, 16, 16);
        g.dispose();
        return img;
    }

    @Override
    public String getName() {
        return "Modifier Textures";
    }
}
