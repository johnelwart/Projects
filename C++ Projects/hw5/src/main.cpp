#include <SFML/Graphics.hpp>
#include <SFML/Audio.hpp>
#include <time.h>
#include <string>

// Life loss and game over sounds from https://www.zapsplat.com, free for personal use.
// Average by Patrick Patrikios (Background music) is from the Youtube Audio Library, free for personal use.
// Arcade Classic font by pizzadude is from https://www.1001freefonts.com, free for personal use
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

    RenderWindow window (VideoMode (N * ts, (M * ts) + 40), "Xonix Game!");
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

    // Sound buffer for game over tone
    SoundBuffer buffer1;
    buffer1.loadFromFile("sounds/arcade_game_fall_tone_001.flac");
    if(!buffer1.loadFromFile("sounds/arcade_game_fall_tone_001.flac")) {
        return EXIT_FAILURE;
    }

    // Sound buffer for background music
    SoundBuffer buffer2;
    buffer2.loadFromFile("sounds/Average-Patrick-Patrikios.flac");
    if(!buffer2.loadFromFile("sounds/Average-Patrick-Patrikios.flac")) {
        return EXIT_FAILURE;
    }

    // Sound buffer for life loss sound
    SoundBuffer buffer3;
    buffer3.loadFromFile("sounds/Lose-Life-1.flac");
    if(!buffer3.loadFromFile("sounds/Lose-Life-1.flac")) {
        return EXIT_FAILURE;
    }

    //Custom font for text
    Font font;
    font.loadFromFile("fonts/ArcadeClassic.ttf");
    if(!font.loadFromFile("fonts/ArcadeClassic.ttf")){
        return EXIT_FAILURE;
    }

    // Game over sound
    Sound sound1;
    sound1.setBuffer(buffer1);
    sound1.setVolume(25);

    // Background music
    Sound sound2;
    sound2.setBuffer(buffer2);
    sound2.setLoop(true);
    sound2.setVolume(15);

    // Life loss sound
    Sound sound3;
    sound3.setBuffer(buffer3);
    sound3.setVolume(15);

    // Score counter text and properties
    Text score;
    score.setFont(font);
    score.setFillColor(Color::White);
    score.setCharacterSize(40);
    score.setPosition(10, 440);

    // High score counter text and properties
    Text hiScore;
    hiScore.setFont(font);
    hiScore.setFillColor(Color::White);
    hiScore.setCharacterSize(40);
    hiScore.setPosition(420, 440);

    // "You won" text and properties
    Text wonText;
    wonText.setFont(font);
    wonText.setString("You  Win");
    wonText.setCharacterSize(60);
    wonText.setPosition(225, 175);

    // Welcome text and properties
    Text welcomeText;
    welcomeText.setFont(font);
    welcomeText.setString("Welcome  to  Xonix!");
    welcomeText.setCharacterSize(60);
    welcomeText.setPosition(100, 10);

    // Lives counter text and properties
    Text lives;
    lives.setFont(font);
    lives.setFillColor(Color::White);
    lives.setCharacterSize(40);
    lives.setPosition(230, 440);

    // Instructions text and properties
    Text instructionText;
    instructionText.setFont(font);
    instructionText.setString("Your  objective  is  to  fill  in  as  much  of  the  grid  as \n"
                              "possible  without  colliding  with  the  enemies  or  yourself\n\n"
                              "Use  the  arrow  keys  or  w  a  s  d  keys  to  move  around\n\n"
                              "870  points  to  win  the  game\n\n"
                              "To  restart  the  game  press  the  escape  key\n\n"
                              "To  clear  this  text  press  the  enter  key");
    instructionText.setCharacterSize(23);
    instructionText.setPosition(30, 70);


    Sprite sTile (t1), sGameover (t2), sEnemy (t3);
    sGameover.setPosition (100, 100);
    sEnemy.setOrigin (20, 20);

    int curScore = 0;  // Variable for score counter
    int highScore = 0;  // Variable for high score
    int numLives = 2;  // Number of lives left

    int enemyCount = 4;
    Enemy a[10];

    int Game = 1;  // I changed Game to an int from a boolean because the lives system would not work
                   // With the starting code
    int x = 0, y = 0,
        dx = 0, dy = 0; // Delta x and y for the player motion
    float timer = 0, delay = 0.07;
    Clock clock;

    for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
            if (i == 0 || j == 0 || i == M - 1 || j == N - 1) grid[i][j] = 1;
        }
    }

    sound2.play();

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

                    numLives = 2;
                    sound2.play();
                    for (int i = 1; i < M - 1; i++) {
                        for (int j = 1; j < N - 1; j++) {
                            grid[i][j] = 0;
                        }
                    }

                    x = 10;
                    y = 0;
                    dx = 0;
                    dy = 0;
                    Game = 1;
                }
            }
        }

        // Processing arrow key presses (Added W,A,S,D keys for part 4)
        if (Keyboard::isKeyPressed (Keyboard::Left) || Keyboard::isKeyPressed(Keyboard::A)) {
            dx = -1;
            dy = 0;
        };

        if (Keyboard::isKeyPressed (Keyboard::Right) || Keyboard::isKeyPressed(Keyboard::D)) {
            dx = 1;
            dy = 0;
        };

        if (Keyboard::isKeyPressed (Keyboard::Up) || Keyboard::isKeyPressed(Keyboard::W)) {
            dx = 0;
            dy = -1;
        };

        if (Keyboard::isKeyPressed (Keyboard::Down) || Keyboard::isKeyPressed(Keyboard::S)) {
            dx = 0;
            dy = 1;
        };

        //Press the enter key to hide the welcome and instructions text
        if (Keyboard::isKeyPressed(Keyboard::Enter)) {
            welcomeText.setFillColor(Color::Transparent);
            instructionText.setFillColor((Color::Transparent));
        }

        if (Game == 1) {

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
                    Game = 0;
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
                    drop (a[i].getY () / ts, a[i].getX () / ts);
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
                    Game = 0;
                }
            }
        }

            /////////draw//////////
            window.clear ();

            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    if (grid[i][j] == 1) {
                        sTile.setTextureRect (IntRect (0, 0, ts, ts));  // Blue
                        sTile.setPosition (j * ts, i * ts);
                        window.draw (sTile);
                    }
                    if (grid[i][j] == 2) {
                        sTile.setTextureRect (IntRect (54, 0, ts, ts)); // Green
                        sTile.setPosition (j * ts, i * ts);
                        window.draw (sTile);
                    }
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

            if (Game == 0) {

                // Check if the player is on their last life and end the game if so
                if (numLives == 0) {

                    numLives -= 1;
                    sound2.stop ();
                    sound1.play ();
                    Game = 3;

                    // Check if the ending score is greater than that high score and make the necessary changes
                    if (curScore > highScore) {
                        highScore = curScore;
                    }
                }
                else {
                    sound3.play (); // Play the lose life sound
                    numLives -= 1; // Subtract lives by 1

                    // Reset player position and speed
                    x = 10;
                    y = 0;
                    dx = 0;
                    dy = 0;

                    // Delete the path the player made when they crashed.
                    for (int i = 1; i < M - 1; i++) {
                        for (int j = 1; j < N - 1; j++) {
                            if (grid[i][j] == 2) {
                                grid[i][j] = 0;
                            }
                        }
                    }
                    Game = 1;
                }
            }

            // The gameover object has its own if statement otherwise it loops forever and flickers
            if(Game == 3)
            {
                window.draw(sGameover);
            }

        curScore = -126;  // Set to -126 to account for the starting tiles
        for(int i = 0; i < M; i++)
        {
            for(int j = 0; j < N; j++)
            {
                if (grid[i][j] == 1)
                {
                    curScore++;
                    if (curScore == 870)
                    {
                        Game = false;
                        window.draw(wonText); // If all the enemies are trapped the player wins
                        sound2.stop();
                    }
                }
            }
        }

        //Draw the welcome and instruction text
        window.draw(welcomeText);
        window.draw(instructionText);

        // Displaying the current score, lives, and high score for the player
        score.setString("Score  " + std::to_string(curScore));
        hiScore.setString("High Score  " + std::to_string(highScore));
        lives.setString("Lives  x" + std::to_string(numLives + 1));

        window.draw(lives);
        window.draw(score);
        window.draw(hiScore);

        window.display ();
    }
    return 0;
}