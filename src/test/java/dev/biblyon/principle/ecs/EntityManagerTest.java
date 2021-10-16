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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntityManagerTest {

    private EntityManager manager;

    @BeforeEach
    public void before() {
        manager = new EntityManager();
    }

    @Test
    public void createEntity() {
        var entity1 = manager.createEntity();
        var entity2 = manager.createEntity();
        assert entity1.getIndex() == 0;
        assert entity1.getGeneration() == 0;
        assert entity2.getIndex() == 1;
        assert entity2.getGeneration() == 0;
    }

    @Test
    public void destroyEntity() {
        var entity = manager.createEntity();
        manager.destroyEntity(entity);
        assert !manager.isValid(entity);
    }

    @Test
    public void testGeneration() {
        var entities = IntStream.range(0, 1004).mapToObj(d->manager.createEntity()).collect(Collectors.toList());
        entities.forEach(manager::destroyEntity);

        var entity = manager.createEntity();

        assert entity.getIndex() == 0;
        assert entity.getGeneration() == 1;
    }
}