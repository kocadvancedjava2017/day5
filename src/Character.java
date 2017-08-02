import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Character {
    Vector p;
    Vector v;
    Vector a;

    Vector sz = new Vector(100, 100);

    BufferedImage sprite;

    ArrayList<Bullet> bullets = new ArrayList<>();

    final int WIDTH;
    final int HEIGHT;

    float friction = 0.95f;

    public Character(int WIDTH, int HEIGHT, BufferedImage sprite){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;

        p = new Vector(WIDTH/2, HEIGHT/2);
        v = new Vector(100, 100);
        a = new Vector(0, 0);
        sz = new Vector(100, 100);

        this.sprite = sprite;
    }

    public void fireBullet(BufferedImage bulletimage){
        bullets.add(new Bullet(
            Vector.add(p, Vector.div(sz, 2)),
            new Vector(-500, 0),
            new Vector(30, 20),
            0.5f,
            bulletimage
        ));
    }

    public void update(float dt){
        if(p.x + sz.ix > WIDTH || p.x < 0){
            v.setX(-v.x);
        }
        if(p.y + sz.ix > HEIGHT || p.y < 0){
            v.setY(-v.y);
        }

        v.add(Vector.mult(a, dt));
        v.mult(friction);
        p.add(Vector.mult(v, dt));

        a = new Vector(0, 0);

        for(int i = bullets.size() - 1; i >= 0; i--){
            bullets.get(i).update(dt);
            if (!bullets.get(i).isActive) {
                bullets.remove(i);
            }
        }
    }

    public void draw(Graphics2D g){
        g.drawImage(sprite, p.ix, p.iy, sz.ix, sz.iy, null);
        for( Bullet b : bullets) b.draw(g);
    }
}
