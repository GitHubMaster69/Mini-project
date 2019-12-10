
package Main;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.Table;
import processing.data.TableRow;



public class Main extends PApplet {

    float angle;

    Table table;


    public void settings() {
        size(600,600,P3D);
    }

    public void setup() {
       // loadImage(earth.jpg);

    }

    public void draw() {
        background(51);
        translate(width*0.5f, height*0.5f);
        rotateY(angle);
        angle += 0.05;

        lights();
        fill(200);
        noStroke();
        float r = 200;
        sphere(r);
        fill(255,0,0);

        for (TableRow row : table.rows()) {
            float lat = row.getFloat("latitude");
            float lon = row.getFloat("longitude");
            float mag = row.getFloat("mag");
            float theta = radians(lat) + PI/2;
            float phi = radians(lon) + PI;
            float x = r * sin(theta) * cos(phi);
            float y = r * sin(theta) * sin(phi);
            float z = r * cos(theta);
            PVector pos = new PVector(x,y,z);

            float h = pow(10,mag);
            float maxh = pow(10,7);
            h = map(h,0,maxh,10,100);
            PVector xaxis = new PVector(1,0,0);
            float angleb = PVector.angleBetween(xaxis,pos);
            PVector raxis = xaxis.cross(pos);


            pushMatrix();
            translate(x,y,z);
            box(h,5,5);
            popMatrix();

        }
    }


    public static void main(String[] args){
        PApplet.main("Main.Main");
    }



}
