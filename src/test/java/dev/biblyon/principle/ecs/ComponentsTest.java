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

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ComponentsTest {

    private Components<TestComponent> components;
    private List<Entity> entityList;
    private List<TestComponent> componentList;

    public void before() {
        components = new Components<>(TestComponent.class);
        EntityManager manager = new EntityManager();
        entityList = new ArrayList<>();
        componentList = new ArrayList<>();
        for (int q = 0; q < 100; q++) {
            entityList.add(manager.createEntity());
            componentList.add(new TestComponent(q));
        }
    }

    @Test
    public void testInsert() {
        before();
        for (int q = 0; q < entityList.size(); q++) {
            components.addComponent(entityList.get(q), componentList.get(q), false);
        }
        for (int q = 0; q < entityList.size(); q++) {
            var comp = components.getComponent(entityList.get(q));
            assert comp.number == componentList.get(q).number;
        }
    }

    @Test
    public void testRemove() {
        before();
        for (int q = 0; q < entityList.size(); q++) {
            components.addComponent(entityList.get(q), componentList.get(q), false);
        }
        components.removeComponent(entityList.get(10));
    }

    @Test
    public void testSleeping() {
        before();
        for (int q = 0; q < 10; q++) {
            components.addComponent(entityList.get(q), componentList.get(q), false);
        }
        components.addComponent(entityList.get(10), componentList.get(10), true);
        assert components.isSleeping(entityList.get(10));
        for (int q = 0; q < 10; q++) {
            components.removeComponent(entityList.get(q));
        }
        assert components.isSleeping(entityList.get(10));
        assert components.getComponent(entityList.get(10)).number == 10;
    }

    @Test
    public void testSetSleeping() {
        before();
        for (int q = 0; q < 20; q++) {
            components.addComponent(entityList.get(q), componentList.get(q), q < 10);
        }
        components.setSleeping(entityList.get(19), true);
        for (int q = 0; q < 10; q++) {
            assert components.isSleeping(entityList.get(q));
        }
        for (int q = 10; q < 20; q++) {
            if (q == 19)
                continue;
            assert !components.isSleeping(entityList.get(q));
        }
        assert components.isSleeping(entityList.get(19));
    }


     @SuppressWarnings("unused")
     public static class TestComponent implements Component<TestComponent> {
        public int number = 0;

        public TestComponent() {

        }

        public TestComponent(int number) {
            this.number = number;
        }

        @Override
        public void set(TestComponent component) {
            this.number = component.number;
        }
    }
}