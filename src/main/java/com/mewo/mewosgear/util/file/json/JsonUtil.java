package com.mewo.mewosgear.util.file.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mewo.mewosgear.Main;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;

import java.io.*;
import java.nio.file.Path;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void write(File targetFile, Object data) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetFile));
            String dat = gson.toJson(data);
            bufferedWriter.write(dat);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Main.LOGGER.error("Could not read file at {}", targetFile.getAbsolutePath());
        }
    }

    public static <T> T read(File targetFile, Class<T> clazz) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(targetFile));
            return gson.fromJson(bufferedReader, clazz);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Main.LOGGER.error("Could not write to file at {}", targetFile.getAbsolutePath());
        }
        return null;
    }

    public static File getWorldFilePath(MinecraftServer server) {
        Path path = server.getWorldPath(LevelResource.ROOT);
        return path.toFile();
    }
}
