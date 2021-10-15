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

public class Entity {
    private static final int INDEX_BITS = 23;
    private static final int INDEX_BITS_MASK = (1 << INDEX_BITS) - 1;
    private static final int GEN_BITS = 32 - INDEX_BITS;
    private static final int GEN_BITS_MASK = (1 << GEN_BITS) - 1;

    private int id;

    Entity(int index, int generation) {
        id = index | (generation << INDEX_BITS);
    }

    public int getIndex() {
       return id & INDEX_BITS_MASK;
    }

    public int getGeneration() {
        return (id >> INDEX_BITS) & GEN_BITS_MASK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return getIndex() == entity.getIndex();
    }

    @Override
    public int hashCode() {
        return getIndex();
    }
}
