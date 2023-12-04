package components;

import jade.Component;
import org.joml.Vector3f;

public class RigidBody extends Component {

    public Vector3f velocity = new Vector3f();
    public float friction = 0.8f;
    public boolean isDynamic = true;

    @Override
    public void update(float dt) {

    }
}
