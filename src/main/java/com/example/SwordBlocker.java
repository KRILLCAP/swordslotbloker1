package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.world.item.SwordItem;

public class SwordBlocker implements ClientModInitializer {
    private int lastSlot = -1;
    private boolean wasSwinging = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            var p = client.player;
            if (p == null) return;

            int slot = p.getInventory().selectedSlot;
            boolean hasSword = p.getMainHandItem().getItem() instanceof SwordItem;
            boolean swinging = p.handSwinging;

            if (hasSword && wasSwinging && !swinging && lastSlot != slot) {
                p.getInventory().selectedSlot = lastSlot;
            }

            wasSwinging = swinging;
            lastSlot = p.getInventory().selectedSlot;
        });
    }
}
