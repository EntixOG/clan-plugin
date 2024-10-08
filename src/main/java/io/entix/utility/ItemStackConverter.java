package io.entix.utility;

import it.unimi.dsi.fastutil.io.FastByteArrayInputStream;
import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.annotation.Nullable;

@UtilityClass
public class ItemStackConverter {

    public @Nullable String encode(@NonNull ItemStack itemStack) {
        try (FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            dataOutput.writeObject(itemStack);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.array);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public @Nullable ItemStack decode(@NonNull String base64) {
        try (FastByteArrayInputStream inputStream = new FastByteArrayInputStream(Base64Coder.decodeLines(base64));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            ItemStack itemStack = (ItemStack) dataInput.readObject();
            dataInput.close();
            return itemStack;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}