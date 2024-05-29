package model.projectile;

import model.UnitView;


@UnitView(color = {255, 0, 0, 255}, type = "projectile", name = "fireball")
public class Fireball extends Projectile {
    @Override
    public double getSpeed() {
        return 0.4;
    }

    @Override
    public double getDamage() {
        return 200;
    }
}
