package dev.biblyon.principle.ecs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    private World world;

    @BeforeEach
    public void beforeEach() {
        world = new World();
    }


    @Test
    public void testMask() {
        var world = new World();

        world.registerSystem(System1.class);
        world.registerSystem(System2.class);

        var e1 = world.createEntity();
        var e2 = world.createEntity();
        var e3 = world.createEntity();
        e1.addComponent(Comp1.class, Comp3.class);
        e2.addComponent(Comp1.class, Comp2.class);
        e3.addComponent(Comp1.class, Comp2.class, Comp3.class);

        world.update();

        assertEquals(1, e1.getComponent(Comp1.class).x);
        assertEquals(2, e1.getComponent(Comp3.class).z);

        assertEquals(-1, e2.getComponent(Comp1.class).x);
        assertEquals(1, e2.getComponent(Comp2.class).y);

        assertEquals(-1, e2.getComponent(Comp1.class).x);
        assertEquals(1, e3.getComponent(Comp2.class).y);
        assertEquals(2, e3.getComponent(Comp3.class).z);
    }

    @Test
    public void testEntityGen() {
        var e1 = world.createEntity();
    }

    static public class TestComponentA implements Component {

    }


    public static class System1 extends AbstractSystem {
        @Override
        public Class<? extends Component>[] requiredComponents() {
            return new Class[] { Comp1.class, Comp3.class };
        }

        @Override
        public void update() {
            for (var e : entities) {
                var comp1 = e.getComponent(Comp1.class);
                var comp3 = e.getComponent(Comp3.class);
                comp1.x += 1;
                comp3.z += 2;
            }
        }
    }

    public static class System2 extends AbstractSystem {

        @Override
        public Class<? extends Component>[] requiredComponents() {
            return new Class[] { Comp1.class, Comp2.class };
        }

        @Override
        public void update() {
            for (var e : entities) {
                var comp1 = e.getComponent(Comp1.class);
                var comp2 = e.getComponent(Comp2.class);
                comp1.x -= 1;
                comp2.y += 1;
            }
        }
    }

    public static class Comp1 implements Component {
        int x = 0;
        public Comp1() {

        }
    }

    public static class Comp2 implements Component {
        int y = 0;
        public Comp2() {

        }
    }

    public static class Comp3 implements  Component {
        int z = 0;
        public Comp3() {

        }
    }
}