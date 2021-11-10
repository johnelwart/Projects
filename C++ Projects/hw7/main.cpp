#include <SFML/Graphics.hpp>
#include <SFML/Audio.hpp>
#include <time.h>
#include <list>
#include <cmath>

// UFO icon by Freepik from https://www.flaticon.com, free for personal use.
// The Quantum Realm by The Whole Other is from the Youtube Audio Library, free for personal use.
// UFO appearance warning sound from https://www.zapsplat.com, free for personal use.

// For extra credit I added conditionals to the asteroid vs player and player vs UFO collision functions so the
// player has 3 seconds of invincibility after they explode so they dont lose lives over and over after spawning.

using namespace sf;

const int W = 1200;
const int H = 800;

float DEGTORAD = 0.017453f;

class Animation {
public:
    float Frame, speed;
    Sprite sprite;
    std::vector<IntRect> frames;

    Animation() {}

    Animation(Texture &t, int x, int y, int w, int h, int count, float Speed) {
        Frame = 0;
        speed = Speed;

        for (int i = 0; i < count; i++) {
            frames.push_back(IntRect(x + i * w, y, w, h));
        }

        sprite.setTexture(t);
        sprite.setOrigin(w / 2, h / 2);
        sprite.setTextureRect(frames[0]);
    }


    void update() {
        Frame += speed;
        int n = frames.size();
        if (Frame >= n) {
            Frame -= n;
        }
        if (n > 0) {
            sprite.setTextureRect(frames[int(Frame)]);
        }
    }

    bool isEnd() {
        return Frame + speed >= frames.size();
    }

};


class Entity {
public:
    float x, y,  // x and y location of the entity
    dx, dy,  // speed in the x and y direction of the entity
    R,  // collision radius of the entity
    angle;  // rotation of the entity
    bool life;  // if false, remove from the entities list
    std::string name;  // the name of the derived class
    Animation anim;  // object for displaying the animation

    Entity() {
        life = true;  // set the entity to be drawn on the screen
    }

    void settings(Animation &a, int X, int Y, float Angle = 0, int radius = 1) {
        anim = a;
        x = X;
        y = Y;
        angle = Angle;
        R = radius;
    }

    virtual void update() {};

    void draw(RenderWindow &app) {
        anim.sprite.setPosition(x, y);
        anim.sprite.setRotation(angle + 90);
        app.draw(anim.sprite);

        CircleShape circle(R);
        circle.setFillColor(Color(255, 0, 0, 170));
        circle.setPosition(x, y);
        circle.setOrigin(R, R);
        //app.draw(circle);
    }

    virtual ~Entity() {};
};


class asteroid : public Entity {
public:
    asteroid() {
        dx = rand() % 8 - 4;
        dy = rand() % 8 - 4;
        name = "asteroid";
        ++asteroidCount;
    }

    // concrete class, i.e., not virtual
    void update() {
        x += dx;  // update the x position of the asteroid
        y += dy;

        // Draw the asteroid on the opposite side of the screen if it goes of an edge
        if (x > W) {
            x = 0;
        }
        if (x < 0) {
            x = W;
        }
        if (y > H) {
            y = 0;
        }
        if (y < 0) {
            y = H;
        }
    }

    static int asteroidCount;

    ~asteroid() {
        --asteroidCount;
    }

};


class bullet : public Entity {
public:
    bullet() {
        name = "bullet";
    }

    void update() {
        // Set the direction and speed of bullet
        dx = cos(angle * DEGTORAD) * 6;
        dy = sin(angle * DEGTORAD) * 6;
        // angle+=rand()%6-3;
        x += dx;
        y += dy;

        // when the bullet hits the edge of the screen remove it from the display list
        if (x > W || x < 0 || y > H || y < 0) {
            life = false;
        }
    }

};


class player : public Entity {
public:
    bool thrust = false;

    player() {
        dx = 0;
        dy = 0;
        name = "player";
    }

    void update() {
        // When the up arrow is pressed, then thrust is true
        // Speed up the space ship
        if (thrust) {
            dx += cos(angle * DEGTORAD) * 0.2; // Increase the speed by 20% in the angle direction
            dy += sin(angle * DEGTORAD) * 0.2;
        } else {
            dx *= 0.99;  // slow down by 1%
            dy *= 0.99;
        }

        //limit speed of the player to 15
        int maxSpeed = 15;
        float speed = sqrt(dx * dx + dy * dy);
        if (speed > maxSpeed) {
            dx *= maxSpeed / speed;
            dy *= maxSpeed / speed;
        }

        // Enforcing the borders
        x += dx;
        y += dy;

        if (x > W) {
            x = 0;
        }
        if (x < 0) {
            x = W;
        }
        if (y > H) {
            y = 0;
        }
        if (y < 0) {
            y = H;
        }
    }

};


class ufo : public Entity {
public:
    ufo() {
        dx = 2;
        dy = 0;
        name = "UFO";
        ++ufoCount;
    }

    void update() {
        x += dx;
        y += dy;

        if (x > W) {
            life = false;
        }
        if (x < 0) {
            life = false;
        }
    }

    static int ufoCount;

    ~ufo() {
        --ufoCount;
    }
};


bool isCollide(Entity *a, Entity *b) {
    return (b->x - a->x) * (b->x - a->x) +
           (b->y - a->y) * (b->y - a->y) <
           (a->R + b->R) * (a->R + b->R);
}

int asteroid::asteroidCount = 0;
int ufo::ufoCount = 0;

int main() {
    int t = 181;
    srand(time(0));

    RenderWindow app(VideoMode(W, H), "Asteroids!");
    app.setFramerateLimit(60);

    Texture t1, t2, t3, t4, t5, t6, t7, t8;
    t1.loadFromFile("images/spaceship.png");
    t2.loadFromFile("images/background.jpg");
    t3.loadFromFile("images/explosions/type_C.png");
    t4.loadFromFile("images/rock.png");
    t5.loadFromFile("images/fire_blue.png");
    t6.loadFromFile("images/rock_small.png");
    t7.loadFromFile("images/explosions/type_B.png");
    t8.loadFromFile("images/ufo.png");

    t1.setSmooth(true);
    t2.setSmooth(true);

    Sprite background(t2);

    Animation sExplosion(t3, 0, 0, 256, 256, 48, 0.5);
    Animation sRock(t4, 0, 0, 64, 64, 16, 0.2);
    Animation sRock_small(t6, 0, 0, 64, 64, 16, 0.2);
    Animation sBullet(t5, 0, 0, 32, 64, 16, 0.8);
    Animation sPlayer(t1, 40, 0, 40, 40, 1, 0);
    Animation sPlayer_go(t1, 40, 40, 40, 40, 1, 0);
    Animation sExplosion_ship(t7, 0, 0, 192, 192, 64, 0.5);
    Animation sUFO(t8, 0, 0, 64, 64, 1, 0.05);

    SoundBuffer buffer1, buffer2;
    buffer1.loadFromFile("sounds/ufoAlert.flac");
    buffer2.loadFromFile("sounds/ufoSound.ogg");

    Sound sound1, sound2;
    sound1.setBuffer(buffer1);
    sound1.setVolume(25);
    sound2.setBuffer(buffer2);
    sound2.setLoop(true);
    sound2.setVolume(30);

    std::list<Entity *> entities;  // list of entity pointers

    // Spawns 15 asteroids and adds them to the vector of entities
    for (int i = 0; i < 15; i++) {
        asteroid *a = new asteroid();
        a->settings(sRock, rand() % W, rand() % H, rand() % 360, 25); // asteroid animation
        entities.push_back(a);
    }

    // Spawns a new player object and adds it to the vector of entities
    player *p = new player();
    p->settings(sPlayer, W / 2, H / 2, 0, 20);
    entities.push_back(p);

    /////main loop/////
    while (app.isOpen()) {
        Event event;
        while (app.pollEvent(event)) {
            if (event.type == Event::Closed) {
                app.close();
            }

            if (event.type == Event::KeyPressed) {
                if (event.key.code == Keyboard::Space) {
                    bullet *b = new bullet();
                    b->settings(sBullet, p->x, p->y, p->angle, 10);
                    entities.push_back(b);
                }
            }
        }

        if (Keyboard::isKeyPressed(Keyboard::Right)) {
            p->angle += 3;
        }
        if (Keyboard::isKeyPressed(Keyboard::Left)) {
            p->angle -= 3;
        }
        if (Keyboard::isKeyPressed(Keyboard::Up)) {
            p->thrust = true;
        } else {
            p->thrust = false;
        }


        for (auto a:entities) {
            for (auto b:entities) {

                // If a bullet and an asteroid collide...
                if (a->name == "asteroid" && b->name == "bullet") {
                    if (isCollide(a, b)) {
                        a->life = false; // The bullet is put on the list for deletion
                        b->life = false; // The asteroid is put on the list for deletion

                        // Spawns an explosion animation for the asteroid
                        Entity *e = new Entity();
                        e->settings(sExplosion, a->x, a->y);
                        e->name = "explosion";
                        entities.push_back(e);

                        // If the asteroid hit is bigger it splits into 2 smaller ones
                        for (int i = 0; i < 2; i++) {
                            if (a->R == 15) continue;
                            Entity *e = new asteroid();
                            e->settings(sRock_small, a->x, a->y, rand() % 360, 15);
                            entities.push_back(e);
                        }
                    }
                }

                // If the player and an asteroid collide...
                if (t > 180){
                    if (a->name == "player" && b->name == "asteroid") {
                        if (isCollide(a, b)) {
                            b->life = false; // The asteroid is put on the list for deletion

                            // Spawns an explosion animation for the player
                            Entity *e = new Entity();
                            e->settings(sExplosion_ship, a->x, a->y);
                            e->name = "explosion";
                            entities.push_back(e);

                            // Resets the player to the center of the screen
                            p->settings(sPlayer, W / 2, H / 2, 0, 20);
                            p->dx = 0;
                            p->dy = 0;
                            t = 0;
                        }
                    }
                }

                // If a bullet and the UFO collide...
                if (a->name == "bullet" && b->name == "UFO") {
                    if (isCollide(a, b)) {
                        a->life = false;  // The bullet is put on the list for deletion
                        b->life = false;  // The UFO is put on the list for deletion

                        // Spawns explosion animation for the asteroid
                        Entity *e = new Entity();
                        e->settings(sExplosion_ship, b->x, b->y);
                        e->name = "explosion";
                        entities.push_back(e);
                    }
                }

                // If the player and UFO collide...
                if (t > 180) {
                    if (a->name == "player" && b->name == "UFO") {
                        if (isCollide(a, b)) {
                            b->life = false;  // The UFO is put on the list for deletion

                            // Spawns explosion animations for the player and for the UFO
                            Entity *e_u = new Entity();
                            e_u->settings(sExplosion_ship, b->x, b->y);
                            e_u->name = "explosion";
                            entities.push_back(e_u);

                            Entity *e_p = new Entity();
                            e_p->settings(sExplosion_ship, a->x, a->y);
                            e_p->name = "explosion";
                            entities.push_back(e_p);

                            // Resets the player to the middle of the screen
                            p->settings(sPlayer, W / 2, H / 2, 0, 20);
                            p->dx = 0;
                            p->dy = 0;
                            t = 0;
                        }
                    }
                }
            }
        }
        if (t < 181)
        {
            t++;
        }

        // If the thrust variable is true than the animation of the player object changes to a different image
        if (p->thrust) {
            p->anim = sPlayer_go;
        } else {
            p->anim = sPlayer;
        }


        for (auto e:entities) {
            if (e->name == "explosion") {
                if (e->anim.isEnd()) {
                    e->life = 0;
                }
            }
        }

        // If the computer picks a zero and there is not already a ufo on screen it spawns a new one
        if (rand() % 1000 == 0 && ufo::ufoCount == 0) {
            ufo *u = new ufo();
            u->settings(sUFO, 1, rand() % H + 1, 270, 25);
            entities.push_back(u);

            sound1.play();
            sound2.play();
        }

        if (ufo::ufoCount == 0)
        {
            sound1.stop();
            sound2.stop();
        }

        // When the player destroys the last asteroid 15 more spawn using this function
        if (asteroid::asteroidCount == 0) {
            for (int i = 0; i < 15; i++) {
                asteroid *a = new asteroid();
                a->settings(sRock, 0, rand() % H, rand() % 360, 25);
                entities.push_back(a);
            }
        }

        // entities.begin() returns the first iterator of the entities list
        // entities.end() returns the iterator of the theoretical element after the last element in the list
        // i is an iterator, an iterator is an object that is like a pointer
        for (auto i = entities.begin(); i != entities.end();) {
            Entity *e = *i; // Use the * on the iterator i to get the element of the list, i.e., an Entity pointer

            e->update();  // polymorphism, call the derived class update
            e->anim.update();  // update the animation

            // Remove an entity pointer from the list if life is false
            if (e->life == false) {
                i = entities.erase(
                        i);  // erase removes the element from the list and moves the iterator to the next position
                delete e;  // polymorphism, call the destructor for the derived class
            } else i++; // move the iterator to the next element in the list
        }


        //////draw//////
        app.draw(background);

        for (auto i:entities)
            i->draw(app);

        app.display();
    }

    return 0;
}