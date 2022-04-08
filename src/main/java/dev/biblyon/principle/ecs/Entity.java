package dev.biblyon.principle.ecs;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Entity {
    private final World world;
    final long id;
    final BitSet mask = new BitSet();
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();

    Entity(World world, long id) {
        this.world = world;
        this.id = id;
    }

    public  void addComponent(Class<? extends Component>... clazz) {
        for (var c : clazz) {
            if (!components.containsKey(clazz)) {
                if (!components.containsKey(c)) {
                    try {
                        components.put(c, c.getConstructor().newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mask.set(world.getComponentBit(c));
                world.updateMask(this);
            }
        }
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        return (T)components.getOrDefault(clazz, null);
    }

    public long getId() {
        return id;
    }

    public BitSet getMask() {
        return mask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
