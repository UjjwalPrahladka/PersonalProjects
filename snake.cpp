#include<graphics.h>
#include<stdio.h>
#include<conio.h>
#include<dos.h>
#include<pthread.h>
#include<time.h>
char key_pressed = 'd';
void *take_char(void *vargp){
    while(1){
        key_pressed = getch();
    }
}
int main(){
    pthread_t thread;
    int gd = DETECT, gm;
    int x[50], y[50], i, dx = 5, dy = 0, exit = 0, apple_x, apple_y, before_x, before_y;
    srand(time(0));
    apple_x = (rand() % (635  + 1));
    apple_y = (rand() % (475  + 1));
    int size_of_snake = 30;
    for(i = 0; i<size_of_snake; i++){
        x[i] = i*5;
        y[i] = 200;
    }
    initgraph(&gd, &gm, "");
    setcolor(BLACK);
    pthread_create(&thread, NULL, take_char, NULL);
    while(1){
        setfillstyle(SOLID_FILL, RED);
        bar(apple_x, apple_y, apple_x + 5, apple_y + 5);
        setfillstyle(SOLID_FILL, GREEN);
        if(getpixel(x[size_of_snake - 1],y[size_of_snake - 1]) == GREEN){
            exit = 1;
            break;
        }
        else if(getpixel(x[size_of_snake - 1],y[size_of_snake - 1]) == RED || getpixel(x[size_of_snake - 1]+5,y[size_of_snake - 1]+5) == RED){

            if(size_of_snake + 1 < 50)
                size_of_snake++;
            if(dx == 0 && dy == 5){
                y[size_of_snake-1] = y[size_of_snake-2] + 5;
                x[size_of_snake-1] = x[size_of_snake-2];
            }
            else if(dx == 0 && dy == -5){
                y[size_of_snake-1] = y[size_of_snake-2] - 5;
                x[size_of_snake-1] = x[size_of_snake-2];
            }
            else if(dx == 5 && dy == 0){
                x[size_of_snake-1] = x[size_of_snake-2] + 5;
                y[size_of_snake-1] = y[size_of_snake-2];
            }
            else{
                x[size_of_snake-1] = x[size_of_snake-2] - 5;
                y[size_of_snake-1] = y[size_of_snake-2];
            }
            setfillstyle(SOLID_FILL, BLACK);
            bar(apple_x, apple_y, apple_x + 5, apple_y + 5);
            before_x = apple_x;
            before_y = apple_y;
            apple_x = (rand()*5 % (635  + 1));
            apple_y = (rand()*5 % (475  + 1));
            while(before_x == apple_x && before_y == apple_y){
                apple_x = (rand()*5 % (635  + 1));
                apple_y = (rand()*5 % (475  + 1));
            }

        }
        for(i = 0;i < size_of_snake; i++){
            bar(x[i],y[i],x[i] + 5,y[i] + 5);
        }
        if(key_pressed == 'w' && dx != 0 && dy != -5){
            dx = 0;
            dy = -5;
        }
        else if(key_pressed == 's' && dx != 0 && dy != 5){
            dx = 0;
            dy = 5;
        }
        else if(key_pressed == 'a' && dx != -5 && dy != 0){
            dx = -5;
            dy = 0;
        }
        else if(key_pressed == 'd' && dx != 5 && dy != 0){
            dx = 5;
            dy = 0;
        }
        delay(15);
        setfillstyle(SOLID_FILL,BLACK);
        bar(x[0], y[0], x[0] + 5, y[0] + 5);
        for(i = 0; i <size_of_snake - 1;i++){
            x[i] = x[i+1];
            y[i] = y[i+1];
        }
        x[size_of_snake - 1] = x[size_of_snake - 1] + dx;
        y[size_of_snake - 1] = y[size_of_snake - 1] + dy;
        if(x[size_of_snake - 1] < 0){
            x[size_of_snake - 1] = 640 + x[size_of_snake - 1];
        }
        else
            x[size_of_snake - 1] = x[size_of_snake - 1] % 640;
        if(y[size_of_snake - 1] < 0){
            y[size_of_snake - 1] = 480 + y[size_of_snake - 1];
        }
        else
            y[size_of_snake - 1] = y[size_of_snake - 1] % 480;

    }
    cleardevice();
    setcolor(WHITE);
    outtextxy(280, 200, "YOU LOSE\n");
    getch();
    closegraph();
    return 0;
}
