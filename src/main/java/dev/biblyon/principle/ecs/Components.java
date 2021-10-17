/*
 * Book of Essence
 * Copyright (C) 2021 Ghley
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.
 */

package dev.biblyon.principle.ecs;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Components<T extends Component> {
    private Class<T> clazz;
    private int numComponents = 0;
    private int numAllocatedComponents = 0;
    private int sleepingIndex = 0;
    private Map<Entity, Integer> indexMap = new HashMap<>();
    private Entity[] entities = new Entity[0];
    private T[] components;

    public Components(Class<T> clazz) {
        this(clazz, 4);
    }

    public Components(Class<T> clazz, int preAllocate) {
        this.clazz = clazz;
        components = (T[]) Array.newInstance(clazz, 0);
        allocate(preAllocate);
    }

    public void addComponent(Entity entity, T data, boolean isSleeping) {
        int index = prepareAndGetIndex(isSleeping);
        if (components[index] == null) {
            components[index] = createInstance();
        }
        components[index].set(data);
        entities[index] = entity;
        indexMap.put(entity, index);
        numComponents++;
    }

    public void removeComponent(Entity entity) {
        int index = indexMap.get(entity);
        destroyComponent(index);

        if (index >= sleepingIndex) {
            if (numComponents - 1 != index) {
                moveComponent(numComponents - 1, index);
            }
        }else {
            if (index != sleepingIndex - 1) {
                moveComponent(sleepingIndex - 1, index);
            }
            if (sleepingIndex != numComponents) {
                moveComponent(numComponents - 1, sleepingIndex - 1);
            }
            sleepingIndex--;
        }
        numComponents--;
    }

    public boolean isSleeping(Entity entity) {
        return indexMap.get(entity) >= sleepingIndex;
    }

    public void setSleeping(Entity entity, boolean sleeping) {
        if (isSleeping(entity) == sleeping) {
            return;
        }
        int index = indexMap.get(entity);
        if (sleeping) {
            swapComponent(index, sleepingIndex - 1);
            sleepingIndex--;
        }else {
            swapComponent(index, sleepingIndex);
            sleepingIndex++;
        }
    }

    private void allocate(int newSize) {
        components = Arrays.copyOf(components, newSize);
        entities = Arrays.copyOf(entities, newSize);
        numAllocatedComponents = newSize;
    }

    public T getComponent(Entity entity) {
        assert indexMap.containsKey(entity);

        return getComponent(indexMap.get(entity));
    }

    public T getComponent(int index) {
        return components[index];
    }



    private void swapComponent(int index1, int index2) {
        Entity tempEntity = entities[index1];
        T tempComponent = components[index1];

        destroyComponent(index1);

        moveComponent(index2, index1);

        entities[index2] = tempEntity;
        components[index2].set(tempComponent);
        indexMap.put(tempEntity, index2);
    }

    private void moveComponent(int from, int to) {
        entities[to] = entities[from];
        if (components[to] == null) {
            components[to] = createInstance();
        }
        components[to].set(components[from]);
        destroyComponent(from);
        indexMap.put(entities[to], to);
    }

    private void destroyComponent(int index) {
        indexMap.remove(entities[index]);
        entities[index] = null;
    }

    private T createInstance() {
        try {
            return (T)clazz.getConstructor().newInstance();
        }catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public Entity[] getEntities() {
        return entities;
    }

    public int getNumComponents() {
        return numComponents;
    }

    public int getSleepingIndex() {
        return sleepingIndex;
    }

    private int prepareAndGetIndex(boolean isSleeping) {
        if (numAllocatedComponents == numComponents) {
            allocate(numAllocatedComponents * 2);
        }

        int index = -1;

        if (isSleeping) {
            index = numComponents;
        }else {
            if (numComponents != sleepingIndex) {
                moveComponent(sleepingIndex, numComponents);
            }
            index = sleepingIndex;
            sleepingIndex++;
        }
        return index;
    }

}
