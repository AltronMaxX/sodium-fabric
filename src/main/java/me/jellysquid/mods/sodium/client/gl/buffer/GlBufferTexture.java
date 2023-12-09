package me.jellysquid.mods.sodium.client.gl.buffer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.jellysquid.mods.sodium.client.gl.device.CommandList;
import org.lwjgl.opengl.GL46;

import java.nio.ByteBuffer;

public class GlBufferTexture {
    private final GlContinuousUploadBuffer buffer;

    private final int glTexHandle;

    private final int textureNum;

    public GlBufferTexture(CommandList commandList, int textureNum) {
        this.buffer = new GlContinuousUploadBuffer(commandList);
        this.glTexHandle = GlStateManager._genTexture();
        this.textureNum = textureNum;
    }

    public int getTextureNum() {
        return textureNum;
    }

    public void putData(CommandList commandList, ByteBuffer data, int size) {
        this.buffer.uploadOverwrite(commandList, data, size);
    }

    public void bind() {
        GL46.glBindTexture(GL46.GL_TEXTURE_BUFFER, this.glTexHandle);
        GL46.glTexBuffer(GL46.GL_TEXTURE_BUFFER, GL46.GL_R32UI, this.buffer.getObjectHandle());
        RenderSystem.setShaderTexture(this.textureNum, this.glTexHandle);
    }
}
