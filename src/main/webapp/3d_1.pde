 size(200, 200, P3D);
 noStroke();
 background(50);
 lights();
 translate(width/2+30, height/2, 0);
 rotateX(-PI/6);
 rotateY(PI/3 + 210/float(height) * PI);
 box(45);
 translate(0, 0, -50);
 box(30);

