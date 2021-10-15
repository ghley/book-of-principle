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

package dev.biblyon.essence.ecs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EntityManager {
    private List<Integer> generations = new ArrayList<>();
    private LinkedList<Integer> freeIndices = new LinkedList<>();

    public boolean isValid(Entity entity) {
        return generations.get(entity.getIndex()) == entity.getGeneration();
    }

    public Entity createEntity() {
        int index;

        if (freeIndices.size() > 1000) {
            index = freeIndices.pop();
        }else {
            generations.add(0);

            index = generations.size()-1;
        }

        return new Entity(index, generations.get(index));
    }

    public void destroyEntity(Entity entity) {
        int index = entity.getIndex();
        generations.set(index, generations.get(index)+1);
        freeIndices.addLast(index);
    }
}
