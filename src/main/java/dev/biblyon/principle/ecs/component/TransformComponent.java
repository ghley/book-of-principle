package dev.biblyon.principle.ecs.component;

import dev.biblyon.principle.ecs.Component;
import dev.biblyon.principle.math.Transform;

public class TransformComponent implements Component {
    private Transform transform;

    public Transform getTransform() {
        return transform;
    }
}
