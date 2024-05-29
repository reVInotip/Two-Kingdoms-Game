package model.projectile;

import model.UnitView;


@UnitView(color = {50, 50, 50, 255}, type = "projectile", name = "arrow")
public class Arrow extends Projectile {
    @Override
    public double getSpeed() {
        return 0.7;
    }

    @Override
    public double getDamage() {
        return 50;
    }
}
