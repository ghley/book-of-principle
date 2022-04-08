package dev.biblyon.principle.ecs;

import java.util.*;

public class World {
    private int bit = 0;

    private long entityId = 0;
    private TreeSet<Long> destroyedEntities = new TreeSet<>();

    private Map<Class<? extends Component>, Integer> componentBit = new HashMap<>();
    private Map<Class<? extends AbstractSystem>, AbstractSystem> systems = new HashMap<>();

    public int getComponentBit(Class<? extends Component> clazz) {
        registerComponent(clazz);
        return componentBit.get(clazz);
    }

    void registerComponent(Class<? extends Component> clazz) {
        if (!componentBit.containsKey(clazz)) {
            componentBit.put(clazz, bit++);
        }
    }

    public void updateMask(Entity entity) {
        for (AbstractSystem system : systems.values()) {
            if (system.matchesMask(entity)) {
                system.addEntity(entity);
            }
        }
    }

    public Entity createEntity() {
        if (!destroyedEntities.isEmpty()) {
            return new Entity(this, destroyedEntities.pollFirst());
        }
        return new Entity(this, entityId++);
    }

    public void destroyEntity(Entity entity) {
        destroyedEntities.add(entity.id);
        for (var s : systems.values()) {

        }
    }

    public void registerSystem(Class<? extends AbstractSystem> clazz) {
        try {
            var system = clazz.getConstructor().newInstance();
            system.setWorld(this);
            system.initMask();
            systems.put(clazz, system);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    <T extends AbstractSystem> T getSystem(Class<T> clazz) {
        return (T) systems.get(clazz);
    }

    public void update() {
        for (var system : systems.values()) {
            system.update();
        }
    }

    public void resetComponents() {
        bit = 0;
        componentBit.clear();
    }

}
