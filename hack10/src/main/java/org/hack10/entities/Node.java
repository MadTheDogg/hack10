package org.hack10.entities;

public class Node {
    private Entity entity;
    private Position position;

    public Node(Position position) {
        entity = null;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
