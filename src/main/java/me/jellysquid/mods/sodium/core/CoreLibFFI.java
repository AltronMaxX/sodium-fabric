package me.jellysquid.mods.sodium.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
public class CoreLibFFI {
    static native void setPanicHandler(long ptr);

    static native void setAllocator(long pAllocatorPfn);

    static native void graphCreate(long ppGraph);

    static native void graphAddChunk(long pGraph, int x, int y, int z);
    static native void graphUpdateChunk(long pGraph, int x, int y, int z, long pNode);
    static native void graphRemoveChunk(long pGraph, int x, int y, int z);
    static native void graphSearch(long pGraph, long pFrustum, int viewDistance, long pResults);

    static native void graphDelete(long pGraph);

    static native void frustumCreate(long ppFrustum, long ppPoints, long pOffset);

    static native void frustumDelete(long pFrustum);

    static {
        loadLib();
    }

    public static void loadLib() {
        try {
            CoreLibFFI.load("sodium_core", true);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Loads a native library from the resources of this Jar
     *
     * @param name           Library to load
     * @param forceOverwrite Force overwrite the library file
     * @throws IOException               Cannot move library out of Jar
     */
    public static void load(String name, boolean forceOverwrite) throws IOException {
        name = System.mapLibraryName(name);
        File libDir = new File("lib");
        if (!libDir.exists()) libDir.mkdirs();
        File object = new File("lib", name);
        if (forceOverwrite || !object.exists()) {
            InputStream is = CoreLibFFI.class.getClassLoader().getResourceAsStream("META-INF/natives/" + name);
            if (is == null) throw new IOException("Could not find lib " + name + " in jar", null);

            Files.copy(is, object.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        System.load(object.getAbsolutePath());
    }

}
