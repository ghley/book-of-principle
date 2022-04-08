package dev.biblyon.principle.ecs;

import java.util.BitSet;
import java.util.LinkedHashSet;

public abstract class AbstractSystem {
    private World world;
    private final BitSet mask = new BitSet();
    protected final LinkedHashSet<Entity> entities = new LinkedHashSet<>();

    public AbstractSystem() {

    }

    void setWorld(World world) {
        this.world = world;
    }

    void initMask() {
        for (var c : requiredComponents()) {
            mask.set(world.getComponentBit(c));
        }
    }

    public abstract Class<? extends Component>[] requiredComponents();

    public BitSet getMask() {
        return mask;
    }

    public abstract void update();

    public boolean matchesMask(Entity entity) {
        var mask1 = mask.toLongArray();
        var mask2 = entity.mask.toLongArray();
        if (mask1.length > mask2.length) {
            return false;
        }
        for (int q = 0; q < mask1.length; q++) {
            if ((mask1[q] & mask2[q]) != mask1[q]) {
                return false;
            }
        }
        return true;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
}
