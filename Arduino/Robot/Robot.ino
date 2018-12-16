#include <Multiservo.h>
#include <Spider.h>

Spider mySpider;
 
void setup() {
  Serial.begin(9600); 
  Leg rightTop(15, 16, 17, 90, 130, 115, 'r');
  Leg leftTop(2, 1, 0, 90, 55, 60, 'l'); 
  Leg rightCenter(12, 13, 14, 95, 130, 130, 'r');
  Leg leftCenter(5, 4, 3, 90, 50, 40, 'l');
  Leg rightBottom(6, 7, 8, 90, 130, 90, 'r');
  Leg leftBottom(9, 10, 11, 90, 40, 50, 'l'); 
  mySpider = Spider(leftTop, leftCenter, leftBottom, rightTop, rightCenter, rightBottom);
}

void crouch(int del = 200) {
  mySpider.moving(MOVE_CROUCH);
  delay(del);
  mySpider.moving(MOVE_STANDUP);
}

void move_forward(int del = 100) {
  /*  First Algorithm
  mySpider.moving({LEG_STD, {30, 60, 70}, LEG_STD, {30, 60, 70}, LEG_STD, {30, 60, 70}});
  delay(del);
  mySpider.moving({LEG_STD, {30, 1, 1}, LEG_STD, {30, 1, 1}, LEG_STD, {30, 1, 1}});
  delay(del);
  mySpider.moving({LEG_UP, {30, 1, 1}, LEG_UP, {30, 1, 1}, LEG_UP, {30, 1, 1}});
  delay(del);
  mySpider.moving({LEG_UP, LEG_STD, LEG_UP, LEG_STD, LEG_UP, LEG_STD});
  delay(del);
  mySpider.moving({LEG_STD, LEG_STD, LEG_STD, LEG_STD, LEG_STD, LEG_STD}); */

  
  mySpider.moving({{30, 60, 70}, LEG_STD, LEG_STD, {30, 60, 70}, LEG_STD, LEG_STD});
  delay(del);
  mySpider.moving({{30, 1, 1}, LEG_STD, LEG_STD, {30, 1, 1}, LEG_STD, LEG_STD});
  delay(del);
  mySpider.moving({{30, 1, 1}, LEG_STD, {30, 60, 70}, {30, 1, 1}, LEG_STD, {30, 60, 70}});
  delay(del);
  mySpider.moving({{30, 1, 1}, LEG_STD, {30, 1, 1}, {30, 1, 1}, LEG_STD, {30, 1, 1}});
  delay(del);
  mySpider.moving({LEG_STD, {1, 60, 70}, LEG_STD, LEG_STD, {1, 60, 70}, LEG_STD});
  delay(del);
  mySpider.moving({LEG_STD, LEG_STD, LEG_STD, LEG_STD, LEG_STD, LEG_STD});
  
  /* Second Algorithm
  mySpider.moving({{50, 60, 70}, LEG_STD, LEG_STD, {50, 60, 70}, LEG_STD, LEG_STD});
  delay(del);
  mySpider.moving({{50, 1, -30}, LEG_STD, LEG_STD, {50, 1, -30}, LEG_STD, LEG_STD});
  delay(del);
  mySpider.moving({{50, 1, -30}, {50, 60, 70}, LEG_STD, {50, 1, -30}, {50, 60, 70}, LEG_STD});
  delay(del);
  mySpider.moving({{50, 1, -30}, {50, 1, -30}, LEG_STD, {50, 1, -30}, {50, 1, -30}, LEG_STD});
  delay(del);
  mySpider.moving({{50, 1, -30}, {50, 1, -30}, {-50, 1, 1}, {50, 1, -30}, {50, 1, -30}, {-50, 1, 1}});
  delay(del / 5);
  mySpider.moving({{50, 30, -30}, {50, 10, -30}, {-50, 1, -30}, {50, 30, -30}, {50, 10, -30}, {-50, 1, -30}});
  delay(del / 5);
  mySpider.moving({{1, 30, -30}, {1, 10, -30}, {1, 1, -30}, {1, 30, -30}, {1, 10, -30}, {1, 1, -30}});
  delay(del);
  mySpider.moving({LEG_STD, LEG_STD, LEG_STD, LEG_STD, LEG_STD, LEG_STD});*/
}

void move_back(int del = 100) {
  mySpider.moving({LEG_STD, LEG_STD, {-30, 60, 70}, LEG_STD, LEG_STD, {-30, 60, 70}});
  delay(del);
  mySpider.moving({LEG_STD, LEG_STD, {-30, 1, 1}, LEG_STD, LEG_STD, {-30, 1, 1}});
  delay(del);
  mySpider.moving({{-30, 60, 70}, LEG_STD, {-30, 1, 1}, {-30, 60, 70}, LEG_STD, {-30, 1, 1}});
  delay(del);
  mySpider.moving({{-30, 1, 1}, LEG_STD, {-30, 1, 1}, {-30, 1, 1}, LEG_STD, {-30, 1, 1}});
  delay(del);
  mySpider.moving({LEG_STD, {1, 60, 70}, LEG_STD, LEG_STD, {1, 60, 70}, LEG_STD});
  delay(del);
  mySpider.moving(MOVE_STANDUP);
}

void move_left(int del = 100) {
  mySpider.moving({LEG_UP, LEG_STD, LEG_UP, LEG_STD, LEG_UP, LEG_STD});
  delay(del);
  mySpider.moving({LEG_BACK, LEG_STD, LEG_BACK, LEG_STD, LEG_BACK, LEG_STD});
  delay(del);
  mySpider.moving({LEG_BACK, LEG_UP, LEG_BACK, LEG_UP, LEG_BACK, LEG_UP});
  delay(del);
  mySpider.moving(MOVE_STANDUP);
}

void move_right(int del = 100) {
  mySpider.moving(MOVE_TURN_LEFT);
  delay(del);
  mySpider.moving(MOVE_STANDUP);
  delay(del);
  mySpider.moving(MOVE_TURN_LEFT);
  delay(del);
  mySpider.moving(MOVE_STANDUP);
}

void loop() {
  if (Serial.available() > 0) {
        byte incomingByte = Serial.read();
        if(incomingByte == '1') {
          move_forward();
        }
        else if(incomingByte == '2') {
          move_back();
        }
        else if(incomingByte == '3') {
          move_left();
        }
        else if(incomingByte == '4') {
          move_right();
        }
  }
}
