PFont font;

void setup() {
    size( 600, 600, P3D);
    background(254);
    rect(10,10,580,570);
    font = loadFont("FFScala.ttf"); 
    textFont(font); 
}

void draw() {
    text("word1", 100, 100,100);
    text("word2", 100, 100,10);
    text("minus", 200, 200,-100);
    text("other", 200, 300,200);
    fill(0, 102, 153, 51);
}