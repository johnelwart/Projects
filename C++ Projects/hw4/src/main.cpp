#include <SFML/Graphics.hpp>
#include <time.h>
#include <iostream>

using namespace sf;

const int M = 25;  // Height of grid
const int N = 40;  // Width of grid

int grid[M][N] = {0};  // A value of zero means a blank square
int ts = 18; //tile size

class Enemy {

private:
    int E_x, E_y,   // Location of the spinner
        E_dx, E_dy; // Speed of the spinner

public:
    void setX (int x) {
        if (x >= 0 && x < N) {
            E_x = x;
        }
    }
    int getX () {
        return E_x;
    }

    void setY (int y) {
        if (y >= 0 && y < M) {
            E_y = y;
        }
    }
    int getY () {
        return E_y;
    }

    void setDX(int dx) {
        if (dx > 0 && dx < 8) {
            E_dx = dx;
        }
    }
    int getDX(){
        return E_dx;
    }

    void setDY(int dy) {
        if (dy > 0 && dy < 8) {
            E_dy = dy;
        }
    }
    int getDY(){
        return E_dy;
    }

    Enemy () {
        E_x = E_y = 300; // Starting location of spinner
        E_dx = 4 - rand () % 8; // Setting the random speed of the spinner in the x direction
        E_dy = 4 - rand () % 8; // Setting the random speed of the spinner in the y direction
    }

    void move () {
        E_x += E_dx;

        if (grid[E_y / ts][E_x / ts] == 1) {
            E_dx = -E_dx;
            E_x += E_dx;
        }

        E_y += E_dy;

        if (grid[E_y / ts][E_x / ts] == 1) {
            E_dy = -E_dy;
            E_y += E_dy;
        }
    }
};

// Flood fill a box with -1 if the input x,y is a 0.
// recursive function
void drop (int y, int x) {
    // Base case. Simplest case. We know what the answer is without doing any work.
    if (grid[y][x] == 0) {
        grid[y][x] = -1;  // Check if the x,y coordinate is empty, then set to -1.
    }
    // -1 is no color. Used to fill in regions that are currently 0 color

    // Recursive step. Breaks problem into simpler problems and calls drop to solve these problems.
    if (grid[y - 1][x] == 0) {
        drop (y - 1, x); // Check if north neighbor is a zero.
    }

    if (grid[y + 1][x] == 0) {
        drop (y + 1, x); // Check if south neighbor is a zero.
    }

    if (grid[y][x - 1] == 0) {
        drop (y, x - 1); // Check if west neighbor is a zero.
    }

    if (grid[y][x + 1] == 0) {
        drop (y, x + 1); // Check if east neighbor is a zero.
    }
}

int main () {
    srand (time (0));

    RenderWindow window (VideoMode (N * ts, M * ts), "Xonix Game!");
    window.setFramerateLimit (60); // Max frame rate is 60 frames/sec

    Texture t1, t2, t3;
    t1.loadFromFile ("images/tiles.png");
    if (!t1.loadFromFile ("images/tiles.png")) {
        return EXIT_FAILURE;
    }

    t2.loadFromFile ("images/gameover.png");
    if (!t2.loadFromFile ("images/gameover.png")) {
        return EXIT_FAILURE;
    }

    t3.loadFromFile ("images/enemy.png");
    if (!t3.loadFromFile ("images/enemy.png")) {
        return EXIT_FAILURE;
    }

    Sprite sTile (t1), sGameover (t2), sEnemy (t3);
    sGameover.setPosition (100, 100);
    sEnemy.setOrigin (20, 20);

    int enemyCount = 4;
    Enemy a[10];

    bool Game = true;
    int x = 0, y = 0,
        dx = 0, dy = 0; // Delta x and y for the player motion
    float timer = 0, delay = 0.07;
    Clock clock;

    for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
            if (i == 0 || j == 0 || i == M - 1 || j == N - 1) grid[i][j] = 1;
        }
    }

    while (window.isOpen ()) {
        float time = clock.getElapsedTime ().asSeconds ();
        clock.restart ();
        timer += time;

        // Event loop
        Event e;
        while (window.pollEvent (e)) {
            if (e.type == Event::Closed) {
                window.close ();
            }

            if (e.type == Event::KeyPressed) {
                if (e.key.code == Keyboard::Escape) {
                    for (int i = 1; i < M - 1; i++) {
                        for (int j = 1; j < N - 1; j++) {
                            grid[i][j] = 0;
                        }
                    }

                    x = 10;
                    y = 0;
                    Game = true;
                }
            }
        }

        // Processing arrow key presses
        if (Keyboard::isKeyPressed (Keyboard::Left)) {
            dx = -1;
            dy = 0;
        };

        if (Keyboard::isKeyPressed (Keyboard::Right)) {
            dx = 1;
            dy = 0;
        };

        if (Keyboard::isKeyPressed (Keyboard::Up)) {
            dx = 0;
            dy = -1;
        };

        if (Keyboard::isKeyPressed (Keyboard::Down)) {
            dx = 0;
            dy = 1;
        };

        if (!Game)
        {
            continue;
        }

        if (timer > delay) {
            x += dx;
            y += dy;

            // Keeping the red tile in the bounds
            if (x < 0) {
                x = 0;
            }

            if (x > N - 1) {
                x = N - 1;
            }

            if (y < 0) {
                y = 0;
            }

            if (y > M - 1) {
                y = M - 1;
            }

            if (grid[y][x] == 2) {
                Game = false;
            }

            if (grid[y][x] == 0) {
                grid[y][x] = 2;
            }

            timer = 0;
        }

        for (int i = 0; i < enemyCount; i++) {
            a[i].move ();
        }

        if (grid[y][x] == 1) {
            dx = dy = 0;

            // Fill in grid squares that contain enemies
            for (int i = 0; i < enemyCount; i++) {
                drop (a[i].getY () / ts, a[i].getX () / ts); // Call drop at the x,y coordinate of each enemy
            }

            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    if (grid[i][j] == -1) {
                        grid[i][j] = 0; // If grid is -1, set back to zero
                    } else {
                        grid[i][j] = 1; // If grid was zero, set grid location to blue
                    }
                }
            }
        }

        for (int i = 0; i < enemyCount; i++) {
            if (grid[a[i].getY () / ts][a[i].getX () / ts] == 2) {
                Game = false;
            }
        }

        /////////draw//////////
        window.clear ();

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 0) {
                    continue;  // Empty
                }
                if (grid[i][j] == 1) {
                    sTile.setTextureRect (IntRect (0, 0, ts, ts));  // Blue
                }
                if (grid[i][j] == 2) {
                    sTile.setTextureRect (IntRect (54, 0, ts, ts)); // Green
                }
                sTile.setPosition (j * ts, i * ts);
                window.draw (sTile);
            }
        }

        sTile.setTextureRect (IntRect (36, 0, ts, ts)); // Grab the red tile
        sTile.setPosition (x * ts, y * ts); // Sets the position of the red tile
        window.draw (sTile); // Draw the red tile

        sEnemy.rotate (10);
        for (int i = 0; i < enemyCount; i++) {
            sEnemy.setPosition (a[i].getX (), a[i].getY ());
            window.draw (sEnemy);
        }

        if (!Game) {
            window.draw (sGameover);
        }

        window.display ();
    }

    return 0;
}
