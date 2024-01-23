package com.yyon.grapplinghook.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.IKeyConflictContext;
import org.jetbrains.annotations.NotNull;

public class NonConflictingKeyBinding extends KeyMapping {
	public NonConflictingKeyBinding(String description, int keyCode, String category) {
		super(description, keyCode, category);
		this.setNonConflict();
	}

	private void setNonConflict() {
		this.setKeyConflictContext(new IKeyConflictContext() {
			@Override
			public boolean isActive() {
				return true;
			}
			@Override
			public boolean conflicts(IKeyConflictContext other) {
				return false;
			}
		});
	}

	public NonConflictingKeyBinding(String description, InputConstants.Type type, int keyCode, String category) {
		super(description, type, keyCode, category);
		this.setNonConflict();
	}

   public boolean same(KeyMapping p_197983_1_) {
	   return false;
   }
   public boolean hasKeyCodeModifierConflict(KeyMapping other) {
	   return true;
   }
   
   public boolean isDown = false;
   
   public boolean isDown() {
	   return isDown;
   }

	@Override
	public boolean isActiveAndMatches(@NotNull InputConstants.Key keyCode) {
		return keyCode != InputConstants.UNKNOWN && keyCode.equals(getKey());
	}

	@Override
   public void setDown(boolean value) {
		 if (isDown == value) return;
		 System.out.println(isDown + " -> " + value);
	   this.isDown = value;
   }
}
