package com.rappytv.headowner.util;

import com.mojang.authlib.properties.Property;
import com.rappytv.headowner.HeadOwnerAddon;
import net.labymod.core.LabyModCore;
import net.labymod.utils.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

import java.util.Iterator;
import java.util.UUID;

public class Util {

    public static Skull getSkullLooking(){
        TileEntity tileEntity = getTileEntityLooking();

        return new Skull(tileEntity);
    }

    private static TileEntity getTileEntityLooking(){
        try {
            if(LabyModCore.getMinecraft().getPlayer() == null) return null;
            BlockRayTraceResult movingObjectPosition = (BlockRayTraceResult) Minecraft.getInstance().player.pick(HeadOwnerAddon.length, 1.0F, false);
            if (movingObjectPosition == null){
                return null;
            }

            BlockPos blockPos = movingObjectPosition.getPos();
            if (blockPos == null){
                return null;
            }

            return LabyModCore.getMinecraft().getWorld().getTileEntity(blockPos);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static class Skull {

        private TileEntityType type = TileEntityType.SKULL;

        private String username = null;
        private UUID uuid = null;
        private String value = null;

        public Skull(TileEntity tileEntity){
            if(!(tileEntity instanceof SkullTileEntity))
                return;

            SkullTileEntity tileEntitySkull = (SkullTileEntity) tileEntity;
            this.type = tileEntitySkull.getType();

            if(tileEntitySkull.getPlayerProfile() != null){
                this.username = tileEntitySkull.getPlayerProfile().getName();
                this.uuid = tileEntitySkull.getPlayerProfile().getId();

                Iterator<Property> propertyIterator = tileEntitySkull.getPlayerProfile().getProperties().get("textures").iterator();
                while(propertyIterator.hasNext()){
                    this.value = propertyIterator.next().getValue();
                }
            }
        }

        private String getSkullTypeName() {
            if(this.type.equals(TileEntityType.SKULL)) return "Skull";
            else return "Unknown Skull Type: " + this.type;
        }

        public String getDisplay(){
            if(this.username != null)
                return this.username;

            if(this.value != null) {
                if(!HeadOwnerAddon.copyCmd.equalsIgnoreCase(""))
                    return "Unknown Head (created by texture value, type /" + HeadOwnerAddon.copyCmd + " to copy skull data)";

                return "Unknown Head (created by texture value)";
            }

            return getSkullTypeName();
        }

        public String getCopy(){
            String uuid = this.uuid == null ? "Unknown" : this.uuid.toString();
            String username = this.username == null ? "Unknown" : this.username;
            String value = this.value == null ? "Unknown" : this.value;
            String type = getSkullTypeName();

            return "Skull type: " + type + " (" + this.type + ")" +
                    ", " +
                    "Username: " + username +
                    ", " +
                    "UUID: " + uuid +
                    ", " +
                    "Texture value: " + value;
        }

        public boolean isShown() {
            return (type == TileEntityType.SKULL);
        }
    }
}
