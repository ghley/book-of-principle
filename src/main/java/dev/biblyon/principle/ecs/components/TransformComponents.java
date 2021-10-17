package dev.biblyon.principle.ecs.components;

import dev.biblyon.principle.ecs.Component;
import dev.biblyon.principle.ecs.Components;
import dev.biblyon.principle.math.Transform;

public class TransformComponents extends Components<TransformComponents.Bean> {

    public TransformComponents() {
        super(Bean.class);
    }

    public static class Bean implements Component<Bean> {
        private Transform transform;

        @Override
        public void set(Bean component) {
            this.transform.set(component.transform);
        }
    }
}
