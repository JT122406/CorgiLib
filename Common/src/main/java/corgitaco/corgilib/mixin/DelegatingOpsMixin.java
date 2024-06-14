package corgitaco.corgilib.mixin;

import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.RecordBuilder;
import net.minecraft.resources.DelegatingOps;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DelegatingOps.class)
public class DelegatingOpsMixin<T> {

    @Shadow
    @Final
    public DynamicOps<T> delegate;

    /**
     * @author Corgi Taco
     * @reason Use delegate's list builder
     */
    @Overwrite
    public ListBuilder<T> listBuilder() {
        return this.delegate.listBuilder();
    }

    /**
     * @author Corgi Taco
     * @reason Use delegate's map builder
     */
    @Overwrite
    public RecordBuilder<T> mapBuilder() {
        return this.delegate.mapBuilder();
    }
}